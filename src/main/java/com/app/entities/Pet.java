package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Pet_info")
public class Pet {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pet_id;
	private String pet_name;
	private String pet_type;
	@Column(name = "pickup_date")
	private String pickupDate;
	@Column(name = "delivery_date")
	private String deliveryDate;
	@Column(length=5000)
	private String about_pet;
	private String image;
	
	
	@ManyToOne
	private User user;
	
	
	
	

	
	
	public Pet() {
		super();
	}
	public int getPet_id() {
		return pet_id;
	}
	public void setPet_id(int pet_id) {
		this.pet_id = pet_id;
	}
	public String getPet_name() {
		return pet_name;
	}
	public void setPet_name(String pet_name) {
		this.pet_name = pet_name;
	}
	public String getPet_type() {
		return pet_type;
	}
	public void setPet_type(String pet_type) {
		this.pet_type = pet_type;
	}
	public String getAbout_pet() {
		return about_pet;
	}
	public void setAbout_pet(String about_pet) {
		this.about_pet = about_pet;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	


	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	@Override
	public String toString() {
		return "Pet [pet_id=" + pet_id + ", pet_name=" + pet_name + ", pet_type=" + pet_type + ", pickupDate="
				+ pickupDate + ", deliveryDate=" + deliveryDate + ", about_pet=" + about_pet + ", image=" + image
				+ ", user=" + user + "]";
	}
	//for removing we are matching objects id
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.pet_id==((Pet)obj).getPet_id();
	}
	

}
