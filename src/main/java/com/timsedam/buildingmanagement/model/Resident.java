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

	public boolean isResident(Building building) {
		
		System.out.println("------------");
		System.out.println(this.getUsername());
		
		for(Residence ownedApartment : ownedApartments) {
			if(ownedApartment.getBuilding().getId() == building.getId()) {
				System.out.println("OWNED APARTMENT " + building.getId());
				return true;
			}
		}
		for(Residence residence : residences) {
			if(residence.getBuilding().getId() == building.getId()) {
				System.out.println("LIVES IN " + building.getId());
				return true;
			}
		}
		return false;
	}
	
}
