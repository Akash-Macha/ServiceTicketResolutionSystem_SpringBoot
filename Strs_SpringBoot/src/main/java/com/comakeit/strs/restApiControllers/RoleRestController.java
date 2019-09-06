package com.comakeit.strs.restApiControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.strs.entites.Role;
import com.comakeit.strs.services.RoleServices;

@RestController
@RequestMapping("role")
public class RoleRestController {
	@Autowired
	private RoleServices roleServices ;
	
	@RequestMapping(value="/getAllRoles")
	public List<Role> GetAllRoles() {
		return roleServices.getAllRoles();
	}
	
}
