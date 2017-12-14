package com.timsedam.buildingmanagement.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private String status;
	private String description;
	@ManyToOne
	private Building location;
	@ElementCollection
	Map<Integer, String> photos;
	@OneToMany(mappedBy = "reportCommented")
	private List<Comment> comments;
	@OneToMany(mappedBy = "forwardedReport", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Forward> forwards;
	
	public Report() {
		super();
	}

	public Report(User sender, String status, String description, Building location, List<String> photos,
			List<Comment> comments, List<Forward> forwards) {
		super();
		this.sender = sender;
		this.status = status;
		this.description = description;
		this.location = location;
		this.photos = new HashMap<Integer, String>();
		for(int i=0; i<photos.size(); i++)
			this.photos.put(i, photos.get(i));
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public Map<Integer, String> getPhotos() {
		return photos;
	}

	public void setPhotos(Map<Integer, String> photos) {
		this.photos = photos;
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
		return "Report [id=" + id + ", sender=" + sender + ", status=" + status + ", description=" + description
				+ ", location=" + location + ", photos=" + photos + ", comments=" + comments + ", forwards=" + forwards
				+ "]";
	}
	
	
}
