package com.comakeit.strs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comakeit.strs.entites.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer>{

	
	@Query("SELECT role "
			+ "FROM Role role "
			+ "WHERE role.code= :role_code")
	public Role getRoleByCode(@Param("role_code") String role_code);
}
