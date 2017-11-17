package com.timsedam.buildingmanagement.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private User commenter;
	@ManyToOne
	private Object targetElement;
	private LocalDateTime postTime;
	
	public Comment() {}
	
	public Comment(User commenter, LocalDateTime postTime, Object targetElement) {
		this.commenter = commenter;
		this.postTime = postTime;
		this.targetElement = targetElement;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getCommenter() {
		return commenter;
	}

	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}

	public Object getTargetElement() {
		return targetElement;
	}

	public void setTargetElement(Object targetElement) {
		this.targetElement = targetElement;
	}

	public LocalDateTime getPostTime() {
		return postTime;
	}

	public void setPostTime(LocalDateTime postTime) {
		this.postTime = postTime;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", commenter=" + commenter + ", postTime=" + postTime + ", targetElement="
				+ targetElement + "]";
	}
	
}
