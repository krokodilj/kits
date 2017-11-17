package com.timsedam.buildingmanagement.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Manager extends User {

	@OneToMany
	private List<Building> managedBuildings;

	public Manager() {}

	public Manager(String username, String password, String email, String picture, Role role,
			Collection<Comment> comments, List<Building> managedBuildings) {
		super(username, password, email, picture, role, comments);
		this.managedBuildings = managedBuildings;
	}

	public List<Building> getManagedBuildings() {
		return managedBuildings;
	}

	public void setManagedBuildings(List<Building> managedBuildings) {
		this.managedBuildings = managedBuildings;
	}

	@Override
	public String toString() {
		return "Manager [managedBuildings=" + managedBuildings + "]";
	}
	
}
