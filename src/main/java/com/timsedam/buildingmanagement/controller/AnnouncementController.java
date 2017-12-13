package com.timsedam.buildingmanagement.controller;

import com.timsedam.buildingmanagement.dto.AnnouncementDTO;
import com.timsedam.buildingmanagement.dto.CreateAnnouncementDTO;
import com.timsedam.buildingmanagement.model.Announcement;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.AnnouncementService;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.service.UserService;
import com.timsedam.buildingmanagement.util.mappers.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "api/announcements")
public class AnnouncementController {


    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private UserService userService;

    private AnnouncementMapper announcementMapper = new AnnouncementMapper();

    @PostMapping(consumes = "application/json")
    public ResponseEntity create(
            Principal principal,
            @Valid @RequestBody CreateAnnouncementDTO createAnnouncementDTO,
            BindingResult validationResult
            )
    {
        if (validationResult.hasErrors())
            return new ResponseEntity(validationResult.getAllErrors().toString(), HttpStatus.UNPROCESSABLE_ENTITY);

        Building building = buildingService.findOneById(createAnnouncementDTO.getBuilding());
        if (building == null)
            return new ResponseEntity("building does not exists",HttpStatus.BAD_REQUEST);

        User user =  userService.findOneByUsername(principal.getName());

        if(user == null)
            return new ResponseEntity("user does not exists",HttpStatus.BAD_REQUEST);

        Announcement announcement = announcementMapper.toModel(createAnnouncementDTO,building,user);
        announcement = announcementService.save(announcement);
        if (announcement == null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        AnnouncementDTO announcementDTO = announcementMapper.toDto(announcement);

        return new ResponseEntity(announcementDTO,HttpStatus.OK);
    }

}
