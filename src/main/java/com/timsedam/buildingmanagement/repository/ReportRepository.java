package com.timsedam.buildingmanagement.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.timsedam.buildingmanagement.model.Forward;
import com.timsedam.buildingmanagement.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long>{

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Report r set r.currentHolder = ?1 where r.id = ?2")
	void setCurrentHolder(Forward currentHolder, Long id);

}
