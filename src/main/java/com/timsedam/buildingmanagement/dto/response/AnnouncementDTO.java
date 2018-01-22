package com.timsedam.buildingmanagement.dto.response;


import java.time.LocalDateTime;

public class AnnouncementDTO {

    private long id;
    private String content;
    private LocalDateTime postedAt;
    private Long poster;
    private Long building;

    public AnnouncementDTO(){}

    public AnnouncementDTO(long id, String content, LocalDateTime postedAt, Long poster, Long building) {
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

    public Long getPoster() {
        return poster;
    }

    public void setPoster(Long poster) {
        this.poster = poster;
    }

    public Long getBuilding() {
        return building;
    }

    public void setBuilding(Long building) {
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
