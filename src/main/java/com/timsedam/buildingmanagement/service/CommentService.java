package com.timsedam.buildingmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.model.Comment;
import com.timsedam.buildingmanagement.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
    private CommentRepository commentRepository;
	
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	public List<Comment> findAllByReport(long reportId) {
		return commentRepository.findAllByReportCommentedId(reportId);
	}
	
}
