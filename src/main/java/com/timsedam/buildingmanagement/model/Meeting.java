package com.timsedam.buildingmanagement.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Meeting {
	
	@Id
	@GeneratedValue
	private Long id;
	private LocalDateTime startsAt;
	private String record;
	@ManyToOne
	private Building building;
	private String location;
	@OneToMany
	private List<Proposal> acceptedProposals;
	
	public Meeting() {
		super();
	}

	public Meeting(LocalDateTime startsAt, String record, Building building, String location,
			List<Proposal> acceptedProposals) {
		super();
		this.startsAt = startsAt;
		this.record = record;
		this.building = building;
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

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Proposal> getAcceptedProposals() {
		return acceptedProposals;
	}

	public void setAcceptedProposals(List<Proposal> acceptedProposals) {
		this.acceptedProposals = acceptedProposals;
	}

	@Override
	public String toString() {
		return "Meeting [id=" + id + ", startsAt=" + startsAt + ", record=" + record + ", building=" + building
				+ ", location=" + location + ", acceptedProposals=" + acceptedProposals + "]";
	}
	
}
