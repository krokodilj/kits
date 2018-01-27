package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.ChoiceQuestionCreateDTO;
import com.timsedam.buildingmanagement.model.Answer;
import com.timsedam.buildingmanagement.model.ChoiceQuestion;

@Component
public class ChoiceQuestionMapper {
	
	@Autowired
	private OfferedAnswerMapper offeredAnswerMapper;
	
	public ChoiceQuestion toModel(ChoiceQuestionCreateDTO dto) {
		ChoiceQuestion model = new ChoiceQuestion();
		model.setQuestionText(dto.getQuestionText());
		model.setOfferedAnswers(offeredAnswerMapper.toModel(dto.getOfferedAnswers(), model));
		model.setUsersAnswers(new ArrayList<Answer>());
		return model;
	}
 
}
