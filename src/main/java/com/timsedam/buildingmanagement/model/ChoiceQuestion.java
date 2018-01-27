package com.timsedam.buildingmanagement.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class ChoiceQuestion extends Question {
	
	@OneToMany(mappedBy = "questionAnswered")
	private List<Answer> offeredAnswers;
	
	public ChoiceQuestion() {
		super();
	}

	public ChoiceQuestion(List<Answer> offeredAnswers) {
		super();
		this.offeredAnswers = offeredAnswers;
	}

	public List<Answer> getOfferedAnswers() {
		return offeredAnswers;
	}

	public void setOfferedAnswers(List<Answer> offeredAnswers) {
		this.offeredAnswers = offeredAnswers;
	}

	@Override
	public String toString() {
		return "ChoiceQuestion [offeredAnswers=" + offeredAnswers + "]";
	}
		
}
