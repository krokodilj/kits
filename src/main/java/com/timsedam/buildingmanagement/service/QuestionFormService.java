package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.FormMissingException;
import com.timsedam.buildingmanagement.exceptions.UserNotManagerException;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.QuestionForm;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.QuestionFormRepository;

@Service
public class QuestionFormService {
	
	@Autowired
	private QuestionFormRepository questionFormRepository;
	
	@Autowired
	private BuildingService buildingService;
	
	public QuestionForm create(QuestionForm form, Building building, User creator) throws UserNotManagerException {
		if(!buildingService.isManager(building, creator))
			throw new UserNotManagerException(creator.getId(), building.getId());
		
		form.setBuilding(building);
		form.setCreator(creator);
		return questionFormRepository.save(form);
	}
	
	public QuestionForm findOneById(Long id) throws FormMissingException {
		QuestionForm form = questionFormRepository.findOne(id);
		if(form == null)
			throw new FormMissingException(id);
		else
			return form;
	}

}
