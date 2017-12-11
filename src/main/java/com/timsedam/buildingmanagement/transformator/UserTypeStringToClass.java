package com.timsedam.buildingmanagement.transformator;

import org.springframework.util.StringUtils;

public class UserTypeStringToClass {
	
	private String packagePath = "com.timsedam.buildingmanagement.model";
	
	public Class<?> transform(String userTypeString) throws ClassNotFoundException {
		String lowerCase = userTypeString.toLowerCase();
		String capitalized = StringUtils.capitalize(lowerCase);
		
		Class<?> userTypeClass = Class.forName(packagePath + "." + capitalized);
		return userTypeClass;
	}

}
