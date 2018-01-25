package com.timsedam.buildingmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.timsedam.buildingmanagement.exceptions.UserExistsException;
import com.timsedam.buildingmanagement.exceptions.UserNotResidentException;
import com.timsedam.buildingmanagement.model.Announcement;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.AnnouncementRepository;
import com.timsedam.buildingmanagement.repository.BuildingRepository;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AnnouncementServiceTest {

	@Autowired
    private AnnouncementService announcementService;

	@Autowired
	private AnnouncementRepository announcementRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Test
    @Transactional
    @Rollback(true)
    public void testCreate() throws UserExistsException, UserNotResidentException {
		Announcement announcement = new Announcement();
		announcement.setContent("Hallway cleaning at 17h today!");
		announcement.setPostedAt(LocalDateTime.now());
		announcement.setPoster(userRepository.findOne(16L));
		announcement.setBuilding(buildingRepository.findOne(1L));
		
    	long numAnnouncementsBeforeCreate = announcementRepository.count();
    	
    	Announcement result = announcementService.create(announcement);
    	assertThat(result).isNotNull();
    	
    	assertThat(announcementRepository.count()).isEqualTo(numAnnouncementsBeforeCreate + 1);
    	Announcement announcementFromDB = announcementRepository.findOne(result.getId());
    	assertThat(announcementFromDB.getContent()).isEqualTo(announcement.getContent());
    	assertThat(announcementFromDB.getPostedAt().toString()).isEqualTo(announcement.getPostedAt().toString());
    }
	
	@Test(expected = UserNotResidentException.class)
    @Transactional
    @Rollback(true)
    public void userNotResidentOrApartmentOwner() throws UserExistsException, UserNotResidentException {
		Announcement announcement = new Announcement();
		announcement.setContent("Hallway cleaning at 17h today!");
		announcement.setPostedAt(LocalDateTime.now());
		announcement.setPoster(userRepository.findOne(5L));
		announcement.setBuilding(buildingRepository.findOne(1L));
		
    	announcementService.create(announcement);
    }
	
	@Test
    @Transactional
    public void findByBuilding() throws UserNotResidentException {
		User poster = userRepository.findOne(16L);
		Building building = buildingRepository.findOne(1L);
		
		List<Announcement> announcements = announcementService.findAllByBuilding(poster, building, 0, 1);
		assertThat(announcements.size()).isEqualTo(1);
    }
	
	@Test(expected = UserNotResidentException.class)
	@Transactional
	public void findByBuildingNotResident() throws UserNotResidentException {
		User poster = userRepository.findOne(5L);
		Building building = buildingRepository.findOne(1L);
		
		announcementService.findAllByBuilding(poster, building, 0, 1);
	}
	
	
	

}
