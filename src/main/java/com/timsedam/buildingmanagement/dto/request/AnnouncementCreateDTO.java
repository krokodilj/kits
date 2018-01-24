package com.timsedam.buildingmanagement.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class AnnouncementCreateDTO {

    @NotNull(message = "'content' not provided")
    private String content;
    
    @NotNull(message = "'building' not provided")
    private long building;
    
    @NotNull(message = "'postedAt' not provided")
    private LocalDateTime postedAt;

    public AnnouncementCreateDTO(){}

    public AnnouncementCreateDTO(String content, long building, LocalDateTime postedAt) {
        this.content = content;
        this.building = building;
        this.postedAt = postedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getBuilding() {
        return building;
    }

    public void setBuilding(long building) {
        this.building = building;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    @Override
    public String toString() {
        return "CreateAnnouncementDTO{" +
                "content='" + content + '\'' +
                ", building=" + building +
                ", postedAt=" + postedAt +
                '}';
    }
}
