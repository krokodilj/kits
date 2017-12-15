package com.timsedam.buildingmanagement.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

public class CreateAnnouncementDTO {

    @NotNull
    private String content;
    @NotNull
    private long building;
    @NotNull
    private LocalDateTime postedAt;

    public CreateAnnouncementDTO(){}

    public CreateAnnouncementDTO(String content, long building, LocalDateTime postedAt) {
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
