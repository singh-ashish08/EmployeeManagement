package com.emp.security;

import org.springframework.context.annotation.Configuration;

//@EnableWebSecurity
@Configuration
public class SecurityConfig {
<<<<<<< Updated upstream

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filter(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				// ⬅️ Allow public access (no login)
				.requestMatchers("/", "/convert-pdf", "/download/**", "/css/**", "/js/**").permitAll()
				// 🔐 ADMIN-only access
				.requestMatchers("/employee/**", "/employee/create", "/employee/update/**", "/employee/pupdate/**",
						"/employee/delete/**")
				.hasRole("ADMIN")

				.anyRequest().authenticated())

				.formLogin(Customizer.withDefaults()).logout(logout -> logout.logoutUrl("/logout"))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user1 = User.builder().username("@Ashish08").password(passwordEncoder().encode("!Ashish"))
				.roles("USER").build();
		UserDetails user2 = User.builder().username("@Ramesh").password(passwordEncoder().encode("R@m")).roles("USER")
				.build();
		UserDetails admin = User.builder().username("@Admin").password(passwordEncoder().encode("!@Admin08"))
				.roles("ADMIN").build();

		return new InMemoryUserDetailsManager(user1, user2, admin);
	}
=======
	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 * 
	 * @Bean public SecurityFilterChain filter(HttpSecurity http) throws Exception {
	 * 
	 * http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth // ⬅️
	 * Allow public access (no login) .requestMatchers("/", "/convert-pdf",
	 * "/download/**", "/css/**", "/js/**").permitAll()
	 * 
	 * // 🔐 USER or ADMIN required for employee view
	 * .requestMatchers("/employee/find", "/employee/{id}", "/employee",
	 * "/employee/create_e", "/login", "/actuator/**") .hasAnyRole("USER", "ADMIN")
	 * 
	 * // 🔐 ADMIN-only access .requestMatchers("/employee/**", "/employee/create",
	 * "/employee/update/**", "/employee/pupdate/**", "/employee/delete/**")
	 * .hasRole("ADMIN")
	 * 
	 * .anyRequest().authenticated())
	 * 
	 * .formLogin(Customizer.withDefaults()).logout(logout ->
	 * logout.logoutUrl("/logout")) .sessionManagement(session ->
	 * session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	 * .httpBasic(Customizer.withDefaults());
	 * 
	 * return http.build(); }
	 * 
	 * @Bean public UserDetailsService userDetailsService() { UserDetails user1 =
	 * User.builder().username("@Ashish08").password(passwordEncoder().encode(
	 * "!Ashish")) .roles("USER").build(); UserDetails user2 =
	 * User.builder().username("@Ramesh").password(passwordEncoder().encode("R@m")).
	 * roles("USER") .build(); UserDetails admin =
	 * User.builder().username("@Admin").password(passwordEncoder().encode(
	 * "!@Admin08")) .roles("ADMIN").build();
	 * 
	 * return new InMemoryUserDetailsManager(user1, user2, admin); }
	 */
>>>>>>> Stashed changes

}
