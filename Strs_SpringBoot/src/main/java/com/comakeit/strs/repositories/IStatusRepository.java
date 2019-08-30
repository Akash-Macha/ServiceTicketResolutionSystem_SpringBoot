package com.comakeit.strs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comakeit.strs.entites.Status;

public interface IStatusRepository  extends JpaRepository<Status, Integer>{

	@Query("SELECT status FROM Status status WHERE status.code = :status_code")
	Status getStatus(@Param("status_code") String status_code);

}
