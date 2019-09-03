package com.comakeit.strs.restApiControllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.strs.entites.*;
//import com.comakeit.strs.entites.Priority;
//import com.comakeit.strs.entites.Role;
//import com.comakeit.strs.entites.User;
//import com.comakeit.strs.entites.Priority;
//import com.comakeit.strs.entites.Role;
//import com.comakeit.strs.entites.User;
import com.comakeit.strs.services.UserServices;

@RestController
@RequestMapping("user")
public class UserRestController {
	@Autowired
	private UserServices userServices ;

	@RequestMapping(value= "/Validate",
					method = RequestMethod.POST)
	public Role validateAndGetRole(@RequestBody User user) {
		System.out.println("\n\nInUserRestController\n\n");
		
		return userServices.validate(user);
	}

	@RequestMapping(value="/getDepartments")
	public List<Department> getListOfDepartment(){
		System.out.println("\n\npublic List<Department> getListOfDepartment(){\n\n");
		return userServices.getListOfDepartment(); 
	}
	
	@RequestMapping(value="/getPriorities")
	public List<Priority> getListOfPriority(){
		System.out.println("\n\npublic List<Priority> getListOfPriority(){\n\n");
		return userServices.getListOfPriority(); 
	}
	
	@RequestMapping(value="/getUsers")
	public List<User> getListOfUsers(){
		System.out.println("public List<Priority> getListOfUsers(){");
		return userServices.getListOfUsers();
	}
	
//	insertTicket
	@RequestMapping(value="/insertTicket")
	public String insertTicket(@RequestBody Ticket ticket){
		System.out.println("\n\npublic String insertTicket(@RequestBody Ticket ticket){");
		System.out.println("\ninsertTicket in UserRestController -----> " + ticket + "\n");

		return userServices.insertTicket(ticket);
	}
	
}
