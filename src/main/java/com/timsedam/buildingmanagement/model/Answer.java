package com.timsedam.buildingmanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private User respondant;
	
	@ManyToOne
	private Question questionAnswered;
	
	public Answer() {
		super();
	}

	public Answer(User respondant, Question questionAnswered) {
		super();
		this.respondant = respondant;
		this.questionAnswered = questionAnswered;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getRespondant() {
		return respondant;
	}

	public void setRespondant(User respondant) {
		this.respondant = respondant;
	}

	public Question getQuestionAnswered() {
		return questionAnswered;
	}

	public void setQuestionAnswered(Question questionAnswered) {
		this.questionAnswered = questionAnswered;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", respondant=" + respondant + ", questionAnswered=" + questionAnswered + "]";
	}
	
}
