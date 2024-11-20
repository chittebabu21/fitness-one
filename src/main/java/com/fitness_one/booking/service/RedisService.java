/**
 * 
 */
package com.fitness_one.booking.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * author: chittebabu
 */

@Service
public class RedisService {
	@Autowired
	StringRedisTemplate redisTemplate;
	
	// set cache expiration time
	private static final long EXPIRE_TIME = 5; 
	
	public boolean incrementBookingCount(String classId) {
        String key = "booking:class:" + classId;
        
        // Get the current count (stored as a string)
        String countStr = redisTemplate.opsForValue().get(key);
        Integer count = null;
        
        if (countStr != null) {
            try {
                count = Integer.valueOf(countStr); // Convert string to integer
            } catch (NumberFormatException e) {
                // Handle case where the value is not a valid integer string
                System.out.println("Error: Value is not a valid integer");
            }
        }

        if (count == null) {
            // If count is null (not found), initialize the count to 1 and set expiration
            redisTemplate.opsForValue().set(key, "1", EXPIRE_TIME, TimeUnit.MINUTES);
            return true;
        }

        if (count >= 5) {
            // If the count is 5 or more, deny further increments
            return false;
        }

        // Increment the count in Redis (stored as a string, but interpreted as an integer)
        redisTemplate.opsForValue().increment(key);
        return true;
    }

    // Decrement booking count
    public void decrementBookingCount(String classId) {
        String key = "booking:class:" + classId;
        
        // Get the current count (stored as a string)
        String countStr = redisTemplate.opsForValue().get(key);
        Integer count = null;

        if (countStr != null) {
            try {
                count = Integer.valueOf(countStr); // Convert string to integer
            } catch (NumberFormatException e) {
                // Handle case where the value is not a valid integer string
                System.out.println("Error: Value is not a valid integer");
            }
        }

        if (count != null && count > 0) {
            // Decrement the count in Redis (stored as a string, but interpreted as an integer)
            try {
                redisTemplate.opsForValue().decrement(key);
            } catch (RedisSystemException e) {
                // Handle exception
                System.out.println("Error in decrement.");
            }
        }
    }

}
