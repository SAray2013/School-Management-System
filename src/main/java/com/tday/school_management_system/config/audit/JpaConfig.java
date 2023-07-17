package com.tday.school_management_system.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
	
	@Bean
	public AuditorAware<String>  auditorAware(){
		return new AuditorAwareImpl();
	}

}