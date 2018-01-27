package com.timsedam.buildingmanagement.dto.request;

public class OfferedAnswerSendDTO {
	
	private Long answerId;
	private Long questionId;
	
	public OfferedAnswerSendDTO() {
		super();
	}

	public OfferedAnswerSendDTO(Long answerId, Long questionId) {
		super();
		this.answerId = answerId;
		this.questionId = questionId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
}
