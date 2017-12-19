package com.timsedam.buildingmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuildingManagementApplication {

	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(BuildingManagementApplication.class);
		app.setLogStartupInfo(false);
		app.run(args);
	}
}
