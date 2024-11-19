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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * author: chittebabu
 */

@Entity
@Table(name = "users")
@Data
public class User {
	// properties
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	@Column(name = "email_address", nullable = false)
	private String emailAddress;
	
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "is_verified", nullable = false)
	private boolean isVerified;
	
	@Column(name = "created_on", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private String createdOn;

    // method to format timestamp object
    @PrePersist
    private void setCreatedOn() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdOn = dateFormat.format(timestamp);
    }
	
}
