package com.timsedam.buildingmanagement.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TextAnswer extends Answer {
	
	private String content;

	public TextAnswer() {
		super();
	}

	public TextAnswer(String content, OpenEndedQuestion questionAnswered) {
		super();
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
