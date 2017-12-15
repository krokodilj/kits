package com.timsedam.buildingmanagement.dto;

public class AcceptBidDTO {

	private long bid;
	
	public AcceptBidDTO(){}
	
	public AcceptBidDTO(long bid){
		this.bid = bid;
	}

	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}
	
	
}
