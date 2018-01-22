package com.timsedam.buildingmanagement.dto.request;

import javax.validation.constraints.NotNull;

public class ForwardDTO {

	@NotNull(message = "{senderId.empty}")
	private long to;
	
	@NotNull(message = "{receiverId.empty}")
	private long report;
	
	public ForwardDTO(){}
	
	public ForwardDTO(long to, long report){
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
