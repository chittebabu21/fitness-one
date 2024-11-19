/**
 * 
 */
package com.fitness_one.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness_one.booking.model.UserPackage;

/**
 * author: chittebabu
 */

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, Integer> {

}
