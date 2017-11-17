package com.timsedam.buildingmanagement.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String questionText;
	@ManyToOne
	private QuestionForm questionForm;
	
	public Question() {}

	public Question(String questionText, QuestionForm questionForm) {
		this.questionText = questionText;
		this.questionForm = questionForm;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public QuestionForm getQuestionForm() {
		return questionForm;
	}

	public void setQuestionForm(QuestionForm questionForm) {
		this.questionForm = questionForm;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", questionText=" + questionText + ", questionForm=" + questionForm + "]";
	}

}
