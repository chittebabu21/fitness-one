/**
 * 
 */
package com.fitness_one.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness_one.booking.model.UserPackage;
import com.fitness_one.booking.repository.UserPackageRepository;

import jakarta.transaction.Transactional;

/**
 * author: chittebabu
 */

@Service
@Transactional
public class UserPackageService {
	// wire to repository
	@Autowired
	private UserPackageRepository userPackageRepository;
	
	// crud methods
	public List<UserPackage> findAllUserPackages() {
		return userPackageRepository.findAll();
	}
	
	public UserPackage findUserPackageById(Integer userPackageId) {
		return userPackageRepository.findById(userPackageId).orElse(null);
	}
	
	public UserPackage saveUserPackage(UserPackage userPackage) {
		return userPackageRepository.save(userPackage);
	}
	
	public UserPackage updateBalanceCredits(UserPackage userPackage, Integer newBalanceCredits) {
		userPackage.setBalanceCredits(newBalanceCredits);
		return userPackageRepository.save(userPackage);
	}
	
	public void removeUserPackage(Integer userPackageId) {
		userPackageRepository.deleteById(userPackageId);
	}
	
	// payment method
	public boolean paymentCharge(double amount) {
        if (amount > 0) {
            System.out.println("Payment successful for amount: " + amount);
            return true;
        } else {
            System.out.println("Payment failed: Invalid amount.");
            return false;
        }
    }
	
	private boolean checkCredits(Integer balanceCredits, Integer fitnessClassCredits) {
		if (balanceCredits - fitnessClassCredits >= 0) {
			return true;
		} else {
			return false;
		}
	}
}
