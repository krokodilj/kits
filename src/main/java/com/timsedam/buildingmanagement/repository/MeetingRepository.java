package com.timsedam.buildingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
