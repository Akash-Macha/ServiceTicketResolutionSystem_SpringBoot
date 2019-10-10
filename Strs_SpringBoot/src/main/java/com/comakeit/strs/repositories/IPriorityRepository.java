package com.comakeit.strs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comakeit.strs.entites.Priority;

@Repository
public interface IPriorityRepository  extends JpaRepository<Priority, Integer>{

	@Query("SELECT priority " 
			+ "FROM Priority priority " 
			+ "WHERE priority.code = :code ")
	public Priority getPriority(@Param("code") String code);
	
	@Query("SELECT priority " 
			+ "FROM Priority priority " 
			+ "WHERE priority.value = :priority_value ")
	public Priority getPriorityByValue(@Param("priority_value") String priority_value);
}
