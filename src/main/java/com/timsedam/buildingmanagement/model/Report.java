package com.timsedam.buildingmanagement.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Manager originalReceiver;
	private Manager currentHolder;
	private ReportStatus status;
	private String description;
	private Building location;
	private String photo;
	@OneToMany
	private List<Comment> comments;
	
	public Report() {}

	public Report(Manager originalReceiver, Manager currentHolder, ReportStatus status, String description,
			Building location, String photo, List<Comment> comments) {
		super();
		this.originalReceiver = originalReceiver;
		this.currentHolder = currentHolder;
		this.status = status;
		this.description = description;
		this.location = location;
		this.photo = photo;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Manager getOriginalReceiver() {
		return originalReceiver;
	}

	public void setOriginalReceiver(Manager originalReceiver) {
		this.originalReceiver = originalReceiver;
	}

	public Manager getCurrentHolder() {
		return currentHolder;
	}

	public void setCurrentHolder(Manager currentHolder) {
		this.currentHolder = currentHolder;
	}

	public ReportStatus getStatus() {
		return status;
	}

	public void setStatus(ReportStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Building getLocation() {
		return location;
	}

	public void setLocation(Building location) {
		this.location = location;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", originalReceiver=" + originalReceiver + ", currentHolder=" + currentHolder
				+ ", status=" + status + ", description=" + description + ", location=" + location + ", photo=" + photo
				+ ", comments=" + comments + "]";
	}
	
}
