package com.timsedam.buildingmanagement.dto.request;

import javax.validation.constraints.NotNull;

public class OpenEndedQuestionCreateDTO {
	
	@NotNull(message = "'questionText' not provided")
	private String questionText;
	
	@NotNull(message = "'questionForm' not provided")
	private Long questionForm;
	
	public OpenEndedQuestionCreateDTO() {
		super();
	}
	
	public OpenEndedQuestionCreateDTO(String questionText, Long questionForm) {
		super();
		this.questionText = questionText;
		this.questionForm = questionForm;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public Long getQuestionForm() {
		return questionForm;
	}

	public void setQuestionForm(Long questionForm) {
		this.questionForm = questionForm;
	}
	
}
