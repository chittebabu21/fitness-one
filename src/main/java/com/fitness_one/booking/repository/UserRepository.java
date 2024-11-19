/**
 * 
 */
package com.fitness_one.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness_one.booking.model.User;

/**
 * author: chittebabu
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	// find by email address
	User findUserByEmailAddress(String emailAddress);
}
