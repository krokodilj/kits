package com.timsedam.buildingmanagement.dto.response;


import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.User;

import java.time.LocalDateTime;
import java.util.Date;

public class AnnouncementDTO {

    private long id;
    private String content;
    private LocalDateTime postedAt;
    private User poster;
    private Building building;

    public AnnouncementDTO(){}

    public AnnouncementDTO(long id, String content, LocalDateTime postedAt, User poster, Building building) {
        this.id = id;
        this.content = content;
        this.postedAt = postedAt;
        this.poster = poster;
        this.building = building;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return "AnnouncementDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", postedAt=" + postedAt +
                ", poster=" + poster +
                ", building=" + building +
                '}';
    }
}
