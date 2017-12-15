package com.timsedam.buildingmanagement.controller;

import java.security.Principal;
import java.time.LocalDateTime;
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

import com.timsedam.buildingmanagement.dto.MeetingCreateDTO;
import com.timsedam.buildingmanagement.dto.MeetingDTO;
import com.timsedam.buildingmanagement.model.Meeting;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.MeetingService;
import com.timsedam.buildingmanagement.service.UserService;
import com.timsedam.buildingmanagement.util.mappers.MeetingMapper;

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
	public ResponseEntity<MeetingDTO> create(@Valid @RequestBody MeetingCreateDTO meetingCreateDTO,
			BindingResult validationResult, Principal principal) throws ClassNotFoundException {
		
		User requestSender = userService.findOneByUsername(principal.getName());
		
		if (validationResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if(!userService.isManager(requestSender.getId(), meetingCreateDTO.getBuildingId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else if(meetingCreateDTO.getStartsAt().isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			
			Meeting meeting = meetingMapper.toModel(meetingCreateDTO);
			meetingService.save(meeting);
			
			MeetingDTO responseData = meetingMapper.toDto(meeting);
			
			return new ResponseEntity<MeetingDTO>(responseData, HttpStatus.CREATED);
		}
		
	}
	
	@GetMapping(value = "/{meetingId}", produces = "application/json")
	public ResponseEntity<MeetingDTO> getOne(@PathVariable Long meetingId) throws ClassNotFoundException {
		
		Meeting meeting = meetingService.get(meetingId);
		if(meeting == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else {
			MeetingDTO responseData = meetingMapper.toDto(meeting);
			return new ResponseEntity<MeetingDTO>(responseData, HttpStatus.OK);
		}
		
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<MeetingDTO>> getAllByBuildingId(@RequestParam Long buildingId) {	
		List<Meeting> meetings = meetingService.getAllByBuildingId(buildingId);

		List<MeetingDTO> responseData = new ArrayList<>();
		for(Meeting meeting : meetings)
			responseData.add(meetingMapper.toDto(meeting));
		return new ResponseEntity<List<MeetingDTO>>(responseData, HttpStatus.OK);
		
	}
	
	

}
