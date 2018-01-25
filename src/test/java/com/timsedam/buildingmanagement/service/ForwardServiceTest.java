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

import com.timsedam.buildingmanagement.exceptions.UserNotReportHolderException;
import com.timsedam.buildingmanagement.model.Forward;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.ForwardRepository;
import com.timsedam.buildingmanagement.repository.ReportRepository;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ForwardServiceTest {
	
	@Autowired
    private ForwardService forwardService;
	
	@Autowired
	private ForwardRepository forwardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReportRepository reportRepository;

	@Test
    @Transactional
    @Rollback(true)
    public void testCreate() throws UserNotReportHolderException {
		User forwardSender = userRepository.findOne(11L);
		User forwardReceiver = userRepository.findOne(12L);
		Report forwardedReport = reportRepository.findOne(1L);
		
    	Forward forward = new Forward();
    	forward.setForwarder(forwardSender);
    	forward.setForwardedTo(forwardReceiver);
    	forward.setForwardedReport(forwardedReport);
    	
    	long numForwardsBeforeCreate = forwardRepository.count();
    	
    	Forward result = forwardService.create(forward);
    	assertThat(result).isNotNull();
    	
    	assertThat(forwardRepository.count()).isEqualTo(numForwardsBeforeCreate + 1);
    	Forward forwardFromDB = (Forward)forwardRepository.findOne(result.getId());
    	assertThat(forwardFromDB.getForwarder().getId()).isEqualTo(forward.getForwarder().getId());
    	assertThat(forwardFromDB.getForwardedTo().getId()).isEqualTo(forward.getForwardedTo().getId());
    	assertThat(forwardFromDB.getForwardedReport().getId()).isEqualTo(forward.getForwardedReport().getId());
    }
	
	@Test(expected = UserNotReportHolderException.class)
    @Transactional
    public void testCreateSenderNotHolder() throws UserNotReportHolderException {
		User forwardSender = userRepository.findOne(1L);
		User forwardReceiver = userRepository.findOne(12L);
		Report forwardedReport = reportRepository.findOne(1L);
		
    	Forward forward = new Forward();
    	forward.setForwarder(forwardSender);
    	forward.setForwardedTo(forwardReceiver);
    	forward.setForwardedReport(forwardedReport);
    	
    	forwardService.create(forward);
    }
	
}
