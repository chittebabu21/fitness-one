/**
 * 
 */
package com.fitness_one.booking.controller;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import io.jsonwebtoken.Claims;
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
	public User createUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		User existingUser = userService.findUserByEmailAddress(user.getEmailAddress());
		boolean validatedPassword = userService.validatePassword(user.getPasswordHash(), existingUser.getPasswordHash());
		System.out.println(user.getPasswordHash());
		System.out.println(existingUser.getPasswordHash());
		System.out.println(existingUser);
		System.out.println(validatedPassword);
		
		long expirationTime = 3600000;
		
		if (existingUser != null && validatedPassword) {
//			long expirationTime = Long.parseLong(expirationTimeString);
						
			String token = Jwts.builder()
					.setSubject(existingUser.getEmailAddress())
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
					.signWith(getSignInKey(), SignatureAlgorithm.HS256)
					.compact();
			
			existingUser.setToken(token);
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

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
