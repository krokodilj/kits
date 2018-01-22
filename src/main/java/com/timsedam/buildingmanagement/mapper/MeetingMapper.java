package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.CreateMeetingDTO;
import com.timsedam.buildingmanagement.dto.response.MeetingDTO;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Meeting;
import com.timsedam.buildingmanagement.model.Proposal;
import com.timsedam.buildingmanagement.repository.BuildingRepository;

@Component
public class MeetingMapper {
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	public Meeting toModel(CreateMeetingDTO meetingCreateDTO) {
		Building building = buildingRepository.getOne(meetingCreateDTO.getBuildingId());
		
		Meeting meeting = new Meeting();
		meeting.setStartsAt(meetingCreateDTO.getStartsAt());
		meeting.setRecord("");
		meeting.setBuilding(building);
		meeting.setLocation(meetingCreateDTO.getLocation());
		meeting.setAcceptedProposals(new ArrayList<>());
		
		return meeting;
	}

	public MeetingDTO toDto(Meeting meeting) {
		MeetingDTO meetingDTO = new MeetingDTO();
		meetingDTO.setId(meeting.getId());
		meetingDTO.setStartsAt(meeting.getStartsAt());
		meetingDTO.setRecord(meeting.getRecord());
		meetingDTO.setLocation(meeting.getLocation());
		
		ArrayList<Long> acceptedProposalIds = new ArrayList<>();
		for(Proposal acceptedProposal : meeting.getAcceptedProposals())
			acceptedProposalIds.add(acceptedProposal.getId());
		
		meetingDTO.setAcceptedProposals(acceptedProposalIds);
		return meetingDTO;
	}
	
	public List<MeetingDTO> toDto(List<Meeting> meetings) {
		ArrayList<MeetingDTO> dtos = new ArrayList<MeetingDTO>();
		for(Meeting meeting : meetings)
			dtos.add(toDto(meeting));
		return dtos;
	}
	
}
