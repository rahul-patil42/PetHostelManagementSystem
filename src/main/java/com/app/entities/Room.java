package com.app.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Room")
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int room_id;
	private boolean roomStatus;
	@OneToOne
	private Pet pet;
	
	public Room() {
		super();
	}
	
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public boolean isRoomStatus() {
		return roomStatus;
	}
	public void setRoomStatus(boolean roomStatus) {
		this.roomStatus = roomStatus;
	}
	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}

	@Override
	public String toString() {
		return "Room [room_id=" + room_id + ", roomStatus=" + roomStatus + ", pet=" + pet + "]";
	}
	
	
	
	
}
