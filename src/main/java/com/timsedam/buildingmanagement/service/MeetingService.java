package com.timsedam.buildingmanagement.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.InvalidTimeException;
import com.timsedam.buildingmanagement.exceptions.MeetingMissingException;
import com.timsedam.buildingmanagement.exceptions.UserNotManagerException;
import com.timsedam.buildingmanagement.model.Meeting;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.MeetingRepository;

@Service
public class MeetingService {
	
	@Autowired
	private BuildingService buildingService;

	@Autowired
	private MeetingRepository meetingRepository;
	
	public void save(Meeting meeting) {
		meetingRepository.save(meeting);
	}
	
	public Meeting create(Meeting meeting, User meetingScheduler) throws UserNotManagerException, InvalidTimeException {
		if(!buildingService.isManager(meeting.getBuilding(), meetingScheduler))
			throw new UserNotManagerException(meetingScheduler.getId(), meeting.getBuilding().getId());
		if(meeting.getStartsAt().isBefore(LocalDateTime.now()))
			throw new InvalidTimeException();
		
		return meetingRepository.save(meeting);
	}
	
	public Meeting findOne(Long meetingId) throws MeetingMissingException {
		Meeting meeting = meetingRepository.findOne(meetingId);
		if(meeting == null)
			throw new MeetingMissingException(meetingId);
		else
			return meeting;
	}
	
	public Meeting findActive(Long buildingId) {
		List<Meeting> allMeetings = meetingRepository.findAllByBuildingId(buildingId);
		LocalDateTime currentTime = LocalDateTime.now();
		Meeting activeMeeting = null;
		Long minValue = 100000000L;
		for(Meeting meeting : allMeetings) {
			if(meeting.getStartsAt().isBefore(currentTime) &&
					currentTime.minusHours(48).isBefore(meeting.getStartsAt()) &&
					meeting.getStartsAt().until(currentTime, ChronoUnit.HOURS) < minValue) {
				minValue = meeting.getStartsAt().until(currentTime, ChronoUnit.HOURS);
				activeMeeting = meeting;
			}
		}
		return activeMeeting;
	}
	
    public List<Meeting> getAllByBuildingId(Long buildingId) {
    	return meetingRepository.findAllByBuildingId(buildingId);
	}

	public List<Meeting> getAllByManagerId(Long managerId) {
		return meetingRepository.findAllByManagerId(managerId);
	}
	
}
