package com.timsedam.buildingmanagement.controller;

import com.timsedam.buildingmanagement.dto.AnnouncementDTO;
import com.timsedam.buildingmanagement.dto.CreateAnnouncementDTO;
import com.timsedam.buildingmanagement.model.Announcement;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Resident;
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
import java.util.ArrayList;
import java.util.List;

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
     */
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
            return new ResponseEntity("Building does not exists",HttpStatus.NOT_FOUND);

        if(principal == null)
            return new ResponseEntity("User does not exists",HttpStatus.NOT_FOUND);

        User user =  userService.findOneByUsername(principal.getName());
        if(user == null)
            return new ResponseEntity("User does not exists",HttpStatus.NOT_FOUND);

        Resident r = (Resident) user;

        if(r.isResident(building))
            return new ResponseEntity("User isn't resident of building",HttpStatus.CONFLICT);

        Announcement announcement = announcementMapper.toModel(createAnnouncementDTO,building,user);
        announcement = announcementService.save(announcement);
        if (announcement == null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        AnnouncementDTO announcementDTO = announcementMapper.toDto(announcement);

        return new ResponseEntity(announcementDTO,HttpStatus.CREATED);
    }

    /**
     * Get announcements by building
     * @param buildingId
     * @param page default 0
     * @param count default 5
     * @return List<AnnouncementDTO>
     */
    @GetMapping(value="/by_building/{buildingId}")
    public ResponseEntity getByBuilding(
            @PathVariable long buildingId,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "5") int count
    ){
        List<AnnouncementDTO> announcementDTOS=new ArrayList<AnnouncementDTO>();
        Building building=buildingService.findOneById(buildingId);
        if(building==null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        List<Announcement> announcements = announcementService.findAllByBuilding(building,page,count);

        if (announcements==null)
            return new ResponseEntity("Building does not exists",HttpStatus.INTERNAL_SERVER_ERROR);

        for(Announcement a :announcements)
            announcementDTOS.add(announcementMapper.toDto(a));

        return new ResponseEntity(announcementDTOS,HttpStatus.OK);
    }
}
