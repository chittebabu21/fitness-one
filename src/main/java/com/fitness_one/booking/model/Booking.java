/**
 * 
 */
package com.fitness_one.booking.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * author: chittebabu
 */

@Entity
@Table(name = "bookings")
@Data
public class Booking {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "booking_id", nullable = false)
	private Integer bookingId;
	
	@Column(name = "booking_status")
	private String bookingStatus;
	
	@Column(name = "booked_on", nullable = false)
	private String bookedOn;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "class_id")
	private FitnessClass fitnessClass;
	
	@PrePersist
	 private void setPurchasedDate() {
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    this.bookedOn = dateFormat.format(timestamp);
	 }
}
