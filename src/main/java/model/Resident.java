package model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("resident")
public class Resident extends User {
	
	@ManyToMany(mappedBy = "residents")
	private List<Residence> residences;
    
	public Resident() {
		super();
	}

	public Resident(List<Residence> residences) {
		super();
		this.residences = residences;
	}

	public List<Residence> getResidences() {
		return residences;
	}

	public void setResidences(List<Residence> residences) {
		this.residences = residences;
	}

	@Override
	public String toString() {
		return "Resident [residences=" + residences + "]";
	}
    
}
