package com.timsedam.buildingmanagement.dto.response;

import javax.validation.constraints.Min;

public class BidDTO {

	@Min(value = 1, message = "{price.negative}")
	private double price;
	private String description;
	private long report;
	
	public BidDTO(){}
	
	public BidDTO(String description, double price, long report){
		this.description = description;
		this.price = price;
		this.report = report;
	}

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

	public long getReport() {
		return report;
	}

	public void setReport(long report) {
		this.report = report;
	}
	
	
}
