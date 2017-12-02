package com.timsedam.buildingmanagement.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
