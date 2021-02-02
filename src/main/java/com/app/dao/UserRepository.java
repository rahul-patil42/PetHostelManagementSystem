	package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.email =:email")
	public User getCustByCustName(@Param("email") String email);
	@Query("select u from User u where u.id =:userId")
	public User getUserByUserId(@Param("userId") int userId);
}