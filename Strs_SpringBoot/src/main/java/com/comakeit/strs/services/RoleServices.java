package com.comakeit.strs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comakeit.strs.entites.Role;
import com.comakeit.strs.repositories.IRoleRepository;

@Service
public class RoleServices {

	
	@Autowired
	private IRoleRepository roleRepository;

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

}
