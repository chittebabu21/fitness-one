/**
 * 
 */
package com.fitness_one.booking.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
@Table(name = "classes")
@Data
public class FitnessClass {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "class_id", nullable = false)
	private Integer classId;
	
	@Column(name = "class_name", nullable = false)
	private String className;
	
	@Column(name = "credits_needed", nullable = false)
	private Integer creditsNeeded;
	
	@Column(name = "slots_available")
	private Integer slotsAvailable;
	
	@Column(name = "class_date", nullable = false)
	private LocalDateTime classDate;
	
	@Column(name = "added_on", nullable = false)
	private String addedOn;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	
	// method to format timestamp object
	public void setClassDate(String expiryDateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		this.classDate = LocalDateTime.parse(expiryDateString, formatter);
	}
		
	@PrePersist
	 private void setAddedOn() {
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    this.addedOn = dateFormat.format(timestamp);
	 }
}
