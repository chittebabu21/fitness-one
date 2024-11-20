/**
 * 
 */
package com.fitness_one.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness_one.booking.model.Package;
import com.fitness_one.booking.model.UserPackage;

/**
 * author: chittebabu
 */

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {
	
}
