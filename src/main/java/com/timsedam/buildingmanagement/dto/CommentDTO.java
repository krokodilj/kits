package com.timsedam.buildingmanagement.dto;

public class CommentDTO {

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
