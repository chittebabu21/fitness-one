/**
 * 
 */
package com.fitness_one.booking.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.fitness_one.booking.model.FitnessClass;
import com.fitness_one.booking.model.User;
import com.fitness_one.booking.model.UserPackage;
import com.fitness_one.booking.service.BookingService;
import com.fitness_one.booking.service.FitnessClassService;
import com.fitness_one.booking.service.RedisService;
import com.fitness_one.booking.service.UserPackageService;
import com.fitness_one.booking.service.UserService;

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
	
	@Autowired
	private UserPackageService userPackageService;
	
	@Autowired
	private FitnessClassService fitnessClassService;
	
	@Autowired 
	private RedisService redisService;
	
	// methods
	@GetMapping
	public List<Booking> findAllBookings() {
		return bookingService.findAllBookings();
	}
	
	@GetMapping("/{bookingId}")
	public Booking findBookingById(@PathVariable Integer bookingId) {
		return bookingService.findBookingById(bookingId);
	}
	
	// to book a class
	@PostMapping
	public ResponseEntity<Object> saveBooking(@RequestBody Booking newBooking) {
		User user = newBooking.getUser();
		FitnessClass fitnessClass = newBooking.getFitnessClass();
		
		if (user == null || fitnessClass == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to find user or class.");
		}
		
		FitnessClass existingFitnessClass = fitnessClassService.findFitnessClassById(fitnessClass.getClassId());
		
		if (existingFitnessClass == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fitness class not found");
		}
		
		if (existingFitnessClass.getSlotsAvailable() <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No slots available.");
		}
		
		// check redis for booking limit
		boolean canBook = redisService.incrementBookingCount(fitnessClass.getClassId().toString());
		
		if (!canBook) {
			return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many bookings at this time.");
		}
		
		try {
			// get the user package by user id
			Optional<UserPackage> userPackageOpt = userPackageService.findUserPackageByUserId(user.getUserId());
			
			if (userPackageOpt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User package not found.");
			}
			
			UserPackage userPackage = userPackageOpt.get();
			
			// check if same country
			if (!userPackage.getPurchasedPackage().getCountry().equals(existingFitnessClass.getCountry())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User package not valid in this country.");
			}
			
			// check for balance credits
			if (userPackage.getBalanceCredits() < existingFitnessClass.getCreditsNeeded()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient credits.");
			}
			
			// update user package
			Integer newBalanceCredits = userPackage.getBalanceCredits() - existingFitnessClass.getCreditsNeeded();
			userPackageService.updateBalanceCredits(userPackage, newBalanceCredits);
			
			// update available slots
			Integer newSlotsAvailable = existingFitnessClass.getSlotsAvailable() - 1;
			fitnessClassService.updateSlotsAvailable(existingFitnessClass, newSlotsAvailable);
			
			// save the booking
			newBooking.setBookingStatus("confirmed");
			Booking savedBooking =  bookingService.saveBooking(newBooking);
			
			Map<String, Object> response = new HashMap<>();
			response.put("message", "Booking added successfully!");
			response.put("booking", savedBooking);
			
			return ResponseEntity.ok(response);
		} finally {
			redisService.decrementBookingCount(fitnessClass.getClassId().toString());
		}
			
	}
	
	// cancel booking method
	@PutMapping("/{bookingId}/cancel")
	public ResponseEntity<Object> cancelBooking(@PathVariable Integer bookingId) {
	    Booking existingBooking = bookingService.findBookingById(bookingId);
	    
	    if (existingBooking == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found.");
	    }
	    
	    FitnessClass fitnessClass = existingBooking.getFitnessClass();
	    LocalDateTime classDate = fitnessClass.getClassDate();
	    LocalDateTime currentTime = LocalDateTime.now();
	    long hoursUntilClass = java.time.Duration.between(currentTime, classDate).toHours();
	    
	    existingBooking.setBookingStatus("cancelled");
	    User user = existingBooking.getUser(); 
	    
	    Optional<UserPackage> userPackageOpt = userPackageService.findUserPackageByUserId(user.getUserId());
	    
	    if (userPackageOpt.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User package not found.");
	    }
	    
	    UserPackage userPackage = userPackageOpt.get();
	    
	    if (hoursUntilClass > 4) {
	        //  refund credits if more than 4 hours
	        Integer refundedCredits = existingBooking.getFitnessClass().getCreditsNeeded();
	        Integer newBalanceCredits = userPackage.getBalanceCredits() + refundedCredits;
	        userPackageService.updateBalanceCredits(userPackage, newBalanceCredits);
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Booking cancelled and credits refunded.");
	        response.put("booking", bookingService.saveBooking(existingBooking));
	        
	        return ResponseEntity.ok(response);
	    } else {
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Booking cancelled, but credits not refunded as the class is within 4 hours.");
	        response.put("booking", bookingService.saveBooking(existingBooking));
	        
	        return ResponseEntity.ok(response);
	    }
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
