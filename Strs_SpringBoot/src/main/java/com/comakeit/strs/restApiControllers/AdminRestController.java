package com.comakeit.strs.restApiControllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.strs.entites.ServiceEngineer;
import com.comakeit.strs.entites.User;
import com.comakeit.strs.services.AdminServices;

@RestController
@RequestMapping("admin")
public class AdminRestController {
    
    @Autowired
    private AdminServices adminServices;

    @RequestMapping(value= "/getAllUsers",
            method = RequestMethod.GET)
    public List<User> getAllUses() {
        return adminServices.getAllUses();
    }
    
    @RequestMapping(value= "/addUser",
            method = RequestMethod.POST)
    public String addUser(@RequestBody User user) {
        return adminServices.addUser(user);
    }
    
    @RequestMapping(value= "/addUserServiceEngineer",
            method = RequestMethod.POST)
    public User addUserServiceEngineer(@RequestBody User user) {
        return adminServices.addUserServiceEngineer(user);
    }
    
    @RequestMapping(value= "/addServiceEngineer",
            method = RequestMethod.POST)
    public String addServiceEngineer(@RequestBody ServiceEngineer serviceEngineer) {
        return adminServices.addServiceEngineer(serviceEngineer);
    }
    
//  addNewDepartment
    @RequestMapping(value= "/addNewDepartment",
            method = RequestMethod.POST)
    public String addNewDepartment(@RequestBody HashMap<String, String> newDepartmentDetails) {
        return adminServices.addNewDepartment(newDepartmentDetails);
    }
    
//  addNewStatus
    @RequestMapping(value= "/addNewStatus",
            method = RequestMethod.POST)
    public String addNewStatus(@RequestBody HashMap<String, String> newStatusDetails) {
        return adminServices.addNewStatus(newStatusDetails);
    }
    
//  addNewRole
    @RequestMapping(value= "/addNewRole",
            method = RequestMethod.POST)
    public String addNewRole(@RequestBody HashMap<String, String> newRoleDetails) {
        return adminServices.addNewRole(newRoleDetails);
    }
    
//  addNewPriority
    @RequestMapping(value= "/addNewPriority",
            method = RequestMethod.POST)
    public String addNewPriority(@RequestBody HashMap<String, String> newPriorityDetails) {
        return adminServices.addNewPriority(newPriorityDetails);
    }
}
