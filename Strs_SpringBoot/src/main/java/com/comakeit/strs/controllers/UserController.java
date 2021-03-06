package com.comakeit.strs.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.comakeit.strs.constants.Constants;
import com.comakeit.strs.entites.Department;
import com.comakeit.strs.entites.Priority;
import com.comakeit.strs.entites.Role;
import com.comakeit.strs.entites.Ticket;
import com.comakeit.strs.entites.User;


@Controller
public class UserController {
	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping("Validate")
	public ModelAndView validate(User user, HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		Role role = null;
		try {
			role = restTemplate.postForObject(	Constants.url + "/user" +  "/Validate", 
													user, 
													Role.class);
	
			HttpSession session = request.getSession();
			session.setAttribute("user_name", user.getUser_name());
			
			if(role.getCode().equals("SER_ENGG")) {
				modelAndView.setViewName("ServiceEngineer.jsp");
				
				/* set listOfDepartments */
				ResponseEntity<List<Department>> responseEntityDepartments = restTemplate.exchange(
						Constants.url + "/user" +  "/getDepartments",
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Department>>() {});
			
				
				List<Department> listOfDepartments = responseEntityDepartments.getBody();
				
				session.setAttribute("listOfDepartments", listOfDepartments);
				
				/* listOfPriorities */
				ResponseEntity<List<Priority>> responseEntityPriorities= restTemplate.exchange(
						Constants.url + "/user" +  "/getPriorities",
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Priority>>() {});
				
				List<Priority> listOfPriorities = responseEntityPriorities.getBody();
				session.setAttribute("listOfPriorities", listOfPriorities);
				
				/* listOfUsers */
				ResponseEntity<List<User>> responseEntityUsers= restTemplate.exchange(
						Constants.url + "/user" +  "/getUsers",
						HttpMethod.GET, null, 
						new ParameterizedTypeReference<List<User>>() {});
				
				List<User> listOfUsers = responseEntityUsers.getBody();
				session.setAttribute("listOfUsers", listOfUsers);
				
				return modelAndView;
	
			}else if(role.getCode().equals("END_U")) {
	
				/* set listOfDepartments */
				ResponseEntity<List<Department>> responseEntityDepartments = restTemplate.exchange(
						Constants.url + "/user" +  "/getDepartments",
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Department>>() {});
				
				List<Department> listOfDepartments = responseEntityDepartments.getBody();
				
				session.setAttribute("listOfDepartments", listOfDepartments);
				
				/* listOfPriorities */
				ResponseEntity<List<Priority>> responseEntityPriorities= restTemplate.exchange(
						Constants.url + "/user" +  "/getPriorities",
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Priority>>() {});
				
				List<Priority> listOfPriorities = responseEntityPriorities.getBody();
				session.setAttribute("listOfPriorities", listOfPriorities);
				
				/* listOfUsers */
				ResponseEntity<List<User>> responseEntityUsers= restTemplate.exchange(
						Constants.url + "/user" +  "/getUsers",
						HttpMethod.GET, null, 
						new ParameterizedTypeReference<List<User>>() {});
				
				List<User> listOfUsers = responseEntityUsers.getBody();
				session.setAttribute("listOfUsers", listOfUsers);
	
				modelAndView.setViewName("EndUser.jsp");
				return modelAndView;
			}else if(role.getCode().equals("ADMN")) {
				
				/* set listOfRoles */
				ResponseEntity<List<Department>> responseEntityDepartment = restTemplate.exchange(
						Constants.url + "/user" +  "/getDepartments",
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Department>>() {});
				
				List<Department> listOfDepartments = responseEntityDepartment.getBody();
				session.setAttribute("listOfDepartments", listOfDepartments);
				
				modelAndView.setViewName("Admin.jsp");
				return modelAndView;
			}
		}catch(Exception e) {

			modelAndView.setViewName("index.jsp?warning=UnAuthorizedLogin");
			return modelAndView;
		}

		return new ModelAndView();
	}
	
	@RequestMapping(value = "ShowFormRaiseTicket", method= RequestMethod.GET)
	public ModelAndView showFormRaiseTicket(HttpServletRequest request) {
	
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("EndUser.jsp?operation=RaiseTicket");

		return modelAndView;
	}
	@RequestMapping(value = "ShowAllTickets", method= RequestMethod.GET)
	public ModelAndView showAllTickets(HttpSession session) {
		
		/* get all the tickets and put them in session and redirect to EndUser.jsp to display them */
		/* listOfUsers */
		ResponseEntity<List<Ticket>> responseEntityUsers= restTemplate.exchange(
				Constants.url + "/ticket" +  "/getAll" + "/" + session.getAttribute("user_name"),
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Ticket>>() {});
		
		List<Ticket> listOfTickets = responseEntityUsers.getBody();
		session.setAttribute("listOfTickets", listOfTickets);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("EndUser.jsp?operation=ShowAllTickets");
		
		return modelAndView;
	}

	@RequestMapping(value= "RaiseTicket",
			method= RequestMethod.POST)
	public ModelAndView RaiseTicket(
			String IssueCategory,
			String message,
			String priority,
			String start_date,
			String requested_end_date ,
			
			HttpSession session
			) {
		
		Ticket ticket = new Ticket();
		/* --------------------- */
		/* set listOfDepartments */
		ResponseEntity<List<Department>> responseEntityDepartments = restTemplate.exchange(
				Constants.url + "/user" +  "/getDepartments",
		HttpMethod.GET, null, 
		new ParameterizedTypeReference<List<Department>>() {});
		
		List<Department> listOfDepartments = responseEntityDepartments.getBody();
		
		session.setAttribute("listOfDepartments", listOfDepartments);
		
		/* listOfPriorities */
		ResponseEntity<List<Priority>> responseEntityPriorities= restTemplate.exchange(
				Constants.url + "/user" +  "/getPriorities",
		HttpMethod.GET, null, 
		new ParameterizedTypeReference<List<Priority>>() {});
		
		List<Priority> listOfPriorities = responseEntityPriorities.getBody();
		session.setAttribute("listOfPriorities", listOfPriorities);
		
		/* listOfUsers */
		ResponseEntity<List<User>> responseEntityUsers= restTemplate.exchange(
				Constants.url + "/user" +  "/getUsers",
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<User>>() {});
		
		List<User> listOfUsers = responseEntityUsers.getBody();
		session.setAttribute("listOfUsers", listOfUsers);
		/*-------------------------------*/

		for(Department eachDepartment : listOfDepartments) {
			if(eachDepartment.getName().equals( IssueCategory ))
				ticket.setCategory( eachDepartment );
		}
		
		ticket.setMessage(message);
		
		for(Priority eachPriority : listOfPriorities)
			if(eachPriority.getValue().equals(priority))
				ticket.setPriority( eachPriority );
		
		
		ticket.setStart_date( LocalDate.parse(start_date) );
		ticket.setRequested_end_date( LocalDate.parse(requested_end_date) );
		
		String requested_by = (String) session.getAttribute("user_name");
		for(User eachUser : listOfUsers)
			if(eachUser.getUser_name().equals(requested_by))
				ticket.setRequested_by( eachUser );
		
		/* insert ticket and assign to a serviceEmployee */
		try {
				restTemplate.postForObject(
				Constants.url + "/user/insertTicket",
				ticket,
				ResponseEntity.class);
		}catch(Exception e) {
			System.out.println("Exception In UserController: ");
			e.printStackTrace();
		}
				
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("EndUser.jsp?operation=TicketGenerated");

		return modelAndView; 
	}
}
