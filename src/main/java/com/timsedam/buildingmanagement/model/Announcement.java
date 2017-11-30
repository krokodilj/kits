package com.timsedam.buildingmanagement.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Announcement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	private String content;
	private LocalDateTime postedAt;
	@ManyToOne
	private User poster;
	@ManyToOne
	private Building building;
	
	public Announcement() {
		super();
	}

	public Announcement(String content, LocalDateTime postedAt, User poster, Building building) {
		super();
		this.content = content;
		this.postedAt = postedAt;
		this.poster = poster;
		this.building = building;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(LocalDateTime postedAt) {
		this.postedAt = postedAt;
	}

	public User getPoster() {
		return poster;
	}

	public void setPoster(User poster) {
		this.poster = poster;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	@Override
	public String toString() {
		return "Announcement [id=" + id + ", content=" + content + ", postedAt=" + postedAt + ", poster=" + poster
				+ ", building=" + building + "]";
	}
	
}
