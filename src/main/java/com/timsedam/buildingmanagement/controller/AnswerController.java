package com.timsedam.buildingmanagement.controller;

import java.security.Principal;

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

import com.timsedam.buildingmanagement.dto.request.OfferedAnswerSendDTO;
import com.timsedam.buildingmanagement.exceptions.FormMissingException;
import com.timsedam.buildingmanagement.exceptions.QuestionMissingException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.mapper.OfferedAnswerMapper;
import com.timsedam.buildingmanagement.model.Answer;
import com.timsedam.buildingmanagement.model.ChoiceQuestion;
import com.timsedam.buildingmanagement.model.OfferedAnswer;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.AnswerService;
import com.timsedam.buildingmanagement.service.QuestionService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value = "/api/answers/")
public class AnswerController {
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OfferedAnswerMapper offeredAnswerMapper;
	
	@PostMapping(value = "offered_answer/send/", consumes = "application/json", produces = "text/plain")
	public ResponseEntity<String> sendOfferedAnswer(@Valid @RequestBody OfferedAnswerSendDTO dto,
			BindingResult validationResult, Principal principal) throws FormMissingException, QuestionMissingException, UserMissingException {

		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		else {
			OfferedAnswer offeredAnswer = (OfferedAnswer) answerService.findOne(dto.getAnswerId());
			ChoiceQuestion question = (ChoiceQuestion) questionService.findOne(dto.getQuestionId());
			User answerSender = userService.findOneByUsername(principal.getName());
			
			offeredAnswer.setRespondant(answerSender);
			question.getUsersAnswers().add(offeredAnswer);

			Answer result = answerService.save(offeredAnswer);
			questionService.save(question);
			
			return new ResponseEntity<String>(result.getId().toString(), HttpStatus.CREATED);
		}
	}
	
	/**
	 * Handles QuestionMissingException that can happen when calling QuestionService.findOne(questionId)
	 */
	@ExceptionHandler(QuestionMissingException.class)
	public ResponseEntity<String> questionMissingException(final QuestionMissingException e) {
		return new ResponseEntity<String>("Question with id: " + e.getQuestionId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
}
