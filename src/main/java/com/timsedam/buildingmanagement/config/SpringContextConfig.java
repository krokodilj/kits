package com.timsedam.buildingmanagement.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.timsedam.buildingmanagement.transformator.UserTypeStringToClass;
import com.timsedam.buildingmanagement.validator.UserTypeValidator;

@Configuration
public class SpringContextConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    @Bean 
    public UserTypeValidator userTypeValidator() {
    	return new UserTypeValidator();
    }
    
    @Bean 
    public UserTypeStringToClass userTypeStringToClass() {
    	return new UserTypeStringToClass();
    }
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
}
