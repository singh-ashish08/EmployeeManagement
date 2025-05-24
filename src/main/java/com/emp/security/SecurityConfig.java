package com.emp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filter(HttpSecurity http) throws Exception {

		http
				// Disable CSRF for simplicity (enable it in production!)
				.csrf(csrf -> csrf.disable())

				// Configure URL access rules
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/employee/find", "/employee/{id}", "/employee", "/employee/create_e",
								"/login")
						.permitAll()
						.requestMatchers("/employee/create", "/employee/update/**", "/employee/delete/**",
								"/employee/pupdate/**")
						.authenticated().anyRequest().authenticated())

				// Enable default login form
				.formLogin(Customizer.withDefaults())

				// Enable logout
				.logout(logout -> logout.logoutUrl("/logout"))

				// Use stateless session (good for REST APIs)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Enable HTTP Basic Auth for testing
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}
}
