package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.entities.Pet;
import com.app.entities.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
  
	//public Staff findStaffByPet(@RequestParam("pet") Pet pet);
	  
  
}
