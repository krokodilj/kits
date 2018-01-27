package com.timsedam.buildingmanagement.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String questionText;
	
	@ManyToOne
	private QuestionForm questionForm;
	
	@OneToMany(mappedBy = "questionAnswered")
	private List<Answer> usersAnswers;

	public Question() {
		super();
	}

	public Question(String questionText, QuestionForm questionForm, List<Answer> usersAnswers) {
		super();
		this.questionText = questionText;
		this.questionForm = questionForm;
		this.usersAnswers = usersAnswers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public QuestionForm getQuestionForm() {
		return questionForm;
	}

	public void setQuestionForm(QuestionForm questionForm) {
		this.questionForm = questionForm;
	}

	public List<Answer> getUsersAnswers() {
		return usersAnswers;
	}

	public void setUsersAnswers(List<Answer> usersAnswers) {
		this.usersAnswers = usersAnswers;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", questionText=" + questionText + ", questionForm=" + questionForm
				+ ", usersAnswers=" + usersAnswers + "]";
	}

}
