/**
 * 
 */
package com.fitness_one.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness_one.booking.model.Package;
import com.fitness_one.booking.repository.PackageRepository;

import jakarta.transaction.Transactional;

/**
 * author: chittebabu
 */

@Service
@Transactional
public class PackageService {
	// wire to repository
	@Autowired
	private PackageRepository packageRepository;
	
	// crud methods
	public List<Package> findAllPackages() {
		return packageRepository.findAll();
	}
	
	public Package findPackageById(Integer packageId) {
		return packageRepository.findById(packageId).orElse(null);
	}
	
	public Package savePackage(Package newPackage) {
		return packageRepository.save(newPackage);
	}
	
	public void removePackage(Integer packageId) {
		packageRepository.deleteById(packageId);
	}
}
