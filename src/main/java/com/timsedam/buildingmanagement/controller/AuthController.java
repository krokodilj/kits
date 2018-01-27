package com.timsedam.buildingmanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.service.AuthService;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(value = "/login", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult validationResult) throws UserMissingException{
        if (validationResult.hasErrors()) {
    		String errorMessage = validationResult.getFieldError().getDefaultMessage();
            return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String token = authService.login(userLoginDTO);

        if(token == null){
            return new ResponseEntity<String>("user not authenticated", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
    
	/**
	 * Handles UserMissingException that can happen when calling AuthService.login(userLoginDto)
	 */
	@ExceptionHandler(UserMissingException.class)
	public ResponseEntity<String> userMissingException(final UserMissingException e) {
		return new ResponseEntity<String>("Invalid login data provided.", HttpStatus.NOT_FOUND);
	}

}
