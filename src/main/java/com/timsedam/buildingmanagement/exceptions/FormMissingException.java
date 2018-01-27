package com.timsedam.buildingmanagement.exceptions;

public class FormMissingException extends Exception {
	
	private Long formId;

	public FormMissingException(Long formId) {
		super();
		this.formId = formId;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
}
