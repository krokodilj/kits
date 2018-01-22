package com.timsedam.buildingmanagement.dto.request;

import javax.validation.constraints.NotNull;

public class CommentDTO {

	@NotNull(message = "{data.empty}")
	private String data;
	
	private long report;
	
	public CommentDTO(){}
	
	public CommentDTO(String data, long report){
		this.data = data;
		this.report = report;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getReport() {
		return report;
	}

	public void setReport(long report) {
		this.report = report;
	}
	
	
	
	
}
