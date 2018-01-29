package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.response.BuildingDTO;
import com.timsedam.buildingmanagement.dto.response.CommentDTO;
import com.timsedam.buildingmanagement.dto.response.ForwardDTO;
import com.timsedam.buildingmanagement.dto.response.ReportDTO;
import com.timsedam.buildingmanagement.model.Report;

@Component
public class ReportMapper {

	@Autowired
    private CommentMapper commentMapper;
	
	@Autowired
    private BuildingMapper buidldingMapper;
	
	@Autowired
    private ForwardMapper forwardMapper;
	
	public ReportDTO toDto(Report r){
		ReportDTO reportDTO = new ReportDTO(r.getId(), r.getStatus(), 
				r.getDescription(), 
				r.getPictures(), r.getSender().getId(), new ArrayList<CommentDTO>());

		BuildingDTO buildingDTO = buidldingMapper.toDto(r.getLocation());
		reportDTO.setLocation(buildingDTO);
		
		ForwardDTO forwardDTO = forwardMapper.toDTO(r.getCurrentHolder());
		reportDTO.setCurrentHolder(forwardDTO);
		
        //comments
        if(r.getComments() != null){
        	ArrayList<CommentDTO> comments = 
        			commentMapper.toDto(r.getComments());
            reportDTO.setComments(comments);
        }
        
        return reportDTO;
    }
	
	public ArrayList<ReportDTO> toDto(List<Report> reports) {
    	ArrayList<ReportDTO> result = new ArrayList<ReportDTO>();
    	for(Report report : reports) {
    		result.add(toDto(report));
    	}
    	return result;
    }
}
