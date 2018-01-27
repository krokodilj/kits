package com.timsedam.buildingmanagement.controller;

import java.security.Principal;

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
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.request.QuestionFormCreateDTO;
import com.timsedam.buildingmanagement.dto.response.QuestionFormDTO;
import com.timsedam.buildingmanagement.exceptions.BuildingMissingException;
import com.timsedam.buildingmanagement.exceptions.FormMissingException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.exceptions.UserNotManagerException;
import com.timsedam.buildingmanagement.exceptions.UserNotResidentOrApartmentOwnerException;
import com.timsedam.buildingmanagement.mapper.QuestionFormMapper;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.QuestionForm;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.service.QuestionFormService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value="/api/question_forms/")
public class QuestionFormController {
	
	@Autowired
	private QuestionFormService questionFormService;
	
	@Autowired
	private QuestionFormMapper questionFormMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BuildingService buildingService;
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> register(@Valid @RequestBody QuestionFormCreateDTO questionFormCreateDTO, BindingResult validationResult, Principal principal) 
			throws UserMissingException, BuildingMissingException, UserNotManagerException {

		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		else {
			User creator = userService.findOneByUsername(principal.getName());
			Building building = buildingService.findOne(questionFormCreateDTO.getBuildingId());
			QuestionForm questionForm = questionFormMapper.toModel(questionFormCreateDTO);
			QuestionForm savedForm = questionFormService.create(questionForm, building, creator);
			
			return new ResponseEntity<Long>(savedForm.getId(), HttpStatus.CREATED);
		}
	}
	
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable long id) throws FormMissingException {
        QuestionForm form = questionFormService.findOneById(id);
        QuestionFormDTO formDTO = questionFormMapper.toDto(form);
        return new ResponseEntity<QuestionFormDTO>(formDTO, HttpStatus.OK);
    }
	
    /**
	 * Handles UserMissingException that can happen when calling UserService.findOne(userId)
	 */
	@ExceptionHandler(UserMissingException.class)
	public ResponseEntity<String> userMissingException(final UserMissingException e) {
		return new ResponseEntity<String>("User with ID: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles BuildingMissingException that can happen when calling BuildingService.findOne(buildingId)
	 */
	@ExceptionHandler(BuildingMissingException.class)
	public ResponseEntity<String> buildingMissingException(final BuildingMissingException e) {
		return new ResponseEntity<String>("Building with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles UserNotManagerException that can happen when calling QuestionService.create(questionForm)
	 */
	@ExceptionHandler(UserNotManagerException.class)
	public ResponseEntity<String> userNotManagerException(final UserNotManagerException e) {
		return new ResponseEntity<String>("User with id: " + e.getUserId() + " is not a Manager"
				+ " in Building with id: " + e.getBuildingId(), HttpStatus.UNPROCESSABLE_ENTITY);
	} 
	
    /**
	 * Handles FormMissingException that can happen when calling QuestionFormService.findOne(userId)
	 */
	@ExceptionHandler(FormMissingException.class)
	public ResponseEntity<String> formMissingException(final FormMissingException e) {
		return new ResponseEntity<String>("QuestionForm with id: " + e.getFormId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
}
