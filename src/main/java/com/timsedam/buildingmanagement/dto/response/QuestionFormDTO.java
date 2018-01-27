package com.timsedam.buildingmanagement.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.timsedam.buildingmanagement.model.QuestionFormStatus;

public class QuestionFormDTO {
	
	private Long id;
	private ArrayList<Long> questions;
	private Long creator;
	private Long building;
	private String title;
	private String content;
	private String status;
	private LocalDateTime openedAt;
	private LocalDateTime startedAt;
	private LocalDateTime closedAt;
	
	public QuestionFormDTO() {
		super();
	}

	public QuestionFormDTO(Long id, ArrayList<Long> questions, Long creator, Long building, String title,
			String content, String status, LocalDateTime openedAt, LocalDateTime startedAt,
			LocalDateTime closedAt) {
		super();
		this.id = id;
		this.questions = questions;
		this.creator = creator;
		this.building = building;
		this.title = title;
		this.content = content;
		this.status = status;
		this.openedAt = openedAt;
		this.startedAt = startedAt;
		this.closedAt = closedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<Long> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Long> questions) {
		this.questions = questions;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getBuilding() {
		return building;
	}

	public void setBuilding(Long building) {
		this.building = building;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getOpenedAt() {
		return openedAt;
	}

	public void setOpenedAt(LocalDateTime openedAt) {
		this.openedAt = openedAt;
	}

	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}

	public LocalDateTime getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(LocalDateTime closedAt) {
		this.closedAt = closedAt;
	}
	
}
