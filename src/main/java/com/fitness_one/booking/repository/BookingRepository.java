/**
 * 
 */
package com.fitness_one.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness_one.booking.model.Booking;

/**
 * author: chittebabu
 */

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
