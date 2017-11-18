package com.timsedam.buildingmanagement.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class OfferedAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String content;
	@ManyToOne
	private ChoiceQuestion questionAsked;
	
	public OfferedAnswer() {}

	public OfferedAnswer(String content, ChoiceQuestion questionAsked) {
		this.content = content;
		this.questionAsked = questionAsked;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ChoiceQuestion getquestionAsked() {
		return questionAsked;
	}

	public void setquestionAsked(ChoiceQuestion questionAsked) {
		this.questionAsked = questionAsked;
	}

	@Override
	public String toString() {
		return "OfferedAnswer [id=" + id + ", content=" + content + ", questionAsked=" + questionAsked + "]";
	}	

}
