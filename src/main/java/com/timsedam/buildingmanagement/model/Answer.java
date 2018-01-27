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
	private Long id;
	
	@ManyToOne
	private User respondant;
	
	@ManyToOne
	private Question questionAnswered;
	
	private String content;
	
	public Answer() {
		super();
	}

	public Answer(Long id, User respondant, Question questionAnswered, String content) {
		super();
		this.id = id;
		this.respondant = respondant;
		this.questionAnswered = questionAnswered;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getRespondant() {
		return respondant;
	}

	public void setRespondant(User respondant) {
		this.respondant = respondant;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Question getQuestionAnswered() {
		return questionAnswered;
	}

	public void setQuestionAnswered(Question questionAnswered) {
		this.questionAnswered = questionAnswered;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", respondant=" + respondant + ", questionAnswered=" + questionAnswered
				+ ", content=" + content + "]";
	}

}
