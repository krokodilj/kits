package com.timsedam.buildingmanagement.validator;

import java.util.Arrays;
import java.util.List;

public class UserTypeValidator {
	
	private final List<String> validUserTypes = Arrays.asList("admin", "manager", "resident");
	
	public boolean isValid(String userType) {
		String userTypeLowerCased = userType.toLowerCase();
		if(validUserTypes.contains(userTypeLowerCased))
			return true;
		else
			return false;
	}

}
