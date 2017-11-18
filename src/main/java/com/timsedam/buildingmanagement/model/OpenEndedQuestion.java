package com.timsedam.buildingmanagement.model;

import java.util.List;

public class OpenEndedQuestion extends Question {
	
	private List<TextAnswer> userAnswers;

	public OpenEndedQuestion() {}

	public OpenEndedQuestion(String questionText, QuestionForm questionForm, List<TextAnswer> userAnswers) {
		super(questionText, questionForm);
		this.userAnswers = userAnswers;
	}

	public List<TextAnswer> getUserAnswers() {
		return userAnswers;
	}

	public void setUserAnswers(List<TextAnswer> userAnswers) {
		this.userAnswers = userAnswers;
	}

	@Override
	public String toString() {
		return "OpenEndedQuestion [userAnswers=" + userAnswers + "]";
	}
	
}
