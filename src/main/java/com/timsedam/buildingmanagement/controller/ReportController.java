package com.timsedam.buildingmanagement.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.CreateReportDTO;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Comment;
import com.timsedam.buildingmanagement.model.Forward;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.model.Resident;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.service.ReportService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value = "/api/reports/")
public class ReportController {

	@Autowired
	private UserService userService;

	@Autowired
	private BuildingService buildingService;

	@Autowired
	private ReportService reportService;

	@SuppressWarnings("rawtypes")
	@PostMapping(value="create", consumes = "application/json")
	public ResponseEntity create(Principal principal, @RequestBody CreateReportDTO reportDTO) {

		Resident sender = (Resident) userService.findByUsername(principal.getName());

		Building building = buildingService.findOneById(reportDTO.getBuilding());
		if (!sender.isResident(building.getAddress())) {
			return new ResponseEntity<>("Sender isn't resident of building!", HttpStatus.CONFLICT);
		}

		Report report = new Report(sender, "OPEN", reportDTO.getDescription(), building,
				reportDTO.getPhotos(), new ArrayList<Comment>(), new ArrayList<Forward>());
		Forward forward = new Forward(null, building.getManager(), report);
		List<Forward> forwards = new ArrayList<Forward>();
		forwards.add(forward);
		report.setForwards(forwards);
		report = reportService.save(report);
		

		return new ResponseEntity<>(report.getId(), HttpStatus.CREATED);
	}
}
