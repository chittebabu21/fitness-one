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

import com.fitness_one.booking.model.Package;
import com.fitness_one.booking.service.PackageService;

/**
 * author: chittebabu
 */

@RestController
@RequestMapping("/packages")
@CrossOrigin
public class PackageController {
	// wire to services
	@Autowired
	private PackageService packageService;
	
	// methods
	@GetMapping
	public List<Package> findAllPackages() {
		return packageService.findAllPackages();
	}
	
	@GetMapping("/{packageId}")
	public Package findPackageById(@PathVariable Integer packageId) {
		return packageService.findPackageById(packageId);
	}
	
	@PostMapping
	public Package savePackage(@RequestBody Package newPackage) {
		return packageService.savePackage(newPackage);
	}
	
	@DeleteMapping("/{packageId}") 
	public void removePackage(@PathVariable Integer packageId) {
		Package existingPackage = packageService.findPackageById(packageId);
		
		if (existingPackage == null) {
			throw new RuntimeException("Package with ID: " + packageId + " not found.");
		}
		packageService.removePackage(packageId);
	}
}
