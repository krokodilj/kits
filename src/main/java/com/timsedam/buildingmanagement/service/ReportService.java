package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.ReportMissingException;
import com.timsedam.buildingmanagement.exceptions.UserNotResidentException;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Forward;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;
	
	public Report save(Report report) {
		return reportRepository.save(report);
	}

	public Report findOne(long id) throws ReportMissingException {
		Report report = reportRepository.findOne(id);
		if(report == null)
			throw new ReportMissingException(id);
		else
			return report;
	}
	
	public Report create(Report report) throws UserNotResidentException {
		User reportSender = report.getSender();
		Building location = report.getLocation();
		
		if(!isResidentOrApartmentOwner(reportSender, location))
			throw new UserNotResidentException(reportSender.getId(), location.getId());
		else
			return reportRepository.save(report);
	}
	
	private boolean isResidentOrApartmentOwner(User reportSender, Building location) {
		for(Residence residence : location.getResidences()) {
			if(residence.getApartmentOwner().getId() == reportSender.getId())
				return true;
			for(User resident : residence.getResidents()) {
				if(resident.getId() == reportSender.getId())
					return true;
			}
		}
		return false;
	}
	
	public void setCurrentHolder(Forward currentHolder, Long id){
		reportRepository.setCurrentHolder(currentHolder, id);
	}

	public void setStatus(String status, long id) {
		reportRepository.setStatus(status, id);
	}
	
	public boolean isAttachedToBuilding(Report report, Building building) {
		if(report.getLocation().getId() == building.getId())
			return true;
		else
			return false;
	}

}
