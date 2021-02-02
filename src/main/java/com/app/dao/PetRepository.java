package com.app.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer>{

	//Pagination to get all pet list
	@Query("from Pet as c where c.user.id=:userId")
	
	//currentPage-page
	//pets per page-5
	public Page<Pet> findPetByUser(@Param("userId") int userId,Pageable pePageable);
	@Query("from Pet as c where c.user.id=:id")
	public List<Pet> findPetByUserId(@Param("id") int id);
	
}
