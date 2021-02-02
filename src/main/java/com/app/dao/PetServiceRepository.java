package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Pet;
import com.app.entities.petService;

public interface PetServiceRepository extends JpaRepository<petService, Integer> {

	public petService findServiceByPet(Pet pet);

}
