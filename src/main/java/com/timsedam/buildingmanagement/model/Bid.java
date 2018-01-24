package com.timsedam.buildingmanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Bid {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String description;
	
	private double price;
	
	@ManyToOne
	private Company company;
	
	@ManyToOne
	private Report reportBid;
	
	private String status;

	public Bid() {
	}

	public Bid(String description, double price, Company company, Report report, String status) {
		super();
		this.description = description;
		this.price = price;
		this.company = company;
		this.reportBid = report;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Report getReport() {
		return reportBid;
	}

	public void setReport(Report report) {
		this.reportBid = report;
	}

	public Report getReportBid() {
		return reportBid;
	}

	public void setReportBid(Report reportBid) {
		this.reportBid = reportBid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
