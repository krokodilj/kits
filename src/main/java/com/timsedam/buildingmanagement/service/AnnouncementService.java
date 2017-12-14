package com.timsedam.buildingmanagement.service;

import com.timsedam.buildingmanagement.model.Announcement;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Meeting;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement save(Announcement announcement){
        try{
            return announcementRepository.save(announcement);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Announcement> findAllByBuilding(Building b, int page, int count){
        try{
            Page<Announcement> pejdz= announcementRepository.findAllByBuilding(b,new PageRequest(page,count));
            return pejdz.getContent();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
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
