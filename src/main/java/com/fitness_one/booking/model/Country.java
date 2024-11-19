/**
 * 
 */
package com.fitness_one.booking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * author: chittebabu
 */

@Entity
@Table(name = "countries")
@Data
public class Country {
	// properties
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "country_id", nullable = false)
	private Integer countryId;
	
	@Column(name = "country", nullable = false)
	private String country;

}
