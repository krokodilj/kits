package com.timsedam.buildingmanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
	private List<String> pictures;
	@OneToMany(mappedBy = "reportCommented")
	private List<Comment> comments;
	@ManyToOne(cascade = CascadeType.ALL)
	private Forward currentHolder;
	@OneToMany(mappedBy = "reportBid")
	private List<Bid> bids;
	
	public Report() {
		super();
	}

	public Report(User sender, String status, String description, Building location, List<String> pictures,
			List<Comment> comments, Forward currentHolder) {
		super();
		this.sender = sender;
		this.status = status;
		this.description = description;
		this.location = location;
		this.pictures = pictures;
		this.comments = comments;
		this.currentHolder = currentHolder;
		this.bids = new ArrayList<Bid>();
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

	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Forward getCurrentHolder() {
		return currentHolder;
	}

	public void setCurrentHolder(Forward currentHolder) {
		this.currentHolder = currentHolder;
	}

	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", sender=" + sender + ", status=" + status + ", description=" + description
				+ ", location=" + location + ", pictures=" + pictures + ", comments=" + comments + ", currentHolder="
				+ currentHolder + "]";
	}

}
