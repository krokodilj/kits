package com.timsedam.buildingmanagement.exceptions;

public class QuestionMissingException extends Exception {

	private Long questionId;

	public QuestionMissingException(Long questionId) {
		super();
		this.questionId = questionId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
}
