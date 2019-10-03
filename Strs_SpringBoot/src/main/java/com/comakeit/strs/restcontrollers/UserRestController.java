package com.comakeit.strs.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.strs.entites.Department;
import com.comakeit.strs.entites.Priority;
import com.comakeit.strs.entites.Role;
import com.comakeit.strs.entites.Ticket;
import com.comakeit.strs.entites.User;
import com.comakeit.strs.exceptions.STRSUnAuthorizedException;
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
    public Role validateAndGetRole(@RequestBody User user) {// throws STRSUnAuthorizedException{

   		Role role = userServices.validate(user);
   		System.out.println("role : in Rest = " + role);
   		
		if(role == null) {
			throw new STRSUnAuthorizedException("Exception");
		}
		return role;
    }

    @RequestMapping(value="/getDepartments")
    public List<Department> getListOfDepartment(){
        return userServices.getListOfDepartment(); 
    }
    
    @RequestMapping(value="/getPriorities")
    public List<Priority> getListOfPriority(){
        return userServices.getListOfPriority(); 
    }
    
    @RequestMapping(value="/getUsers")
    public List<User> getListOfUsers(){
        return userServices.getListOfUsers();
    }
    
//  insertTicket
    @RequestMapping(value="/insertTicket")
    public String insertTicket(@RequestBody Ticket ticket){
        return userServices.insertTicket(ticket);
    }
    
}
