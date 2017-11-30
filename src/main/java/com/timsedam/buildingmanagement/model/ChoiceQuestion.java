package com.timsedam.buildingmanagement.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class ChoiceQuestion extends Question {
	
	@OneToMany(mappedBy = "questionAnswered")
	private List<OfferedAnswer> offeredAnswers;
	
	public ChoiceQuestion() {
		super();
	}

	public ChoiceQuestion(List<OfferedAnswer> offeredAnswers) {
		super();
		this.offeredAnswers = offeredAnswers;
	}

	public List<OfferedAnswer> getOfferedAnswers() {
		return offeredAnswers;
	}

	public void setOfferedAnswers(List<OfferedAnswer> offeredAnswers) {
		this.offeredAnswers = offeredAnswers;
	}

	@Override
	public String toString() {
		return "ChoiceQuestion [offeredAnswers=" + offeredAnswers + "]";
	}
		
}
