package com.comakeit.strs.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.comakeit.strs.constants.Constants;
import com.comakeit.strs.entites.Department;
import com.comakeit.strs.entites.Priority;
import com.comakeit.strs.entites.Role;
import com.comakeit.strs.entites.ServiceEngineer;
import com.comakeit.strs.entites.Status;
import com.comakeit.strs.entites.User;
import com.comakeit.strs.exceptions.STRSUserNotAddedExcpetion;

@Controller
public class AdminController {

    RestTemplate restTemplate = new RestTemplate();
    
    @RequestMapping("admin-Show_All_User")
    public ModelAndView Show_All_User(HttpSession session) {
    	try {
	        ResponseEntity<List<User>> responseEntityUsers= restTemplate.exchange(
	                Constants.url + "/admin" +  "/getAllUsers",
	                HttpMethod.GET, null, 
	                new ParameterizedTypeReference<List<User>>() {});
	        
	        List<User> listOfUsers = (List<User>) responseEntityUsers.getBody();
	        
	        session.setAttribute("listOfUsers", listOfUsers);
    	}catch(Exception e) {
    		
    	}
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Admin.jsp?operation=Show_All_User");
        
        return modelAndView;
    }
//  Add_User
    @RequestMapping("Add_User")
    public ModelAndView addUser(User user) {
        
    	ModelAndView modelAndView = new ModelAndView();
    	
        if(     user.getName().equals("") || 
                user.getPassword().equals("") ||
                user.getUser_name().equals("")) {
            return new ModelAndView("Admin.jsp?operation=Add_user&warning=changeUserName");
        }
        
        try {
	        String status = restTemplate.postForObject(
	                Constants.url + "/admin/addUser", 
	                user,
	                String.class);
	        
	        if(status.equals("added")) {
	            modelAndView.setViewName("Admin.jsp?operation=addedUser");
	        }else if(status.equals("notAdded")) {
	            modelAndView.setViewName("Admin.jsp?operation=Add_user&warning=changeUserName");
	        }
        }catch(Exception e) {

        	modelAndView.setViewName("Admin.jsp?operation=Add_user&warning=changeUserName");
        }
               
        return modelAndView;
    }
    
