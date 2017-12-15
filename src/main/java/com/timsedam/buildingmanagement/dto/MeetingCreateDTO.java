package com.timsedam.buildingmanagement.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class MeetingCreateDTO {
	
	@NotNull
	private LocalDateTime startsAt;
	@NotNull
	private Long buildingId;
	private String location;
	
	public MeetingCreateDTO() {
		super();
	}

	public MeetingCreateDTO(LocalDateTime startsAt, Long buildingId, String location) {
		super();
		this.startsAt = startsAt;
		this.buildingId = buildingId;
		this.location = location;
	}
	
	public MeetingCreateDTO(MeetingCreateDTO meetingCreateDTO) {
		this.startsAt = meetingCreateDTO.getStartsAt();
		this.buildingId = new Long(meetingCreateDTO.getBuildingId());
		this.location = new String(meetingCreateDTO.getLocation());
	}

	public LocalDateTime getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(LocalDateTime startsAt) {
		this.startsAt = startsAt;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "MeetingCreateDTO [startsAt=" + startsAt + ", buildingId=" + buildingId + ", location=" + location + "]";
	}

}
