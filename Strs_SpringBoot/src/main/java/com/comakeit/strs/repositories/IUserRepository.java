package com.comakeit.strs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comakeit.strs.entites.*;
//import com.comakeit.strs.entites.Priority;
//import com.comakeit.strs.entites.Role;
//import com.comakeit.strs.entites.ServiceEngineer;
//import com.comakeit.strs.entites.User;

public interface IUserRepository extends JpaRepository<User, Integer>{

	@Query("SELECT user.role FROM User user WHERE user.user_name = :user_name and user.password = :password ")
	public Role validate(@Param("user_name") String user_name, @Param("password") String password);
	
	@Query("SELECT department "
			+ "FROM Department department ")
	public List<Department> getListOfDepartment();
	
	
	@Query("SELECT priority "
			+ "FROM Priority priority ")
	public List<Priority> getListOfPriority();
	
	@Query("SELECT user "
			+ "FROM User user ")
	public List<User> getListOfUsers();
		
	@Query("SELECT status FROM Status status WHERE status.code = :code")
	public Status getStatus(@Param("code") String code);
	
	@Query("SELECT user FROM User user WHERE user.user_name= :user_name")
	public User getUser(@Param("user_name") String user_name);
}

