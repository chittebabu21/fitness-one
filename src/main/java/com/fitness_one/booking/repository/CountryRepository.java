/**
 * 
 */
package com.fitness_one.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness_one.booking.model.Country;

/**
 * author: chittebabu
 */

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
	
}
