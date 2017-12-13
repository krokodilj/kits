package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;
	
	public Report save(Report report) {
		return reportRepository.save(report);
	}

	public Report findOne(long id) {
		return reportRepository.findOne(id);
	}

}
