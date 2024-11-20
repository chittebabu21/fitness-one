/**
 * 
 */
package com.fitness_one.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness_one.booking.model.FitnessClass;
import com.fitness_one.booking.repository.FitnessClassRepository;
import jakarta.transaction.Transactional;

/**
 * author: chittebabu
 */

@Service
@Transactional
public class FitnessClassService {
		// wire to repository
		@Autowired
		private FitnessClassRepository fitnessClassRepository;
		
		// crud methods
		public List<FitnessClass> findAllFitnessClasses() {
			return fitnessClassRepository.findAll();
		}
		
		public FitnessClass findFitnessClassById(Integer fitnessClassId) {
			return fitnessClassRepository.findById(fitnessClassId).orElse(null);
		}
		
		public List<FitnessClass> findFitnessClassesByCountry(Integer countryId) {
			return fitnessClassRepository.findFitnessClassesByCountry(countryId);
		}
		
		public FitnessClass saveFitnessClass(FitnessClass newFitnessClass) {
			return fitnessClassRepository.save(newFitnessClass);
		}
		
		public FitnessClass updateSlotsAvailable(FitnessClass fitnessClass, Integer slotsAvailable) {
			fitnessClass.setSlotsAvailable(slotsAvailable);
			return fitnessClassRepository.save(fitnessClass);
		}
		
		public void removeFitnessClass(Integer fitnessClassId) {
			fitnessClassRepository.deleteById(fitnessClassId);
		}
}
