package com.timsedam.buildingmanagement.controller;

import java.security.Principal;
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

import com.timsedam.buildingmanagement.dto.request.MeetingCreateDTO;
import com.timsedam.buildingmanagement.dto.response.MeetingDTO;
import com.timsedam.buildingmanagement.exceptions.InvalidTimeException;
import com.timsedam.buildingmanagement.exceptions.MeetingMissingException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.exceptions.UserNotManagerException;
import com.timsedam.buildingmanagement.mapper.MeetingMapper;
import com.timsedam.buildingmanagement.model.Meeting;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.MeetingService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value="/api/meetings")
public class MeetingController {
	
	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MeetingMapper meetingMapper;
				
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> create(@Valid @RequestBody MeetingCreateDTO meetingCreateDTO, BindingResult validationResult, Principal principal)
			throws ClassNotFoundException, UserMissingException, UserNotManagerException, InvalidTimeException {
		
		User requestSender = userService.findOneByUsername(principal.getName());
		
		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else {
			Meeting meeting = meetingMapper.toModel(meetingCreateDTO);
			meeting = meetingService.create(meeting, requestSender);
			
			return new ResponseEntity<Long>(meeting.getId(), HttpStatus.CREATED);
		}
		
	}
	
	@GetMapping(value = "/{meetingId}", produces = "application/json")
	public ResponseEntity<MeetingDTO> getOne(@PathVariable Long meetingId) throws ClassNotFoundException, MeetingMissingException {
		Meeting meeting = meetingService.findOne(meetingId);
		MeetingDTO responseData = meetingMapper.toDto(meeting);
		return new ResponseEntity<MeetingDTO>(responseData, HttpStatus.OK);
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<MeetingDTO>> getAllByBuildingId(@RequestParam Long buildingId) {	
		List<Meeting> meetings = meetingService.getAllByBuildingId(buildingId);

		List<MeetingDTO> responseData = meetingMapper.toDto(meetings);
		return new ResponseEntity<List<MeetingDTO>>(responseData, HttpStatus.OK);
		
	}
	
	/**
	 * Handles UserMissingException that can happen when calling UserService.findOne(userId)
	 */
	@ExceptionHandler(UserMissingException.class)
	public ResponseEntity<String> userMissingException(final UserMissingException e) {
		return new ResponseEntity<String>("User with id: " + e.getUsername() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles UserNotManagerException that can happen when calling MeetingService.create(meeting)
	 */
	@ExceptionHandler(UserNotManagerException.class)
	public ResponseEntity<String> userNotManagerException(final UserNotManagerException e) {
		return new ResponseEntity<String>("User with id: " + e.getUserId() + 
				" is not the Manager of Building with id: " + e.getBuildingId(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	/**
	 * Handles InvalidTimeException that can happen when calling MeetingService.create(meeting)
	 */
	@ExceptionHandler(InvalidTimeException.class)
	public ResponseEntity<String> invalidTimeException(final InvalidTimeException e) {
		return new ResponseEntity<String>("Time provided is in the past.", HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	/**
	 * Handles MeetingMissingException that can happen when calling MeetingService.findOne(meetingId)
	 */
	@ExceptionHandler(MeetingMissingException.class)
	public ResponseEntity<String> meetingMissingException(final MeetingMissingException e) {
		return new ResponseEntity<String>("Meeting with id: " + e.getMeetingId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
}
