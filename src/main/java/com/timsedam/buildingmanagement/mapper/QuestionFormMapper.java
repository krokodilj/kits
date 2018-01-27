package com.timsedam.buildingmanagement.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.QuestionFormCreateDTO;
import com.timsedam.buildingmanagement.dto.response.QuestionFormDTO;
import com.timsedam.buildingmanagement.model.Question;
import com.timsedam.buildingmanagement.model.QuestionForm;
import com.timsedam.buildingmanagement.model.QuestionFormStatus;

@Component
public class QuestionFormMapper {
	
	public QuestionForm toModel(QuestionFormCreateDTO dto) {
		QuestionForm form = new QuestionForm();
		form.setOpenedAt(LocalDateTime.now());
		form.setTitle(dto.getTitle());
		form.setContent(dto.getContent());
		form.setQuestions(new ArrayList<Question>());
		form.setStatus(QuestionFormStatus.OPEN);
		return form;
	}
	
	public QuestionFormDTO toDto(QuestionForm form) {
		QuestionFormDTO dto = new QuestionFormDTO();
		dto.setId(form.getId());
		
		ArrayList<Long> questionIds = new ArrayList<Long>();
		for(Question question : form.getQuestions())
			questionIds.add(question.getId());
		dto.setQuestions(questionIds);
		
		dto.setCreator(form.getCreator().getId());
		dto.setTitle(form.getTitle());
		dto.setContent(form.getContent());
		dto.setStatus(form.getStatus().toString());
		dto.setOpenedAt(form.getOpenedAt());
		dto.setStartedAt(form.getStartedAt());
		dto.setClosedAt(form.getClosedAt());
		return dto;
	}

}
