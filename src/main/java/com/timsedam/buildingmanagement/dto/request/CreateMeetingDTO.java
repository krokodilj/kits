package com.timsedam.buildingmanagement.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class CreateMeetingDTO {
	
	@NotNull(message = "{startTime.emtpy}")
	private LocalDateTime startsAt;
	
	@NotNull(message = "{buildingId.empty}")
	private Long buildingId;
	
	private String location;
	
	public CreateMeetingDTO() {
		super();
	}

	public CreateMeetingDTO(LocalDateTime startsAt, Long buildingId, String location) {
		super();
		this.startsAt = startsAt;
		this.buildingId = buildingId;
		this.location = location;
	}
	
	public CreateMeetingDTO(CreateMeetingDTO meetingCreateDTO) {
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
