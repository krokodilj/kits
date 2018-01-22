package com.timsedam.buildingmanagement.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.request.CreateAnnouncementDTO;
import com.timsedam.buildingmanagement.dto.response.AnnouncementDTO;
import com.timsedam.buildingmanagement.exceptions.BuildingMissingException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.mapper.AnnouncementMapper;
import com.timsedam.buildingmanagement.model.Announcement;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.AnnouncementService;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value = "/api/announcements/")
public class AnnouncementController {


    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private UserService userService;

    private AnnouncementMapper announcementMapper = new AnnouncementMapper();


    /**
     * Create announcement
     * @param principal
     * @param createAnnouncementDTO
     * @return AnnouncementDTO
     * @throws BuildingMissingException 
     * @throws UserMissingException 
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(
            Principal principal,
            @Valid @RequestBody CreateAnnouncementDTO createAnnouncementDTO,
            BindingResult validationResult
            ) throws BuildingMissingException, UserMissingException
    {
        if (validationResult.hasErrors())
            return new ResponseEntity<String>(validationResult.getAllErrors().toString(), HttpStatus.UNPROCESSABLE_ENTITY);

        Building building = buildingService.findOneById(createAnnouncementDTO.getBuilding());
        if (building == null)
            return new ResponseEntity<String>("Building does not exists", HttpStatus.NOT_FOUND);

        if(principal == null)
            return new ResponseEntity<String>("User does not exists", HttpStatus.NOT_FOUND);
        User user =  userService.findOneByUsername(principal.getName());
        if(user == null)
            return new ResponseEntity<String>("User does not exists", HttpStatus.NOT_FOUND);

        if(!userService.isResidentOrApartmentOwnerInBuilding(user.getId(), building.getId()))
            return new ResponseEntity<String>("User isn't resident of building", HttpStatus.CONFLICT);

        Announcement announcement = announcementMapper.toModel(createAnnouncementDTO,building,user);
        announcement = announcementService.save(announcement);
        if (announcement == null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        AnnouncementDTO announcementDTO = announcementMapper.toDto(announcement);

        return new ResponseEntity<AnnouncementDTO>(announcementDTO, HttpStatus.CREATED);
    }

    /**
     * Get announcements by building
     * @param buildingId
     * @param page default 0
     * @param count default 5
     * @return List<AnnouncementDTO>
     * @throws BuildingMissingException 
     * @throws UserMissingException 
     */
    @GetMapping(value="/by_building/{buildingId}")
    public ResponseEntity<?> getByBuilding(
            Principal principal,
            @PathVariable long buildingId,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "5") int count
    ) throws BuildingMissingException, UserMissingException{
        List<AnnouncementDTO> announcementDTOS = new ArrayList<AnnouncementDTO>();
        Building building = buildingService.findOneById(buildingId);
        if(building == null)
            return new ResponseEntity<String>("Building does not exists", HttpStatus.NOT_FOUND);

        User r = userService.findOneByUsername(principal.getName());
        if(!userService.isResident(r.getId(), building.getId()))
            return new ResponseEntity<String>("User isn't resident of building", HttpStatus.CONFLICT);

        List<Announcement> announcements = announcementService.findAllByBuilding(building,page,count);
        if (announcements == null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        for(Announcement a :announcements)
            announcementDTOS.add(announcementMapper.toDto(a));

        return new ResponseEntity<List<AnnouncementDTO>>(announcementDTOS,HttpStatus.OK);
    }
}
