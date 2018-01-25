package com.timsedam.buildingmanagement.mapper;


import java.util.ArrayList;
import java.util.List;

import com.timsedam.buildingmanagement.dto.request.AnnouncementCreateDTO;
import com.timsedam.buildingmanagement.dto.response.AnnouncementDTO;
import com.timsedam.buildingmanagement.model.Announcement;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.User;

public class AnnouncementMapper {

	public Announcement toModel(AnnouncementCreateDTO createAnnouncementDTO, Building b, User u){
		Announcement announcement = new Announcement(createAnnouncementDTO.getContent(),
				createAnnouncementDTO.getPostedAt(), null, null);
		announcement.setBuilding(b);
	    announcement.setPoster(u);
	    return announcement;
	}

    public AnnouncementDTO toDto(Announcement announcement){
        AnnouncementDTO announcementDTO = 
        	new AnnouncementDTO(announcement.getId(), announcement.getContent(), announcement.getPostedAt(),
        			announcement.getPoster().getId(), announcement.getBuilding().getId());

        return announcementDTO;
    }
    
    public ArrayList<AnnouncementDTO> toDto(List<Announcement> announcements) {
    	ArrayList<AnnouncementDTO> dtos = new ArrayList<AnnouncementDTO>();
    	for(Announcement a : announcements) {
    		dtos.add(toDto(a));
    	}
    	return dtos;
    }

}
