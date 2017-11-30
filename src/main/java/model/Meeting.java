package model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Meeting {
	
	@Id
	@GeneratedValue
	private Long id;
	@OneToMany(mappedBy = "meeting")
	private List<Proposal> agenda;
	@ManyToOne
	private Manager scheduledBy;
	private LocalDateTime startsAt;
	private String record;
	@ManyToOne
	private Building building;
	private String location;
	
	public Meeting() {
		super();
	}

	public Meeting(List<Proposal> agenda, Manager scheduledBy, LocalDateTime startsAt, String record, Building building,
			String location) {
		super();
		this.agenda = agenda;
		this.scheduledBy = scheduledBy;
		this.startsAt = startsAt;
		this.record = record;
		this.building = building;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Proposal> getAgenda() {
		return agenda;
	}

	public void setAgenda(List<Proposal> agenda) {
		this.agenda = agenda;
	}

	public Manager getScheduledBy() {
		return scheduledBy;
	}

	public void setScheduledBy(Manager scheduledBy) {
		this.scheduledBy = scheduledBy;
	}

	public LocalDateTime getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(LocalDateTime startsAt) {
		this.startsAt = startsAt;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Meeting [id=" + id + ", agenda=" + agenda + ", scheduledBy=" + scheduledBy + ", startsAt=" + startsAt
				+ ", record=" + record + ", building=" + building + ", location=" + location + "]";
	}
	
}
