package com.timsedam.buildingmanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.request.ChoiceQuestionCreateDTO;
import com.timsedam.buildingmanagement.dto.request.OpenEndedQuestionCreateDTO;
import com.timsedam.buildingmanagement.exceptions.FormMissingException;
import com.timsedam.buildingmanagement.mapper.ChoiceQuestionMapper;
import com.timsedam.buildingmanagement.mapper.OpenEndedQuestionMapper;
import com.timsedam.buildingmanagement.model.ChoiceQuestion;
import com.timsedam.buildingmanagement.model.OpenEndedQuestion;
import com.timsedam.buildingmanagement.model.Question;
import com.timsedam.buildingmanagement.service.QuestionFormService;
import com.timsedam.buildingmanagement.service.QuestionService;

@RestController
@RequestMapping(value = "/api/questions/")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private ChoiceQuestionMapper choiceQuestionMapper;
	
	@Autowired
	private OpenEndedQuestionMapper opQuestionMapper;
	
	@Autowired
	private QuestionFormService questionFormService;
	
	@PostMapping(value = "choice_question/", consumes = "application/json", produces = "text/plain")
	public ResponseEntity<String> createChoiceQuestion(@Valid @RequestBody ChoiceQuestionCreateDTO dto,
			BindingResult validationResult) throws FormMissingException {

		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		else {
			ChoiceQuestion question = choiceQuestionMapper.toModel(dto);
			question.setQuestionForm(questionFormService.findOneById(dto.getQuestionForm()));
			
			Question result = questionService.save(question);
			
			return new ResponseEntity<String>(result.getId().toString(), HttpStatus.CREATED);
		}
	}
	
	@PostMapping(value = "open_ended_question/", consumes = "application/json", produces = "text/plain")
	public ResponseEntity<String> createOpenEndedQuestion(@Valid @RequestBody OpenEndedQuestionCreateDTO dto,
			BindingResult validationResult) throws FormMissingException {

		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		else {
			OpenEndedQuestion question = opQuestionMapper.toModel(dto);
			question.setQuestionForm(questionFormService.findOneById(dto.getQuestionForm()));
			
			Question result = questionService.save(question);
			
			return new ResponseEntity<String>(result.getId().toString(), HttpStatus.CREATED);
		}
	}
	
	/**
	 * Handles FormMissingException that can happen when calling QuestionFormService.findByName(questionFormId)
	 */
	@ExceptionHandler(FormMissingException.class)
	public ResponseEntity<String> formMissingException(final FormMissingException e) {
		return new ResponseEntity<String>("QuestionForm with id: " + e.getFormId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}

}
