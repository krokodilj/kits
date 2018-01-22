package com.timsedam.buildingmanagement.exceptions;

public class MeetingMissingException extends Exception {
	
	private Long meetingId;

	public MeetingMissingException(Long meetingId) {
		super();
		this.meetingId = meetingId;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	
}
