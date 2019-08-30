package com.comakeit.strs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.comakeit.strs.entites.User;

@Repository
public interface IAdminRepository extends JpaRepository<User, Integer>{

	@Query("SELECT user FROM User user")
	List<User> getAllUses();
	


}
