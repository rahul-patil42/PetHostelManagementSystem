package com.app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Staff")
public class Staff {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int staff_id;
	private String staff_name;
	private String staff_email;
	private String staff_phone;
	
	
	/*@ManyToOne
	private Pet pet;

*/
	public Staff() {
		super();
	}


	public int getStaff_id() {
		return staff_id;
	}


	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}


	public String getStaff_name() {
		return staff_name;
	}


	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}


	public String getStaff_email() {
		return staff_email;
	}


	public void setStaff_email(String staff_email) {
		this.staff_email = staff_email;
	}


	public String getStaff_phone() {
		return staff_phone;
	}


	public void setStaff_phone(String staff_phone) {
		this.staff_phone = staff_phone;
	}


	/*
	 * public Pet getPet() { return pet; }
	 * 
	 * 
	 * public void setPet(Pet pet) { this.pet = pet; }
	 */

}
