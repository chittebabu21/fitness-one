/**
 * 
 */
package com.fitness_one.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fitness_one.booking.model.Booking;
import com.fitness_one.booking.service.BookingService;

/**
 * author: chittebabu
 */

@RestController
@RequestMapping("/bookings")
@CrossOrigin
public class BookingController {
	// wire to services
	@Autowired
	private BookingService bookingService;
	
	// methods
	@GetMapping
	public List<Booking> findAllBookings() {
		return bookingService.findAllBookings();
	}
	
	@GetMapping("/{bookingId}")
	public Booking findBookingById(@PathVariable Integer bookingId) {
		return bookingService.findBookingById(bookingId);
	}
	
	@PostMapping
	public Booking saveBooking(@RequestBody Booking booking) {
		return bookingService.saveBooking(booking); // write logic to check if slot is available
	}
	
	@PutMapping("/{bookingId}/booking-status")
	public Booking updateBookingStatus(@PathVariable Integer bookingId, @RequestParam("bookingStatus") String bookingStatus) {
		Booking existingBooking = bookingService.findBookingById(bookingId);
		
		if (existingBooking == null) {
			throw new RuntimeException("Booking with ID " + bookingId + " not found.");
		}
		
		return bookingService.updateBookingStatus(existingBooking, bookingStatus);
	}
	
	@DeleteMapping("/{bookingId}")
	public void removeBooking(@PathVariable Integer bookingId) {
		bookingService.removeBooking(bookingId);
	}
}
