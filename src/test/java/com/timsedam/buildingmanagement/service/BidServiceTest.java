package com.timsedam.buildingmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.timsedam.buildingmanagement.exceptions.BidMissingException;
import com.timsedam.buildingmanagement.exceptions.InvalidStatusException;
import com.timsedam.buildingmanagement.exceptions.UserNotReportHolderException;
import com.timsedam.buildingmanagement.model.Bid;
import com.timsedam.buildingmanagement.model.Company;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.BidRepository;
import com.timsedam.buildingmanagement.repository.ReportRepository;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BidServiceTest {
	
	@Autowired
    private BidService bidService;

	@Autowired
	private BidRepository bidRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReportRepository reportRepository;
	
	@Test
    @Transactional
    @Rollback(true)
    public void testCreate() {
		Bid bid = new Bid();
		bid.setDescription("description");
		bid.setPrice(1000);
		bid.setCompany((Company) userRepository.findByUsername("company1"));
		bid.setReport(reportRepository.findOne(1L));
		bid.setStatus("OPEN");
		
    	long numBidsBeforeCreate = bidRepository.count();
    	
    	Bid result = bidService.save(bid);
    	assertThat(result).isNotNull();
    	
    	assertThat(bidRepository.count()).isEqualTo(numBidsBeforeCreate + 1);
    	Bid bidFromDB = bidRepository.findOne(result.getId());
    	assertThat(bidFromDB.getDescription()).isEqualTo(bid.getDescription());
    	assertThat(bidFromDB.getPrice()).isEqualTo(bid.getPrice());
    	assertThat(bidFromDB.getCompany().getId()).isEqualTo(bid.getCompany().getId());
    	assertThat(bidFromDB.getReport().getId()).isEqualTo(bid.getReport().getId());
    	assertThat(bidFromDB.getStatus()).isEqualTo(bid.getStatus());
    }
	
	@Test
    @Transactional
    public void testFindOne() throws BidMissingException {
		Bid result = bidService.findOne(1L);
		assertThat(result).isNotNull();
    }
	
	@Test(expected = BidMissingException.class)
    @Transactional
    public void testFindOneBidMissing() throws BidMissingException {
		bidService.findOne(99L);
    }
	
	@Test
    @Transactional
    @Rollback(true)
    public void testAcceptBid() throws UserNotReportHolderException, InvalidStatusException {
		Bid bid = bidRepository.findOne(1L);
		User reportHolder = bid.getReport().getCurrentHolder().getForwardedTo();
		
		assertThat(bid.getStatus()).isNotEqualTo("ACCEPTED");
		
    	bidService.acceptBid(reportHolder, bid);
    	Bid bidFromRepository = bidRepository.findOne(1L);
    	
    	assertThat(bidFromRepository.getStatus()).isEqualTo("ACCEPTED");
    }
	
	@Test(expected = UserNotReportHolderException.class)
	@Transactional
	public void testAcceptBidUserNotHolder() throws UserNotReportHolderException, InvalidStatusException {
		Bid bid = bidRepository.findOne(1L);
		User user = userRepository.findOne(30L);
		
		bidService.acceptBid(user, bid);
	}
	
	@Test(expected = InvalidStatusException.class)
	@Transactional
	public void testAcceptBidAlreadyAccepted() throws UserNotReportHolderException, InvalidStatusException {
		Bid bid = bidRepository.findOne(5L);
		User user = userRepository.findOne(15L);
		
		bidService.acceptBid(user, bid);
	}
	
}
