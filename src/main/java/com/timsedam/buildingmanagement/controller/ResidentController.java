package com.timsedam.buildingmanagement.controller;

import com.timsedam.buildingmanagement.dto.UserDTO;
import com.timsedam.buildingmanagement.dto.UserRegisterDTO;
import com.timsedam.buildingmanagement.model.Resident;
import com.timsedam.buildingmanagement.model.Role;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.RoleService;
import com.timsedam.buildingmanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "api/residents")
public class ResidentController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity create(
            @Valid @RequestBody UserRegisterDTO userRegisterDTO,
            BindingResult validationResult
            )
    {
        if (validationResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

        if(userService.exists(userRegisterDTO.getUsername()))
            return new ResponseEntity<>("Username in use",HttpStatus.BAD_REQUEST);

        Resident resident = (Resident) modelMapper.map(userRegisterDTO, Resident.class);

        Role role = roleService.findOneByName("USER");
        resident.setRole(role);
        userService.save(resident);

        UserDTO responseData = modelMapper.map(resident, UserDTO.class);
        return new ResponseEntity(resident,HttpStatus.OK);
    }
}
