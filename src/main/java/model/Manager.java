package model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("manager")
public class Manager extends User {

	@ManyToMany
	@JoinTable(name = "manager_building", joinColumns = @JoinColumn(name = "manager_id"), inverseJoinColumns = @JoinColumn(name = "building_id"))
	private List<Building> managedBuildings;

	public Manager() {
		super();
	}

	public Manager(String username, String password, String email, String picture, List<Role> roles,
			List<Comment> comments, List<Building> managedBuildings) {
		super(username, password, email, picture, roles, comments);
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
