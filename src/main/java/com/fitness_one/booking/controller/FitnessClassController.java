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

import com.fitness_one.booking.model.FitnessClass;
import com.fitness_one.booking.model.Package;
import com.fitness_one.booking.service.FitnessClassService;
import com.fitness_one.booking.service.PackageService;

/**
 * author: chittebabu
 */

@RestController
@RequestMapping("/classes")
@CrossOrigin
public class FitnessClassController {
	// wire to services
	@Autowired
	private FitnessClassService fitnessClassService;
	
	// methods
	@GetMapping
	public List<FitnessClass> findAllFitnessClasses() {
		return fitnessClassService.findAllFitnessClasses();
	}
	
	@GetMapping("/{fitnessClassId}")
	public FitnessClass findFitnessClassById(@PathVariable Integer fitnessClassId) {
		return fitnessClassService.findFitnessClassById(fitnessClassId);
	}
	
	// get fitness classes by country
	@GetMapping("/country/{countryId}")
	public List<FitnessClass> findFitnessClassesByCountry(@PathVariable Integer countryId) {
		return fitnessClassService.findFitnessClassesByCountry(countryId);
	}
	
	@PostMapping
	public FitnessClass saveFitnessClass(@RequestBody FitnessClass newFitnessClass) {
		return fitnessClassService.saveFitnessClass(newFitnessClass);
	}
	
	@DeleteMapping("/{fitnessClassId}") 
	public void removeFitnessClass(@PathVariable Integer fitnessClassId) {
		FitnessClass existingFitnessClass = fitnessClassService.findFitnessClassById(fitnessClassId);
		
		if (existingFitnessClass == null) {
			throw new RuntimeException("Package with ID: " + fitnessClassId + " not found.");
		}
		
		fitnessClassService.removeFitnessClass(fitnessClassId);
	}
}
