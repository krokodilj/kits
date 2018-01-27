package com.timsedam.buildingmanagement.dto.request;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

public class ChoiceQuestionCreateDTO {
	
	@NotNull(message = "'questionText' not provided")
	private String questionText;
	
	private Long questionForm;
	
	private ArrayList<AnswerCreateDTO> offeredAnswers;

	public ChoiceQuestionCreateDTO() {
		super();
	}

	public ChoiceQuestionCreateDTO(String questionText, Long questionForm,
			ArrayList<AnswerCreateDTO> offeredAnswers) {
		super();
		this.questionText = questionText;
		this.questionForm = questionForm;
		this.offeredAnswers = offeredAnswers;
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

	public ArrayList<AnswerCreateDTO> getOfferedAnswers() {
		return offeredAnswers;
	}

	public void setOfferedAnswers(ArrayList<AnswerCreateDTO> offeredAnswers) {
		this.offeredAnswers = offeredAnswers;
	}

}
