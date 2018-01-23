package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.BidMissingException;
import com.timsedam.buildingmanagement.exceptions.InvalidStatusException;
import com.timsedam.buildingmanagement.exceptions.UserNotReportHolderException;
import com.timsedam.buildingmanagement.model.Bid;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.BidRepository;

@Service
public class BidService {
	
	@Autowired
	private ReportService reportService;

	@Autowired
	private BidRepository bidRepository;
	
	public Bid save(Bid bid) {
		return bidRepository.save(bid);
	}

	public Bid findOne(long bidId) throws BidMissingException {
		Bid bid = bidRepository.findOne(bidId);
		if(bid == null)
			throw new BidMissingException(bidId);
		else
			return bid;
	}
	
	public void acceptBid(User user, Bid bid) throws UserNotReportHolderException, InvalidStatusException {
		if(user.getId() != bid.getReport().getCurrentHolder().getForwardedTo().getId())
			throw new UserNotReportHolderException(user.getId(), bid.getReport().getId());
		if(!bid.getStatus().equals("OPEN"))
			throw new InvalidStatusException();
		
		// set status of all Bids for a Report
		setStatus("DECLINED", bid.getReport());
		// accept a particular Bid
		setStatus("ACCEPTED", bid.getId());

		reportService.setStatus("CLOSED", bid.getReport().getId());
	}

	private void setStatus(String status, long id) {
		bidRepository.setStatus(status, id);
	}

	private void setStatus(String status, Report report) {
		bidRepository.setStatus(status, report);
	}
}
