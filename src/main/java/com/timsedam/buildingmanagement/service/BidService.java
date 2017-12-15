package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.model.Bid;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.repository.BidRepository;

@Service
public class BidService {

	@Autowired
	private BidRepository bidRepository;
	
	public Bid save(Bid bid) {
		return bidRepository.save(bid);
	}

	public Bid findOne(long bid) {
		return bidRepository.findOne(bid);
	}

	public void setStatus(String status, long id) {
		bidRepository.setStatus(status, id);
	}

	public void setStatus(String status, Report report) {
		bidRepository.setStatus(status, report);
	}
}
