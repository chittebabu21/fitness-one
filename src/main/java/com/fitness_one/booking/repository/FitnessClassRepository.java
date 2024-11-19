/**
 * 
 */
package com.fitness_one.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fitness_one.booking.model.FitnessClass;

import io.lettuce.core.dynamic.annotation.Param;

/**
 * author: chittebabu
 */

@Repository
public interface FitnessClassRepository extends JpaRepository<FitnessClass, Integer> {
	@Query("SELECT f FROM FitnessClass f WHERE f.country.countryId = :countryId")
	List<FitnessClass> findFitnessClassesByCountry(@Param("country_id") Integer countryId);
}
