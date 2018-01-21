package com.timsedam.buildingmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.model.Meeting;
import com.timsedam.buildingmanagement.repository.MeetingRepository;

@Service
public class MeetingService {

	@Autowired
	private MeetingRepository meetingRepository;
	
	public void save(Meeting meeting) {
		meetingRepository.save(meeting);
	}
	
	public Meeting get(Long meetingId) {
		return meetingRepository.findOne(meetingId);
	}
	
	public List<Meeting> getAllByBuildingId(Long buildingId) {
		return meetingRepository.findAllByBuildingId(buildingId);
	}
	
}
