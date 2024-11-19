/**
 * 
 */
package com.fitness_one.booking.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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
@Table(name = "user_packages")
@Data
public class UserPackage {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "user_package_id", nullable = false)
	private Integer userPackageId;
	
	@Column(name = "balance_credits", nullable = false)
	private Integer balanceCredits;
	
	@Column(name = "purchase_date", nullable = false)
	private String purchaseDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "package_id")
	private Package purchasedPackage;
	
	@PrePersist
	 private void setPurchasedDate() {
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    this.purchaseDate = dateFormat.format(timestamp);
	 }
}
