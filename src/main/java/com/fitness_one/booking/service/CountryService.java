/**
 * 
 */
package com.fitness_one.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness_one.booking.model.Country;
import com.fitness_one.booking.repository.CountryRepository;

import jakarta.transaction.Transactional;

/**
 * author: chittebabu
 */

@Service
@Transactional
public class CountryService {
	// wire to repository
	@Autowired
	private CountryRepository countryRepository;
	
	// crud methods
	public List<Country> findAllCountries() {
		return countryRepository.findAll();
	}
	
	public Country findCountryById(Integer countryId) {
		return countryRepository.findById(countryId).orElse(null);
	}
	
	public Country saveCountry(Country country) {
		return countryRepository.save(country);
	}
	
	public void removeCountry(Integer countryId) {
		countryRepository.deleteById(countryId);
	}

}
