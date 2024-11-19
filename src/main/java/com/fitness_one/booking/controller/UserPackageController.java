/**
 * 
 */
package com.fitness_one.booking.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness_one.booking.model.Package;
import com.fitness_one.booking.model.UserPackage;
import com.fitness_one.booking.service.PackageService;
import com.fitness_one.booking.service.UserPackageService;

/**
 * author: chittebabu
 */

@RestController
@RequestMapping("/user-packages")
@CrossOrigin
public class UserPackageController {
	@Autowired
	private UserPackageService userPackageService;
	
	@Autowired
	private PackageService packageService;
	
	// methods
	@GetMapping
	public List<UserPackage> findAllUserPackages() {
		return userPackageService.findAllUserPackages();
	}
	
	@GetMapping("/{userPackageId}")
	public UserPackage findUserPackageById(@PathVariable Integer userPackageId) {
		return userPackageService.findUserPackageById(userPackageId);
	}
	
//	@PostMapping
//	public UserPackage saveUserPackage(@RequestBody UserPackage userPackage) {
//		return userPackageService.saveUserPackage(userPackage);
//	}
	
	// buy method
	@PostMapping
	public ResponseEntity<Object> makePayment(@RequestBody UserPackage userPackage) {
		 Integer packageId = userPackage.getPurchasedPackage().getPackageId();
		 Package purchasedPackage = packageService.findPackageById(packageId);

		 if (purchasedPackage == null) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid package ID.");
		}
		 
		double packagePrice = purchasedPackage.getPrice();
		boolean paymentSuccessful = userPackageService.paymentCharge(packagePrice);
		
		if (paymentSuccessful) {
			userPackage.setPurchasedPackage(purchasedPackage);
			UserPackage newUserPackage = userPackageService.saveUserPackage(userPackage);
			
			Map<String, Object> response = new HashMap<>();
			response.put("message", "Payment was successful!");
			response.put("user package", newUserPackage);
			
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed.");
		}
	}
	
	@DeleteMapping("/{userPackageId}")
	public void removeUserPackage(@PathVariable Integer userPackageId) {
		userPackageService.removeUserPackage(userPackageId);
	}
}
