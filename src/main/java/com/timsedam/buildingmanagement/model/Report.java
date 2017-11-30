package com.timsedam.buildingmanagement.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	private User sender;
	@OneToMany
	private List<Manager> receiver;
	@Enumerated(EnumType.STRING)
	private ReportStatus status;
	private String description;
	@ManyToOne
	private Building location;
	private String photo;
	@OneToMany(mappedBy = "reportCommented")
	private List<Comment> comments;
	@OneToMany(mappedBy = "forwardedReport")
	private List<Forward> forwards;
	
	public Report() {
		super();
	}

	public Report(User sender, List<Manager> receiver, ReportStatus status, String description, Building location,
			String photo, List<Comment> comments, List<Forward> forwards) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
		this.description = description;
		this.location = location;
		this.photo = photo;
		this.comments = comments;
		this.forwards = forwards;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public List<Manager> getReceiver() {
		return receiver;
	}

	public void setReceiver(List<Manager> receiver) {
		this.receiver = receiver;
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

	public List<Forward> getForwards() {
		return forwards;
	}

	public void setForwards(List<Forward> forwards) {
		this.forwards = forwards;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", status=" + status
				+ ", description=" + description + ", location=" + location + ", photo=" + photo + ", comments="
				+ comments + ", forwards=" + forwards + "]";
	}

}
