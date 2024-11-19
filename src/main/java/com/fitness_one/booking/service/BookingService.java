/**
 * 
 */
package com.fitness_one.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness_one.booking.model.Booking;
import com.fitness_one.booking.repository.BookingRepository;

import jakarta.transaction.Transactional;

/**
 * author: chittebabu
 */

@Service
@Transactional
public class BookingService {
	// wire to repository
	@Autowired
	private BookingRepository bookingRepository;
	
	// crud methods
	public List<Booking> findAllBookings() {
		return bookingRepository.findAll();
	}
	
	public Booking findBookingById(Integer bookingId) {
		return bookingRepository.findById(bookingId).orElse(null);
	}
	
	public Booking saveBooking(Booking booking) {
		return bookingRepository.save(booking);
	}
	
	public Booking updateBookingStatus(Booking booking, String bookingStatus) {
		booking.setBookingStatus(bookingStatus);
		return bookingRepository.save(booking);
	}
	
	public void removeBooking(Integer bookingId) {
		bookingRepository.deleteById(bookingId);
	}
}
