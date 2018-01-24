package com.timsedam.buildingmanagement.dto.request;

public class BidAcceptDTO {

	private long bid;
	
	public BidAcceptDTO(){}
	
	public BidAcceptDTO(long bid){
		this.bid = bid;
	}

	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}
	
	
}
