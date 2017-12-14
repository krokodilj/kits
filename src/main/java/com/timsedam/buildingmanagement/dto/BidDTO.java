package com.timsedam.buildingmanagement.dto;

public class BidDTO {

	private String description;
	private double price;
	private Long report;
	
	public BidDTO(){}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getReport() {
		return report;
	}

	public void setReport(Long report) {
		this.report = report;
	}
	
	
}
