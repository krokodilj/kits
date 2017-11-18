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
	private Question questionBeingAnswered;
	
	public Answer() {}

	public Answer(User respondant, Question questionBeingAnswered) {
		super();
		this.respondant = respondant;
		this.questionBeingAnswered = questionBeingAnswered;
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

	public Question getQuestionBeingAnswered() {
		return questionBeingAnswered;
	}

	public void setQuestionBeingAnswered(Question questionBeingAnswered) {
		this.questionBeingAnswered = questionBeingAnswered;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", respondant=" + respondant + ", questionBeingAnswered=" + questionBeingAnswered
				+ "]";
	}

}
