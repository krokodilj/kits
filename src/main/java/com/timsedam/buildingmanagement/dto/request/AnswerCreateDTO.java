package com.timsedam.buildingmanagement.dto.request;

import javax.validation.constraints.NotNull;

public class AnswerCreateDTO {
	
	@NotNull(message = "'content' not provided")
	private String content;
	
	private Long questionId;
	
	public AnswerCreateDTO() {
		super();
	}
	
	public AnswerCreateDTO(String content) {
		super();
		this.content = content;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
}
