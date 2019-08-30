package com.comakeit.strs.controllers;

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
		System.out.println("public ModelAndView addUser(User user) {");
		System.out.println("User object = " + user);
		
		String status = restTemplate.postForObject(
				Constants.url + "/admin/addUser", 
				user,
				String.class);
				
		System.out.println("\n\n--> Status = " + status);
		
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
		
		System.out.println("\ndepartmentName = " + departmentName + "\n");
		
		/* adding user in User Table */
		user = restTemplate.postForObject(
				Constants.url + "/admin/addUserServiceEngineer", 
				user,
				User.class);
		
		ModelAndView modelAndView = new ModelAndView();
		if(user != null) {
			System.out.println("Inserted User of ServiceEngineer");

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
		
		System.out.println("\n\nuser after saving = " + user + "\n");
		
		
		System.out.println("serviceEngineer object = " + serviceEngineer);
		
		String statusOfServiceEngineer = restTemplate.postForObject(
				Constants.url + "/admin/addServiceEngineer", 
				serviceEngineer,
				String.class);
				
		System.out.println("\n\n--> statusOfServiceEngineer = " + statusOfServiceEngineer);
		
		if(statusOfServiceEngineer.equals("added")) {
			modelAndView.setViewName("Admin.jsp?operation=addedServiceEngineer");
		}else if(statusOfServiceEngineer.equals("notAdded")) {
			modelAndView.setViewName("Admin.jsp?operation=Add_user?warning=changeUserName");
		}
		/* -- END: adding ServiceEngineer */
		
		return modelAndView;
	}
	
}
