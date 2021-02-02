package com.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Pet;
import com.app.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	@Query(value="select * from Room where room_status=1 LIMIT 1",nativeQuery=true)
		public Room getRoomByRoomStatus();
	
	public Room findRoomByPet(@Param("pet") Pet pet);
	
	
}
