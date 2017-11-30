package model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	private long id;
	private String data;
	@ManyToOne
	private User commenter;
	@ManyToOne
	private Report reportCommented;
	private LocalDateTime postedAt;
	
	public Comment() {
		super();
	}

	public Comment(String data, User commenter, Report reportCommented, LocalDateTime postedAt) {
		super();
		this.data = data;
		this.commenter = commenter;
		this.reportCommented = reportCommented;
		this.postedAt = postedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public User getCommenter() {
		return commenter;
	}

	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}

	public Report getReportCommented() {
		return reportCommented;
	}

	public void setReportCommented(Report reportCommented) {
		this.reportCommented = reportCommented;
	}

	public LocalDateTime getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(LocalDateTime postedAt) {
		this.postedAt = postedAt;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", data=" + data + ", commenter=" + commenter + ", reportCommented="
				+ reportCommented + ", postedAt=" + postedAt + "]";
	}
	
}
