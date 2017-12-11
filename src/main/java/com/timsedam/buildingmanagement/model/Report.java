package com.timsedam.buildingmanagement.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.catalina.Manager;

@Entity
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	private User sender;
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
	@ManyToOne
	private User currentHolder;
	
	public Report() {
		super();
	}

	public Report(User sender, ReportStatus status, String description, Building location, String photo,
			List<Comment> comments, List<Forward> forwards, User currentHolder) {
		super();
		this.sender = sender;
		this.status = status;
		this.description = description;
		this.location = location;
		this.photo = photo;
		this.comments = comments;
		this.forwards = forwards;
		this.currentHolder = currentHolder;
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

	public User getCurrentHolder() {
		return currentHolder;
	}

	public void setCurrentHolder(User currentHolder) {
		this.currentHolder = currentHolder;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", sender=" + sender + ", status=" + status + ", description=" + description
				+ ", location=" + location + ", photo=" + photo + ", comments=" + comments + ", forwards=" + forwards
				+ ", currentHolder=" + currentHolder + "]";
	}
	
	
}
