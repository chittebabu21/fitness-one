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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness_one.booking.model.Country;
import com.fitness_one.booking.service.CountryService;

/**
 * author: chittebabu
 */

@RestController
@RequestMapping("/countries")
@CrossOrigin
public class CountryController {
	// wire to service
	@Autowired
	private CountryService countryService;
	
	// methods
	@GetMapping
	public List<Country> findAllCountries() {
		return countryService.findAllCountries();
	}
	
	@GetMapping("/{countryId}") 
	public Country findCountryById(@PathVariable Integer countryId) {
		return countryService.findCountryById(countryId);
	}
	
	@PostMapping
	public Country createCountry(@RequestBody Country country) {
		return countryService.saveCountry(country);
	}
	
	@DeleteMapping("/{countryId}")
	public void removeCountry(@PathVariable Integer countryId) {
		Country existingCountry = countryService.findCountryById(countryId);
		
		if (existingCountry == null) {
			throw new RuntimeException("Country with ID: " + countryId + " not found.");
		}
		
		countryService.removeCountry(countryId);
	}
}
