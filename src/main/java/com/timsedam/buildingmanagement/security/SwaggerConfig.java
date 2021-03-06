package com.timsedam.buildingmanagement.security;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
    	System.out.println("SWAGGER");
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())          
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());                                           
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
          "Building management REST API", 
          "", 
          "", 
          "", 
          new Contact("Mladen Stajcic, Miodrag Vilotijevic, Nemanja Zunic", "https://github.com/krokodilj/kits", "miodragvilotijevic@gmail.com"), 
          "License of API", "API license URL", Collections.emptyList());
   }
}