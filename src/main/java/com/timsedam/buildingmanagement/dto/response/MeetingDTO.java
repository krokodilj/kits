package com.timsedam.buildingmanagement.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingDTO {
	
	private Long id;
	private LocalDateTime startsAt;
	private String record;
	private String location;
	private List<Long> acceptedProposals;
	
	public MeetingDTO() {
		super();
	}

	public MeetingDTO(Long id, LocalDateTime startsAt, String record, String location, List<Long> acceptedProposals) {
		super();
		this.id = id;
		this.startsAt = startsAt;
		this.record = record;
		this.location = location;
		this.acceptedProposals = acceptedProposals;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(LocalDateTime startsAt) {
		this.startsAt = startsAt;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Long> getAcceptedProposals() {
		return acceptedProposals;
	}

	public void setAcceptedProposals(List<Long> acceptedProposals) {
		this.acceptedProposals = acceptedProposals;
	}

	@Override
	public String toString() {
		return "MeetingDTO [id=" + id + ", startsAt=" + startsAt + ", record=" + record + ", location=" + location
				+ ", acceptedProposals=" + acceptedProposals + "]";
	}	

}
