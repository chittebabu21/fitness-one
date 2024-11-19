/**
 * 
 */
package com.fitness_one.booking.controller;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness_one.booking.model.User;
import com.fitness_one.booking.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * author: chittebabu
 */

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	// properties
	@Value("${security.jwt.secret-key}")
	private String secretKey;
	
//	@Value("${security.jwt.expiration-time}")
//	private String expirationTimeString;
	
	// wired to service
	@Autowired
	private UserService userService;
	
	// methods
	@GetMapping
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	}
	
	@GetMapping("/{userId}")
	public User findUserById(@PathVariable Integer userId) {
		return userService.findUserById(userId);
	}
	
	@GetMapping("/search")
	public User findUserByEmailAddress(@RequestParam("email-address") String emailAddress) {
		return userService.findUserByEmailAddress(emailAddress);
	}
	
	@PostMapping
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		boolean validEmailAddress = this.sendVerifyEmail(user.getEmailAddress());
		
		if (validEmailAddress) {
			User newUser = userService.saveUser(user);
			
			Map<String, Object> response = new HashMap<>();
			response.put("message", "User added successfully!");
			response.put("user", newUser);
			
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add user.");
		}
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		User existingUser = userService.findUserByEmailAddress(user.getEmailAddress());
		boolean validatedPassword = userService.validatePassword(user.getPasswordHash(), existingUser.getPasswordHash());
		
		long expirationTime = 3600000;
		
		if (existingUser != null && validatedPassword) {
//			long expirationTime = Long.parseLong(expirationTimeString);
						
			String token = Jwts.builder()
					.setSubject(existingUser.getEmailAddress())
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
					.signWith(getSignInKey(), SignatureAlgorithm.HS256)
					.compact();
			
			userService.updateToken(existingUser, token);
			return token;
		} else {
			return "Invalid email address or password.";
		}
	}
	
	@PutMapping("/{userId}/password")
	public User updatePassword(@PathVariable Integer userId, @RequestParam("newPassword") String newPassword) {
	    User existingUser = userService.findUserById(userId);
	    
	    if (existingUser == null) {
	        throw new RuntimeException("User with ID " + userId + " not found.");
	    }
	    
	    existingUser.setPasswordHash(newPassword);
	    return userService.updatePassword(existingUser, newPassword);
	}

	// Update the user's token
	@PutMapping("/{userId}/token")
	public User updateToken(@PathVariable Integer userId, @RequestParam("newToken") String newToken) {
	    User existingUser = userService.findUserById(userId);
	    
	    if (existingUser == null) {
	        throw new RuntimeException("User with ID " + userId + " not found.");
	    }
	    
	    existingUser.setToken(newToken);
	    return userService.saveUser(existingUser);
	}

	
	@DeleteMapping("/{userId}")
	public void removeUser(@PathVariable Integer userId) {
		User existingUser = userService.findUserById(userId);
		
		if (existingUser == null) {
			throw new RuntimeException("User with ID: " + userId + " not found.");
		}
		
		userService.removeUser(userId);
	}
	
	// send verify email dummy method
	public boolean sendVerifyEmail(String emailAddress) {
		return true;
	}

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
