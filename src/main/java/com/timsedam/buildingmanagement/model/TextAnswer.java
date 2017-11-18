package com.timsedam.buildingmanagement.model;

import javax.persistence.Entity;

@Entity
public class TextAnswer extends Answer {
	
	private String content;

	public TextAnswer() {
		super();
	}

	public TextAnswer(User respondant, Question questionBeingAnswered, String content) {
		super(respondant, questionBeingAnswered);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "TextAnswer [content=" + content + "]";
	}

}
