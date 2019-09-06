package com.comakeit.strs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comakeit.strs.entites.Priority;
import com.comakeit.strs.repositories.IPriorityRepository;

@Service
public class PriorityServices {

	@Autowired
	private IPriorityRepository prioriotyRepository;

	public List<Priority> getListOfDepartment() {
		return prioriotyRepository.findAll();
	}
}
