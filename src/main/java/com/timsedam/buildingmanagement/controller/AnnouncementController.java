package com.timsedam.buildingmanagement.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.timsedam.buildingmanagement.exceptions.UserExistsException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.exceptions.UserNotResidentException;
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
     * @throws UserNotResidentException 
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(Principal principal, @Valid @RequestBody CreateAnnouncementDTO createAnnouncementDTO,
    		BindingResult validationResult)	throws BuildingMissingException, UserMissingException, UserNotResidentException {
    	
    	if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}

        Building building = buildingService.findOne(createAnnouncementDTO.getBuilding());
        User user =  userService.findOneByUsername(principal.getName());

        Announcement announcement = announcementMapper.toModel(createAnnouncementDTO,building,user);
        announcement = announcementService.create(announcement);

        return new ResponseEntity<Long>(announcement.getId(), HttpStatus.CREATED);
    }

    /**
     * Get announcements by building
     * @param buildingId
     * @param page default 0
     * @param count default 5
     * @return List<AnnouncementDTO>
     * @throws BuildingMissingException 
     * @throws UserMissingException 
     * @throws UserNotResidentException 
     */
    @GetMapping(value="/by_building/{buildingId}")
    public ResponseEntity<?> getByBuilding(Principal principal, @PathVariable long buildingId, 
    		@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "5") int count) 
    			throws BuildingMissingException, UserMissingException, UserNotResidentException {
    	
        Building building = buildingService.findOne(buildingId);
        User user = userService.findOneByUsername(principal.getName());
        
        List<Announcement> announcements = announcementService.findAllByBuilding(user, building, page, count);
        ArrayList<AnnouncementDTO> dtos = announcementMapper.toDto(announcements);

        return new ResponseEntity<List<AnnouncementDTO>>(dtos, HttpStatus.OK);
    }
    
	/**
	 * Handles BuildingMissingException that can happen when calling BuildingService.findOne(buildingId)
	 */
	@ExceptionHandler(BuildingMissingException.class)
	public ResponseEntity<String> buildingMissingException(final BuildingMissingException e) {
		return new ResponseEntity<String>("Building with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles UserMissingException that can happen when calling UserService.findOneByUsername(username)
	 */
	@ExceptionHandler(UserMissingException.class)
	public ResponseEntity<String> userMissingException(final UserMissingException e) {
		return new ResponseEntity<String>("User with username: " + e.getUsername() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles UserNotResidentException that can happen when calling AnnouncementService.create(announcement)
	 */
	@ExceptionHandler(UserNotResidentException.class)
	public ResponseEntity<String> userNotResidentException(final UserNotResidentException e) {
		return new ResponseEntity<String>("User with id: " + e.getUserId() + " is not a Resident or Owner "
				+ "in Building with id: " + e.getBuildingId(), HttpStatus.NOT_FOUND);
	}
	
}
