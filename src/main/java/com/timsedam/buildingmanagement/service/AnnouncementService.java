package com.timsedam.buildingmanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.UserNotResidentException;
import com.timsedam.buildingmanagement.model.Announcement;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Meeting;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.AnnouncementRepository;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;
    
    @Autowired
    private BuildingService buildingService;

    public Announcement save(Announcement announcement){
        try{
            return announcementRepository.save(announcement);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public Announcement create(Announcement announcement) throws UserNotResidentException {
    	if(!buildingService.isResidentOrApartmentOwner(announcement.getPoster(), announcement.getBuilding()))
    		throw new UserNotResidentException(announcement.getPoster().getId(), announcement.getBuilding().getId());
    	
    	return announcementRepository.save(announcement);
    }

    public List<Announcement> findAllByBuilding(User user, Building building, int page, int count) throws UserNotResidentException {
        if(!buildingService.isResident(user, building))
        	throw new UserNotResidentException(user.getId(), building.getId());
        
        Page<Announcement> announcementPage = announcementRepository.findAllByBuilding(building, new PageRequest(page, count));
        return announcementPage.getContent();
    }

    public Announcement createFromReport(Report report){
        try{
            Announcement announcement= new Announcement(
                    report.getDescription(), LocalDateTime.now(),report.getSender(),report.getLocation()
            );
            return announcementRepository.save(announcement);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Announcement createFromMeeting(Meeting meeting){
        try{
            String content="New meeting announced at "+meeting.getStartsAt().toString();
            Announcement announcement= new Announcement(
                    content, LocalDateTime.now(),null,meeting.getBuilding()
            );
            return announcementRepository.save(announcement);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
