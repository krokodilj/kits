package com.timsedam.buildingmanagement.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QuestionForm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private List<Question> questions;
	private QuestionFormStatus status;
	private LocalDateTime openDate;
	private LocalDateTime startDate;
	private LocalDateTime closeDate;
	
	public QuestionForm() {}

	public QuestionForm(List<Question> questions, QuestionFormStatus status, LocalDateTime openDate,
			LocalDateTime startDate, LocalDateTime closeDate) {
		super();
		this.questions = questions;
		this.status = status;
		this.openDate = openDate;
		this.startDate = startDate;
		this.closeDate = closeDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public QuestionFormStatus getStatus() {
		return status;
	}

	public void setStatus(QuestionFormStatus status) {
		this.status = status;
	}

	public LocalDateTime getOpenDate() {
		return openDate;
	}

	public void setOpenDate(LocalDateTime openDate) {
		this.openDate = openDate;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(LocalDateTime closeDate) {
		this.closeDate = closeDate;
	}

	@Override
	public String toString() {
		return "QuestionForm [id=" + id + ", questions=" + questions + ", status=" + status + ", openDate=" + openDate
				+ ", startDate=" + startDate + ", closeDate=" + closeDate + "]";
	}

}
