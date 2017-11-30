package com.timsedam.buildingmanagement.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PickedAnswer extends Answer {
	
	@ManyToOne
	private OfferedAnswer content;

	public PickedAnswer() {}
	
	public PickedAnswer(OfferedAnswer content) {
		super();
		this.content = content;
	}

	public OfferedAnswer getContent() {
		return content;
	}

	public void setContent(OfferedAnswer content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "PickedAnswer [content=" + content + "]";
	}

}
