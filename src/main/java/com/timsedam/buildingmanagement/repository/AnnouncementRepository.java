package com.timsedam.buildingmanagement.repository;

import com.timsedam.buildingmanagement.model.Announcement;
import com.timsedam.buildingmanagement.model.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

    Page<Announcement> findAllByBuilding(@Param("building") Building building,Pageable pageable);

}
