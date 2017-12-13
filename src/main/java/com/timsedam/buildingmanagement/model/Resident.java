package com.timsedam.buildingmanagement.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("RESIDENT")
public class Resident extends User {
	
	@ManyToMany(mappedBy = "residents")
	private List<Residence> residences;
	@OneToMany(mappedBy = "apartmentOwner")
	private List<Residence> ownedApartments;
    
	public Resident() {
		super();
	}

	public Resident(List<Residence> residences, List<Residence> ownedApartments) {
		super();
		this.residences = residences;
		this.ownedApartments = ownedApartments;
	}

	public List<Residence> getResidences() {
		return residences;
	}

	public void setResidences(List<Residence> residences) {
		this.residences = residences;
	}

	public List<Residence> getOwnedApartments() {
		return ownedApartments;
	}

	public void setOwnedApartments(List<Residence> ownedApartments) {
		this.ownedApartments = ownedApartments;
	}

	@Override
	public String toString() {
		return "Resident [residences=" + residences + ", ownedApartments=" + ownedApartments + "]";
	}

	public boolean isResident(String address) {
		for(int i=0; i<ownedApartments.size(); i++){
			if(ownedApartments.get(i).getBuilding().getAddress().equals(address))
				return true;
		}
		for(int i=0; i<residences.size(); i++){
			if(residences.get(i).getBuilding().getAddress().equals(address))
				return true;
		}
		return false;
	}
	
}
