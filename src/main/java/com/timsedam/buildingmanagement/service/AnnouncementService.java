package com.timsedam.buildingmanagement.service;

import com.timsedam.buildingmanagement.model.Announcement;
import com.timsedam.buildingmanagement.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
