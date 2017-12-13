package com.timsedam.buildingmanagement.repository;

import com.timsedam.buildingmanagement.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
}
