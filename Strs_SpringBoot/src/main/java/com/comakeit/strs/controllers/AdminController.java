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
import com.comakeit.strs.entites.Role;
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
		
		if(		user.getName().equals("") || 
				user.getPassword().equals("") ||
				user.getUser_name().equals("")) {
			return new ModelAndView("Admin.jsp?operation=Add_user&warning=changeUserName");
		}
		
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
		
		/* null | empty check */
		if(name.equals("") || user_name.equals("") || password.equals("")) {
			return new ModelAndView("Admin.jsp?operation=Add_Service_Engineer&warning=changeUserName");
		}
		
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
		
//	admin-ShowStatusesAndAddStatuses
	@RequestMapping("admin-ShowStatusesAndAddStatuses")
	public ModelAndView showStatusesAndAddStatuses(HttpSession session) {
		/* set listOfDepartments */
		ResponseEntity<List<Status>> responseEntityStatuses = restTemplate.exchange(
				Constants.url + "/status" +  "/getAllStatuses",
		HttpMethod.GET, null, 
		new ParameterizedTypeReference<List<Status>>() {});
		
		List<Status> listOfStatuses = responseEntityStatuses.getBody();
		
		session.setAttribute("listOfStatuses", listOfStatuses);
		System.out.println("\nlistOfStatuses = > " + listOfStatuses + "\n\n");
		
		ModelAndView modelAndView = new ModelAndView("Admin.jsp?operation=ShowStatusesAndAddStatuses");
	
		return modelAndView;
	}
	
//	admin-addNewStatus
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
		
		System.out.println("CHECK -> " + newStatusDetails.get("newStatusCode"));

		
		String statusOfNewStatus = restTemplate.postForObject(
				Constants.url + "/admin/addNewStatus", 
				newStatusDetails,
				String.class);
				
		System.out.println("\n\n--> statusOfNewDepartment = " + statusOfNewStatus);
		if(statusOfNewStatus.equals("added")) {

			modelAndView.setViewName("admin-ShowStatusesAndAddStatuses");
		}else if(statusOfNewStatus.equals("notAdded")) {
			modelAndView.setViewName("Admin.jsp?status=notAddedNewStatus&operation=ShowStatusesAndAddStatuses");
		}

		return modelAndView;
	}
	
//	admin-ShowRolesAndAddRole
	@RequestMapping("admin-ShowRolesAndAddRole")
	public ModelAndView ShowRolesAndAddRole(HttpSession session) {
		/* set listOfDepartments */
		ResponseEntity<List<Role>> responseEntityRoles = restTemplate.exchange(
				Constants.url + "/role" +  "/getAllRoles",
		HttpMethod.GET, null, 
		new ParameterizedTypeReference<List<Role>>() {});
		
		List<Role> listOfRoles = responseEntityRoles.getBody();
		
		session.setAttribute("listOfRoles", listOfRoles);
		System.out.println("\nlistOfRoles = > " + listOfRoles + "\n\n");
		
		ModelAndView modelAndView = new ModelAndView("Admin.jsp?operation=ShowRolesAndAddRole");
	
		return modelAndView;
	}
	
//	admin-addNewRole
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
				
		System.out.println("\n\n--> statusOfNewRole = " + statusOfNewRole);
		if(statusOfNewRole.equals("added")) {

			modelAndView.setViewName("admin-ShowRolesAndAddRole");
		}else if(statusOfNewRole.equals("notAdded")) {
			modelAndView.setViewName("Admin.jsp?status=notAddedNewRole&operation=ShowRolesAndAddRole");
		}

		return modelAndView;
	}
//	admin-ShowPrioritiesAndAddPriority
	
	
	
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
		System.out.println("\nlistOfDepartments = > " + listOfDepartments + "\n\n");
		
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
				
		System.out.println("\n\n--> statusOfNewDepartment = " + statusOfNewDepartment);
		if(statusOfNewDepartment.equals("added")) {
			modelAndView.setViewName("admin-ShowDepartmentsAndAddDepartment");
		}else if(statusOfNewDepartment.equals("notAdded")) {
			modelAndView.setViewName("Admin.jsp?status=notAddedNewDepartment&operation=ShowDepartmentsAndAddDepartment");
		}
		
		return modelAndView;
	}
}
