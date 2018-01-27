package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.AnswerCreateDTO;
import com.timsedam.buildingmanagement.model.Answer;
import com.timsedam.buildingmanagement.model.ChoiceQuestion;
import com.timsedam.buildingmanagement.model.OfferedAnswer;

@Component
public class OfferedAnswerMapper {
	
	// when called from AnswerController
	public OfferedAnswer toModel(AnswerCreateDTO dto) {
		OfferedAnswer model = new OfferedAnswer();
		model.setContent(dto.getContent());
		return model;
	}
	
	// when called from ChoiceQuestionMapper, they need to be bound together
	public Answer toModel(AnswerCreateDTO dto, ChoiceQuestion question) {
		Answer model = new Answer();
		model.setContent(dto.getContent());
		model.setQuestionAnswered(question);
		return model;
	}
	
	public ArrayList<Answer> toModel(ArrayList<AnswerCreateDTO> dtos, ChoiceQuestion question) {
		ArrayList<Answer> result = new ArrayList<Answer>();
		for(AnswerCreateDTO dto : dtos)
			result.add(toModel(dto, question));
		return result;
	}
	
}