    @RequestMapping("Add_Service_Engineer")
    public ModelAndView addServiceEngineer(
            String name,
            String user_name,
            String password,
            
            String departmentName,
            
            HttpSession session) {
        
        /* null | empty check */
        if(name.equals("") || user_name.equals("") || password.equals("")) {
            return new ModelAndView("Admin.jsp?operation=Add_Service_Engineer&warning=changeUserName");
        }
        
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setUser_name(user_name);
        
        try {
        /* adding user in User Table */
        user = restTemplate.postForObject(
                Constants.url + "/admin/addUserServiceEngineer", 
                user,
                User.class);
        }catch(Exception e) {
        	
        }
        
        ModelAndView modelAndView = new ModelAndView();
        if(user != null) {

        }else{
            modelAndView.setViewName("Admin.jsp?operation=Add_Service_Engineer&warning=changeUserName");
            return modelAndView;
        }
        /*----- END: Adding user in user table */
        
        /* -- START: adding ServiceEngineer */
        ServiceEngineer serviceEngineer = new ServiceEngineer();
        
        List<Department> listOfDepartments = (List<Department>)session.getAttribute("listOfDepartments");
        for(Department department : listOfDepartments) {
            if(department.getName().equals( departmentName ))
                serviceEngineer.setDepartment(department);
        }
        serviceEngineer.setTotal_tickets_worked_on(0);
        serviceEngineer.setUser(user);
        
        
        String statusOfServiceEngineer = restTemplate.postForObject(
                Constants.url + "/admin/addServiceEngineer", 
                serviceEngineer,
                String.class);
        
        if(statusOfServiceEngineer.equals("added")) {
            modelAndView.setViewName("Admin.jsp?operation=addedServiceEngineer");
        }else if(statusOfServiceEngineer.equals("notAdded")) {
            modelAndView.setViewName("Admin.jsp?operation=Add_user?warning=changeUserName");
        }
        /* -- END: adding ServiceEngineer */
        
        return modelAndView;
    }
        
//  admin-ShowStatusesAndAddStatuses
    @RequestMapping("admin-ShowStatusesAndAddStatuses")
    public ModelAndView showStatusesAndAddStatuses(HttpSession session) {
        ResponseEntity<List<Status>> responseEntityStatuses = restTemplate.exchange(
                Constants.url + "/status" +  "/getAllStatuses",
        HttpMethod.GET, null, 
        new ParameterizedTypeReference<List<Status>>() {});
        
        List<Status> listOfStatuses = responseEntityStatuses.getBody();
        
        session.setAttribute("listOfStatuses", listOfStatuses);
        
        ModelAndView modelAndView = new ModelAndView("Admin.jsp?operation=ShowStatusesAndAddStatuses");
    
        return modelAndView;
    }
    
//  admin-addNewStatus
    @RequestMapping("admin-addNewStatus")
    public ModelAndView addNewStatus(
            String newStatusValue,
            String newStatusCode,
            
            HttpSession session) {
        
        if(newStatusValue.equals("") || newStatusCode.equals("")) {
            return new ModelAndView("Admin.jsp?status=notAddedNewStatus&operation=ShowStatusesAndAddStatuses");
        }
        
        ModelAndView modelAndView = new ModelAndView();
        
        HashMap<String, String> newStatusDetails = new HashMap<String, String>();
        newStatusDetails.put("newStatusValue", newStatusValue);
        newStatusDetails.put("newStatusCode", newStatusCode);

        String statusOfNewStatus = restTemplate.postForObject(
                Constants.url + "/admin/addNewStatus", 
                newStatusDetails,
                String.class);
                
        if(statusOfNewStatus.equals("added")) {

            modelAndView.setViewName("admin-ShowStatusesAndAddStatuses");
        }else if(statusOfNewStatus.equals("notAdded")) {
            modelAndView.setViewName("Admin.jsp?status=notAddedNewStatus&operation=ShowStatusesAndAddStatuses");
        }

        return modelAndView;
    }
    
//  admin-ShowRolesAndAddRole
    @RequestMapping("admin-ShowRolesAndAddRole")
    public ModelAndView ShowRolesAndAddRole(HttpSession session) {
        ResponseEntity<List<Role>> responseEntityRoles = restTemplate.exchange(
                Constants.url + "/role" +  "/getAllRoles",
        HttpMethod.GET, null, 
        new ParameterizedTypeReference<List<Role>>() {});
        
        List<Role> listOfRoles = responseEntityRoles.getBody();
        
        session.setAttribute("listOfRoles", listOfRoles);
        
        ModelAndView modelAndView = new ModelAndView("Admin.jsp?operation=ShowRolesAndAddRole");
    
        return modelAndView;
    }
    
//  admin-addNewRole
    @RequestMapping("admin-addNewRole")
    public ModelAndView addNewRole(
            String newRoleName,
            String newRoleCode,
            
            HttpSession session) {
        
        if(newRoleName.equals("") || newRoleCode.equals("")) {
            return new ModelAndView("Admin.jsp?status=notAddedNewRole&operation=admin-ShowRolesAndAddRole");
        }
        ModelAndView modelAndView = new ModelAndView();
        
        HashMap<String, String> newRoleDetails = new HashMap<String, String>();
        newRoleDetails.put("newRoleName", newRoleName);
        newRoleDetails.put("newRoleCode", newRoleCode);
        
        String statusOfNewRole = restTemplate.postForObject(
                Constants.url + "/admin/addNewRole", 
                newRoleDetails,
                String.class);
                
        if(statusOfNewRole.equals("added")) {

            modelAndView.setViewName("admin-ShowRolesAndAddRole");
        }else if(statusOfNewRole.equals("notAdded")) {
            modelAndView.setViewName("Admin.jsp?status=notAddedNewRole&operation=ShowRolesAndAddRole");
        }

        return modelAndView;
    }

//  admin-ShowPrioritiesAndAddPriority
    @RequestMapping("admin-ShowPrioritiesAndAddPriority")
    public ModelAndView ShowPrioritiesAndAddPriority(HttpSession session) {

        ResponseEntity<List<Priority>> responseEntityPriorities = restTemplate.exchange(
                Constants.url + "/priority" +  "/getAllPriorities",
        HttpMethod.GET, null, 
        new ParameterizedTypeReference<List<Priority>>() {});
        
        List<Priority> listOfPriorities = responseEntityPriorities.getBody();
        
        session.setAttribute("listOfPriorities", listOfPriorities);
        
        ModelAndView modelAndView = new ModelAndView("Admin.jsp?operation=ShowPrioritiesAndAddPriority");
    
        return modelAndView;
    }
    
