package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OfferedAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String content;
	@ManyToOne
	private ChoiceQuestion questionAnswered;
	
	public OfferedAnswer() {
		super();
	}

	public OfferedAnswer(String content, ChoiceQuestion questionAnswered) {
		super();
		this.content = content;
		this.questionAnswered = questionAnswered;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ChoiceQuestion getQuestionAnswered() {
		return questionAnswered;
	}

	public void setQuestionAnswered(ChoiceQuestion questionAnswered) {
		this.questionAnswered = questionAnswered;
	}

	@Override
	public String toString() {
		return "OfferedAnswer [id=" + id + ", content=" + content + ", questionAnswered=" + questionAnswered + "]";
	}

}
