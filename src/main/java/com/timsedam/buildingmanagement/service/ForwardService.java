package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.model.Forward;
import com.timsedam.buildingmanagement.repository.ForwardRepository;

@Service
public class ForwardService {

	@Autowired
    ForwardRepository forwardRepository;
	
	public Forward save(Forward forward) {
		return forwardRepository.save(forward);
	}

}
