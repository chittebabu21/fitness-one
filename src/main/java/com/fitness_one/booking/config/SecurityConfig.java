/**
 * 
 */
package com.fitness_one.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * author: chittebabu
 */

@Configuration
public class SecurityConfig {
	@SuppressWarnings({ "deprecation", "removal" })
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable security for all endpoints
        http.csrf().disable()
            .authorizeRequests()
            .anyRequest().permitAll(); // Allow all requests without authentication

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordSecurityEncoder() {
        return new BCryptPasswordEncoder(); // Required if you still want to use BCrypt
    }
}
