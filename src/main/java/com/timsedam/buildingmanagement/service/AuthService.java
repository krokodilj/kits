package com.timsedam.buildingmanagement.service;

import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.UserRepository;
import com.timsedam.buildingmanagement.security.JsonWebToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sirko on 12/7/17.
 */
@Service
public class AuthService {

    @Autowired
    JsonWebToken jwt;

    @Autowired
    UserRepository userRepository;

    public String login(UserLoginDTO dto){
        User user = userRepository.findOneByUsernameAndPassword(dto.getUsername(),dto.getPassword());
        if(user==null) return null;
        String token = jwt.generateToken(user.getUsername(),"role");
        return token;
    }

}
