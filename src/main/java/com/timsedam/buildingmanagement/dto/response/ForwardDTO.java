package com.timsedam.buildingmanagement.dto.response;

public class ForwardDTO {

	private Long id;
	private UserDTO forwarder;
	private UserDTO forwardedTo;
	
	public ForwardDTO(){}

	public ForwardDTO(Long id, UserDTO forwarder, UserDTO forwardedTo) {
		super();
		this.id = id;
		this.forwarder = forwarder;
		this.forwardedTo = forwardedTo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getForwarder() {
		return forwarder;
	}

	public void setForwarder(UserDTO forwarder) {
		this.forwarder = forwarder;
	}

	public UserDTO getForwardedTo() {
		return forwardedTo;
	}

	public void setForwardedTo(UserDTO forwardedTo) {
		this.forwardedTo = forwardedTo;
	}
	
	
}
