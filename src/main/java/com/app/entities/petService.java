package com.app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "petservice")
public class petService {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int service_id;
	@ManyToOne
	private User user;
	@OneToOne
	private Pet pet;

	/*
	 * @ManyToOne private Staff staff;
	 */
	public petService() {
		super();
	}
	public int getService_id() {
		return service_id;
	}
	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	/*
	 * public Staff getStaff() { return staff; } public void setStaff(Staff staff) {
	 * this.staff = staff; }
	 */
	
	
	

}
