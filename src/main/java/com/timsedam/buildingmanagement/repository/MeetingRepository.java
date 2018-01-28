package com.timsedam.buildingmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.timsedam.buildingmanagement.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
	
	public List<Meeting> findAllByBuildingId(Long buildingId);
	
	@Query("SELECT m FROM Meeting as m join m.building as b join b.manager as manager where manager.id=?1") 
	public List<Meeting> findAllByManagerId(Long managerId);

}
