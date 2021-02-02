package com.app.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "Enter valid name")
	@Size(min = 2,max = 20,message = "Enter length min 2 and max 20")
	private String name;

	
	  @Column(unique = true)
	  
		/* @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9+_.-]+$") */
	 
	private String email;

	private String address;

	 @Column(length = 11) 
	private String phone;

	private String role;

	/*
	 * @NotBlank(message = " password should not be blank")
	 * 
	 * @Size(min = 4, max = 12, message = "min 4 and max 12 chars allowed")
	 */
	private String password;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user",orphanRemoval = true)
	private List<Pet> pets = new ArrayList<>();

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	/*
	 * @Override public String toString() { return "User [id=" + id + ", name=" +
	 * name + ", email=" + email + ", address=" + address + ", phone=" + phone +
	 * ", role=" + role + ", password=" + password + ", pets=" + pets + "]"; }
	 */

	
}
