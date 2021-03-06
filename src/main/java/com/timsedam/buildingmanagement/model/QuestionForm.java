package com.timsedam.buildingmanagement.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class QuestionForm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToMany(mappedBy = "questionForm")
	private List<Question> questions;
	@ManyToOne
	private User creator;
	@ManyToOne
	private Building building;
	private QuestionFormStatus status;
	private LocalDateTime openedAt;
	private LocalDateTime startedAt;
	private LocalDateTime closedAt;
	
	public QuestionForm() {
		super();
	}

	public QuestionForm(List<Question> questions, User creator, QuestionFormStatus status, LocalDateTime openedAt,
			LocalDateTime startedAt, LocalDateTime closedAt) {
		super();
		this.questions = questions;
		this.creator = creator;
		this.status = status;
		this.openedAt = openedAt;
		this.startedAt = startedAt;
		this.closedAt = closedAt;
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public QuestionFormStatus getStatus() {
		return status;
	}

	public void setStatus(QuestionFormStatus status) {
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

	@Override
	public String toString() {
		return "QuestionForm [id=" + id + ", questions=" + questions + ", creator=" + creator + ", status=" + status
				+ ", openedAt=" + openedAt + ", startedAt=" + startedAt + ", closedAt=" + closedAt + "]";
	}

}
