package com.timsedam.buildingmanagement.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
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

import com.timsedam.buildingmanagement.dto.request.ProposalCreateDTO;
import com.timsedam.buildingmanagement.dto.response.ProposalDTO;
import com.timsedam.buildingmanagement.exceptions.BuildingMissingException;
import com.timsedam.buildingmanagement.exceptions.ReportMissingException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.mapper.ProposalMapper;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Proposal;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.service.ProposalService;
import com.timsedam.buildingmanagement.service.ReportService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value="/api/proposals")
public class ProposalController {
	
	@Autowired
	private ProposalService proposalService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private UserService userService;
	@Autowired
	private BuildingService buildingService;
	@Autowired
	private ProposalMapper proposalMapper;
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<ProposalDTO> create(@Valid @RequestBody ProposalCreateDTO proposalCreateDTO,
			BindingResult validationResult, Principal principal) throws ClassNotFoundException, BuildingMissingException, ReportMissingException, UserMissingException {
		
		User requestSender = userService.findOneByUsername(principal.getName());		
		
		if (validationResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else {
			Building building = buildingService.findOneById(proposalCreateDTO.getBuildingId());
			if(proposalCreateDTO.getAttachedReport() != null) {
				Report attachedReport = reportService.findOne(proposalCreateDTO.getAttachedReport());
				if(!userService.isResidentOrApartmentOwnerInBuilding(requestSender.getId(), attachedReport.getLocation().getId())) {
					// User has sent a Proposal concerning a Report that has nothing to do with his Building or Residence
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				else {
					Proposal proposal = proposalMapper.toModel(proposalCreateDTO, requestSender, building, attachedReport);
					proposalService.save(proposal);
					return new ResponseEntity<ProposalDTO>(proposalMapper.toDto(proposal), HttpStatus.CREATED);
				}
			}
			else {
				Proposal proposal = proposalMapper.toModel(proposalCreateDTO, requestSender, building);
				proposalService.save(proposal);
				return new ResponseEntity<ProposalDTO>(proposalMapper.toDto(proposal), HttpStatus.CREATED);			
			}
		}
	}
	

	@GetMapping(value = "{proposalId}", produces = "application/json")
	public ResponseEntity<ProposalDTO> getOne(@PathVariable Long proposalId) throws ClassNotFoundException {
		
		Proposal proposal = proposalService.get(proposalId);
		try {
			ProposalDTO responseData = proposalMapper.toDto(proposal);
			return new ResponseEntity<ProposalDTO>(responseData, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<ProposalDTO>> getAllByBuildingId(@RequestParam Long buildingId) throws BuildingMissingException {
		Building building = buildingService.findOneById(buildingId);
		List<Proposal> proposals = proposalService.findAllByBuildingId(buildingId);

		List<ProposalDTO> responseData = new ArrayList<>();
		for(Proposal proposal : proposals) {
			responseData.add(proposalMapper.toDto(proposal));
		}
		return new ResponseEntity<List<ProposalDTO>>(responseData, HttpStatus.OK);
		
	}

}
