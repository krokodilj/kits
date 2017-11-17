package com.timsedam.buildingmanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Forward {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private User forwarder;
	@ManyToOne
	private User forwardedTo;
	
	public Forward() {}

	public Forward(Long id, User forwarder, User forwardedTo) {
		this.forwarder = forwarder;
		this.forwardedTo = forwardedTo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getForwarder() {
		return forwarder;
	}

	public void setForwarder(User forwarder) {
		this.forwarder = forwarder;
	}

	public User getForwardedTo() {
		return forwardedTo;
	}

	public void setForwardedTo(User forwardedTo) {
		this.forwardedTo = forwardedTo;
	}

	@Override
	public String toString() {
		return "Forward [id=" + id + ", forwarder=" + forwarder + ", forwardedTo=" + forwardedTo + "]";
	}
	
}
