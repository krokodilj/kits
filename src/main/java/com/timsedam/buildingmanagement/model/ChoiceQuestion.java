package com.timsedam.buildingmanagement.model;

import java.util.List;

import javax.persistence.OneToMany;

public class ChoiceQuestion extends Question {
	
	@OneToMany
	private List<OfferedAnswer> offeredAnswers;
	@OneToMany
	private List<PickedAnswer> pickedAnswer;
	
	public ChoiceQuestion() {
		super();
	}

	public ChoiceQuestion(String questionText, QuestionForm questionForm, List<OfferedAnswer> offeredAnswers,
			List<PickedAnswer> pickedAnswer) {
		super(questionText, questionForm);
		this.offeredAnswers = offeredAnswers;
		this.pickedAnswer = pickedAnswer;
	}

	public List<OfferedAnswer> getOfferedAnswers() {
		return offeredAnswers;
	}

	public void setOfferedAnswers(List<OfferedAnswer> offeredAnswers) {
		this.offeredAnswers = offeredAnswers;
	}

	public List<PickedAnswer> getPickedAnswer() {
		return pickedAnswer;
	}

	public void setPickedAnswer(List<PickedAnswer> pickedAnswer) {
		this.pickedAnswer = pickedAnswer;
	}

	@Override
	public String toString() {
		return "ChoiceQuestion [offeredAnswers=" + offeredAnswers + ", pickedAnswer=" + pickedAnswer + "]";
	}
	
}
