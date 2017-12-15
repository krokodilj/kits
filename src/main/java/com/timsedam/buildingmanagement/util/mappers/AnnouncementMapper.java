package com.timsedam.buildingmanagement.util.mappers;


import com.timsedam.buildingmanagement.dto.AnnouncementDTO;
import com.timsedam.buildingmanagement.dto.CreateAnnouncementDTO;
import com.timsedam.buildingmanagement.model.Announcement;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Resident;
import com.timsedam.buildingmanagement.model.User;

public class AnnouncementMapper {

    public Announcement toModel(CreateAnnouncementDTO createAnnouncementDTO,Building b,User u){
        Announcement announcement = new Announcement(
                createAnnouncementDTO.getContent(),createAnnouncementDTO.getPostedAt(),
                null,null);
        announcement.setBuilding(b);
        announcement.setPoster(u);
        return announcement;
    }

    public AnnouncementDTO toDto(Announcement announcement){
        AnnouncementDTO announcementDTO = new AnnouncementDTO(
                announcement.getId(),announcement.getContent(),announcement.getPostedAt(),
                null,null);

        //building
        announcement.getBuilding().setResidences(null);
        if(announcement.getBuilding().getManager()!=null) {
            announcement.getBuilding().setManager(null);
        }
        announcementDTO.setBuilding(announcement.getBuilding());

        //userposter
        //TODO ako je kompanija nzs sta ce sve morati na null :(
        if(announcement.getPoster()!=null){
            announcement.getPoster().setComments(null);
            announcement.getPoster().setPassword(null);
            announcement.getPoster().setRole(null);
            if(announcement.getPoster() instanceof Resident){
                Resident r = (Resident) announcement.getPoster();
                r.setOwnedApartments(null);
                r.setResidences(null);
            }
        }

        announcementDTO.setPoster(announcement.getPoster());

        return announcementDTO;
    }

}
