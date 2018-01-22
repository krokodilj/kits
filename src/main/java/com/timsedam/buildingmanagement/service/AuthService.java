package com.timsedam.buildingmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.model.Role;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.UserRepository;
import com.timsedam.buildingmanagement.security.JsonWebToken;

@Service
public class AuthService {

    @Autowired
    private JsonWebToken jwt;
    
    @Autowired
    private UserRepository userRepository;

    public String login(UserLoginDTO dto) throws UserMissingException{
        User user = userRepository.findOneByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        
        if(user == null) 
        	throw new UserMissingException();
        
        List<String> roles = new ArrayList<String>();
        for(Role role : user.getRoles())
        	roles.add(role.getName());
        String token = jwt.generateToken(user.getUsername(), roles);
        return token;
    }

}
