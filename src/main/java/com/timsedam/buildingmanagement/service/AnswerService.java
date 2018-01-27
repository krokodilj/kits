package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.model.Answer;
import com.timsedam.buildingmanagement.model.OfferedAnswer;
import com.timsedam.buildingmanagement.repository.AnswerRepository;

@Service
public class AnswerService {
	
	@Autowired
	private AnswerRepository answerRepsitory;
	
	public Answer save(OfferedAnswer answer) {
		return answerRepsitory.save(answer);
	}
	
	public Answer findOne(Long id) {
		return answerRepsitory.findOne(id);
	}

}