    @RequestMapping("admin-addNewPriority")
    public ModelAndView addNewPriority(
            String newPriorityValue,
            String newPriorityCode,
            
            HttpSession session) {
        
        if(newPriorityValue.equals("") || newPriorityCode.equals("")) {
            return new ModelAndView("Admin.jsp?status=notAddedNewPriority&operation=ShowPrioritiesAndAddPriority");
        }
        ModelAndView modelAndView = new ModelAndView();
        
        HashMap<String, String> newPriorityDetails = new HashMap<String, String>();
        newPriorityDetails.put("newPriorityValue", newPriorityValue);
        newPriorityDetails.put("newPriorityCode", newPriorityCode);
        
        String statusOfNewPriority = restTemplate.postForObject(
                Constants.url + "/admin/addNewPriority", 
                newPriorityDetails,
                String.class);
                
        if(statusOfNewPriority.equals("added"))
            modelAndView.setViewName("admin-ShowPrioritiesAndAddPriority");
        else if(statusOfNewPriority.equals("notAdded"))
            modelAndView.setViewName("Admin.jsp?status=notAddedNewPriority&operation=ShowPrioritiesAndAddPriority");
        
        return modelAndView;
    }
    
//  admin-ShowDepartmentsAndAddDepartment
    @RequestMapping("admin-ShowDepartmentsAndAddDepartment")
    public ModelAndView showDepartmentsAndAddDepartment(HttpSession session) {

        ResponseEntity<List<Department>> responseEntityDepartments = restTemplate.exchange(
                Constants.url + "/user" +  "/getDepartments",
        HttpMethod.GET, null, 
        new ParameterizedTypeReference<List<Department>>() {});
        
        List<Department> listOfDepartments = responseEntityDepartments.getBody();
        
        session.setAttribute("listOfDepartments", listOfDepartments);
        
        ModelAndView modelAndView = new ModelAndView("Admin.jsp?operation=ShowDepartmentsAndAddDepartment");
        return modelAndView;
    }
    
    @RequestMapping("admin-addNewDepartment")
    public ModelAndView addNewDepartment(
            String newDepartmentName,
            String newDepartmentCode,
            
            HttpSession session) {
        
        if(newDepartmentName.equals("") || newDepartmentCode.equals("")) {
            return new ModelAndView("Admin.jsp?status=notAddedNewDepartment&operation=ShowDepartmentsAndAddDepartment");
        }
        
        ModelAndView modelAndView = new ModelAndView();
        
        HashMap<String, String> newDepartmentDetails = new HashMap<String, String>();
        newDepartmentDetails.put("newDepartmentName", newDepartmentName);
        newDepartmentDetails.put("newDepartmentCode", newDepartmentCode);

        String statusOfNewDepartment = restTemplate.postForObject(
                Constants.url + "/admin/addNewDepartment", 
                newDepartmentDetails,
                String.class);
                
        if(statusOfNewDepartment.equals("added")) {
            modelAndView.setViewName("admin-ShowDepartmentsAndAddDepartment");
        }else if(statusOfNewDepartment.equals("notAdded")) {
            modelAndView.setViewName("Admin.jsp?status=notAddedNewDepartment&operation=ShowDepartmentsAndAddDepartment");
        }
        
        return modelAndView;
    }
}
