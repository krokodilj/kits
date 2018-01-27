package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.QuestionMissingException;
import com.timsedam.buildingmanagement.model.Question;
import com.timsedam.buildingmanagement.repository.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	public Question save(Question question) {
		return questionRepository.save(question);
	}
	
	public Question findOne(Long id) throws QuestionMissingException {
		Question question = questionRepository.findOne(id);
		if(question == null)
			throw new QuestionMissingException(id);
		else
			return question;
	}
}
