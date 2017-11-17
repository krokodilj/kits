package com.timsedam.buildingmanagement.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Meeting {
	
	@Id
	@GeneratedValue
	private Long id;
	private List<Proposal> agenda;
	private LocalDateTime startTime;
	private String record;
	
	public Meeting() {}

	public Meeting(Long id, List<Proposal> agenda, LocalDateTime startTime, String record) {
		super();
		this.id = id;
		this.agenda = agenda;
		this.startTime = startTime;
		this.record = record;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Proposal> getAgenda() {
		return agenda;
	}

	public void setAgenda(List<Proposal> agenda) {
		this.agenda = agenda;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	@Override
	public String toString() {
		return "Meeting [id=" + id + ", agenda=" + agenda + ", startTime=" + startTime + ", record=" + record + "]";
	}
	
}
