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
import com.comakeit.strs.entites.ServiceEngineer;
import com.comakeit.strs.entites.Status;
import com.comakeit.strs.entites.User;

@Controller
public class AdminController {

	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping("admin-Show_All_User")
	public ModelAndView Show_All_User(HttpSession session) {
//		get-all-users
		ResponseEntity<List<User>> responseEntityUsers= restTemplate.exchange(
				Constants.url + "/admin" +  "/getAllUsers",
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<User>>() {});
		
		List<User> listOfUsers = (List<User>) responseEntityUsers.getBody();
		
		session.setAttribute("listOfUsers", listOfUsers);
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Admin.jsp?operation=Show_All_User");
		
		return modelAndView;
	}
//	Add_User
	@RequestMapping("Add_User")
	public ModelAndView addUser(User user) {
		
		String status = restTemplate.postForObject(
				Constants.url + "/admin/addUser", 
				user,
				String.class);
				
		ModelAndView modelAndView = new ModelAndView();
		if(status.equals("added")) {
			modelAndView.setViewName("Admin.jsp?operation=addedUser");
		}else if(status.equals("notAdded")) {
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

		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setUser_name(user_name);
		
		/* adding user in User Table */
		user = restTemplate.postForObject(
				Constants.url + "/admin/addUserServiceEngineer", 
				user,
				User.class);
		
		ModelAndView modelAndView = new ModelAndView();
		if(user != null) {
			/* System.out.println("Inserted User of ServiceEngineer"); */

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
	
//	admin-ShowDepartmentsAndAddDepartment
	@RequestMapping("admin-ShowDepartmentsAndAddDepartment")
	public ModelAndView showDepartmentsAndAddDepartment(HttpSession session) {
		/* set listOfDepartments */
		ResponseEntity<List<Department>> responseEntityDepartments = restTemplate.exchange(
				Constants.url + "/user" +  "/getDepartments",
		HttpMethod.GET, null, 
		new ParameterizedTypeReference<List<Department>>() {});
		
		List<Department> listOfDepartments = responseEntityDepartments.getBody();
		
		session.setAttribute("listOfDepartments", listOfDepartments);
		
		ModelAndView modelAndView = new ModelAndView("Admin.jsp?operation=ShowDepartmentsAndAddDepartment");
		return modelAndView;
	}
	
//	admin-ShowStatusesAndAddStatuses
	@RequestMapping("admin-ShowStatusesAndAddStatuses")
	public ModelAndView showStatusesAndAddStatuses(HttpSession session) {
		/* set listOfDepartments */
		ResponseEntity<List<Status>> responseEntityDepartments = restTemplate.exchange(
				Constants.url + "/status" +  "/getAllStatuses",
		HttpMethod.GET, null, 
		new ParameterizedTypeReference<List<Status>>() {});
		
		List<Status> listOfStatuses = responseEntityDepartments.getBody();
		
		session.setAttribute("listOfStatuses", listOfStatuses);
		
		ModelAndView modelAndView = new ModelAndView("Admin.jsp?operation=ShowDepartmentsAndAddDepartment");
		return modelAndView;
	}
	
//	admin-ShowRolesAndAddRole
	
//	admin-ShowPrioritiesAndAddPriority
	
	
	@RequestMapping("admin-addNewDepartment")
	public ModelAndView addNewDepartment(
			String newDepartmentName,
			String newDepartmentCode,
			
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		HashMap<String, String> newDepartmentDetails = new HashMap<String, String>();
		newDepartmentDetails.put("newDepartmentName", newDepartmentName);
		newDepartmentDetails.put("newDepartmentCode", newDepartmentCode);
		
		String statusOfNewDepartment = restTemplate.postForObject(
				Constants.url + "/admin/addNewDepartment", 
				newDepartmentDetails,
				String.class);
				
		if(statusOfNewDepartment.equals("added")) {
			/* set listOfDepartments */
			ResponseEntity<List<Department>> responseEntityDepartments = restTemplate.exchange(
					Constants.url + "/user" +  "/getDepartments",
			HttpMethod.GET, null, 
			new ParameterizedTypeReference<List<Department>>() {});
			
			List<Department> listOfDepartments = responseEntityDepartments.getBody();
			
			session.setAttribute("listOfDepartments", listOfDepartments);
			
			modelAndView.setViewName("Admin.jsp?status=addedNewDepartment&operation=ShowDepartmentsAndAddDepartment");
		}else if(statusOfNewDepartment.equals("notAdded")) {
//			notAddedNewDepartment
			modelAndView.setViewName("Admin.jsp?status=notAddedNewDepartment&operation=ShowDepartmentsAndAddDepartment");
		}
		
		return modelAndView;
	}
}
