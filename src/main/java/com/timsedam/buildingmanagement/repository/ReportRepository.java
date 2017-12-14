package com.timsedam.buildingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long>{

}
