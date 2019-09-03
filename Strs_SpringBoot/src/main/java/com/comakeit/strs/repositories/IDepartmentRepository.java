package com.comakeit.strs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comakeit.strs.entites.Department;

public interface IDepartmentRepository  extends JpaRepository<Department, Integer>{

}
