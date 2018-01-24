package com.timsedam.buildingmanagement.dto.request;

import javax.validation.constraints.NotNull;

public class ForwardCreateDTO {

	@NotNull(message = "senderId not provided")
	private long to;
	
	@NotNull(message = "receiverId not provided")
	private long report;
	
	public ForwardCreateDTO(){}
	
	public ForwardCreateDTO(long to, long report){
		this.to = to;
		this.report = report;
	}

	public long getTo() {
		return to;
	}

	public void setTo(long to) {
		this.to = to;
	}

	public long getReport() {
		return report;
	}

	public void setReport(Long report) {
		this.report = report;
	}
	
	
}
