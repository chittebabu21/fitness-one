/**
 * 
 */
package com.fitness_one.booking.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
@Table(name = "packages")
@Data
public class Package {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "package_id", nullable = false)
	private Integer packageId;
	
	@Column(name = "package_name", nullable = false)
	private String packageName;
	
	@Column(name = "credits", nullable = false)
	private Integer credits;
	
	@Column(name = "price")
	private float price;
	
	@Column(name = "created_on", nullable = false)
	private String createdOn;
	
	@Column(name = "expiry_date")
	private LocalDateTime expiryDate;
	
	@ManyToOne
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;
	
	// method to format timestamp object
	public void setExpiryDate(String expiryDateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    this.expiryDate = LocalDateTime.parse(expiryDateString, formatter);
	}
	
    @PrePersist
    private void setCreatedOn() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdOn = dateFormat.format(timestamp);
    }
}
