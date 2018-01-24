package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.UserNotReportHolderException;
import com.timsedam.buildingmanagement.model.Forward;
import com.timsedam.buildingmanagement.repository.ForwardRepository;

@Service
public class ForwardService {

	@Autowired
    private ForwardRepository forwardRepository;
	
	public Forward save(Forward forward) {
		return forwardRepository.save(forward);
	}
	
	public Forward create(Forward forward) throws UserNotReportHolderException {
		Long senderId = forward.getForwarder().getId();
		Long reportId = forward.getForwardedReport().getId();
		Long currentHolderId = forward.getForwardedReport().getCurrentHolder().getForwardedTo().getId();
		
		if (senderId != currentHolderId)
			throw new UserNotReportHolderException(senderId, reportId);
		else
			return forwardRepository.save(forward);
	}

}
