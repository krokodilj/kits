package com.timsedam.buildingmanagement.dto.request;

import javax.validation.constraints.NotNull;

public class QuestionFormCreateDTO {
	
	@NotNull(message = "'buildingId' not provided")
	private Long buildingId;
	
	@NotNull(message = "'title' not provided")
	private String title;
	
	@NotNull(message = "'content' not provided")
	private String content;
	
	public QuestionFormCreateDTO() {
		super();
	}

	public QuestionFormCreateDTO(Long buildingId, String title, String content) {
		super();
		this.buildingId = buildingId;
		this.title = title;
		this.content = content;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
