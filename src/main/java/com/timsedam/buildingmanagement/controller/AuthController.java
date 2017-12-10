package com.timsedam.buildingmanagement.controller;

import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import com.timsedam.buildingmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by sirko on 12/7/17.
 */
@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            return new ResponseEntity<String>(validationResult.getAllErrors().toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String token=authService.login(userLoginDTO);

        if(token==null){
            return new ResponseEntity<String>("user not authenticated",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<String>(token,HttpStatus.OK);
    }

}
