package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.OpenEndedQuestionCreateDTO;
import com.timsedam.buildingmanagement.model.Answer;
import com.timsedam.buildingmanagement.model.OpenEndedQuestion;

@Component
public class OpenEndedQuestionMapper {
	
	public OpenEndedQuestion toModel(OpenEndedQuestionCreateDTO dto) {
		OpenEndedQuestion model = new OpenEndedQuestion();
		model.setQuestionText(dto.getQuestionText());
		model.setUsersAnswers(new ArrayList<Answer>());
		return model;
	}

}
