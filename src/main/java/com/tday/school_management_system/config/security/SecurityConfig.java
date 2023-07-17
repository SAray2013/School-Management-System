package com.tday.school_management_system.config.security;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import com.tday.school_management_system.config.security.jwt.JwtLoginFilter;
import com.tday.school_management_system.config.security.jwt.TokenVerifyFilter;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class SecurityConfig {
	
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.addFilter(new JwtLoginFilter(authenticationManager2(authenticationConfiguration)))
			.addFilterAfter(new TokenVerifyFilter(), JwtLoginFilter.class)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeHttpRequests()
			.antMatchers("/","/index","/home","css/**","js/**").permitAll()
			.antMatchers(HttpMethod.GET,"/course/preview*").permitAll()
			.antMatchers(HttpMethod.POST,"/registration/*").permitAll()
			.antMatchers(HttpMethod.GET,"/registration/verify/*").permitAll()
			.antMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
			.antMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
			.antMatchers(HttpMethod.GET,"/swagger-ui/**").permitAll()
			.antMatchers(HttpMethod.GET,"/v2/api-docs").permitAll()
			.antMatchers(HttpMethod.GET,"/webjars/**").permitAll()
			.anyRequest()
			.authenticated();
		
		return http.build();
		
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getAuthenticationProvider());
	}
	
	@Bean
	public AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
	
	@Bean
	AuthenticationManager authenticationManager2(
	        AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
}