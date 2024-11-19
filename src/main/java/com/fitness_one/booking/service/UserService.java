/**
 * 
 */
package com.fitness_one.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitness_one.booking.model.User;
import com.fitness_one.booking.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * author: chittebabu
 */

@Service
@Transactional
public class UserService {
	// wire to repository
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	// crud methods
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	public User findUserById(Integer userId) {
		return userRepository.findById(userId).orElse(null);
	}
	
	public User findUserByEmailAddress(String emailAddress) {
		return userRepository.findUserByEmailAddress(emailAddress);
	}
	
	public User saveUser(User user) {
		if (user.getPasswordHash() != null) {
			String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
			user.setPasswordHash(hashedPassword);
		}
		
		return userRepository.save(user);
	}
	
	public User updatePassword(User user, String newPassword) {
		if (newPassword != null) {
			String hashedPassword = passwordEncoder.encode(newPassword);
	        user.setPasswordHash(hashedPassword);
		}
        
        return userRepository.save(user);
    }
	
	public User updateToken(User user, String token) {
		user.setToken(token);
		return userRepository.save(user);
	}
	
	public void removeUser(Integer userId) {
		userRepository.deleteById(userId);
	}
	
	public boolean validatePassword(String inputPassword, String encodedPassword) {
		return passwordEncoder.matches(inputPassword, encodedPassword);
	}

}
