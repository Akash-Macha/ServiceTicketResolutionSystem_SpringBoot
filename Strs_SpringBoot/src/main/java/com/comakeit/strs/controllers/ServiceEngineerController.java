package com.comakeit.strs.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.comakeit.strs.constants.Constants;
import com.comakeit.strs.entites.Priority;
import com.comakeit.strs.entites.Ticket;

@Controller
public class ServiceEngineerController {
	
	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value = "/SE-ShowAllTickets", method= RequestMethod.GET)
	public ModelAndView showAllTickets(HttpSession session) {
		System.out.println("Inside: public ModelAndView showAllTickets(HttpSession session) {");
		
		/* get all the tickets and put them in session and redirect to EndUser.jsp to display them */
		/* listOfUsers */
		System.out.println("\n\nINSIDE /SE-ShowAllTickets user_name in session = " + session.getAttribute("user_name") + "\n\n");
		ResponseEntity<List<Ticket>> responseEntityUsers= restTemplate.exchange(
				Constants.url + "/ticket" +  "/getAll" + "/" + session.getAttribute("user_name"),
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Ticket>>() {});
		
		List<Ticket> listOfTickets = (List<Ticket>) responseEntityUsers.getBody();
		session.setAttribute("listOfTickets", listOfTickets);
		System.out.println("\nListOfTickets = " + listOfTickets + "\n\n");
		
		
		/* listOfPriorities */
		ResponseEntity<List<Priority>> responseEntityPriorities= restTemplate.exchange(
				Constants.url + "/user" +  "/getPriorities",
		HttpMethod.GET, null, 
		new ParameterizedTypeReference<List<Priority>>() {});
		
		List<Priority> listOfPriorities = responseEntityPriorities.getBody();
		session.setAttribute("listOfPriorities", listOfPriorities);
		System.out.println("n\nlistOfPriorities = " + listOfPriorities + "\n\n");
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ServiceEngineer.jsp?operation=ShowAllTickets");
		
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/SE-updateTicketPriroty", ///{TicketId}/{newPriority}", 
			method= RequestMethod.GET)
	public ModelAndView updateTicket(HttpServletRequest request,  HttpSession session) {														
		String ticket_id = request.getParameter("TicketId");
		String newPriorityValue = request.getParameter("newPriorityValue");
		
		System.out.println("****************** HIT:  @RequestMapping(value = \"/SE-updateTicketPriroty\",");
		
		ArrayList<String> updateTicketValues = new ArrayList<String>();
		updateTicketValues.add(ticket_id);
		updateTicketValues.add(newPriorityValue);

		
		System.out.println("\n*********!! Values in serviceEngineer Controller : " + updateTicketValues);
		
		/* */
		String status = restTemplate.postForObject(
				Constants.url + "/serviceEngineer/updateTicketPriority", 
				updateTicketValues,
				String.class);
				
		System.out.println("\n\n--> Status = " + status);
		
////		/* REFRESHING  ListOfTIckets */
		ResponseEntity<List<Ticket>> responseEntityUsers= restTemplate.exchange(
				Constants.url + "/ticket" +  "/getAll" + "/" + session.getAttribute("user_name"),
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Ticket>>() {});
		
		List<Ticket> listOfTickets = (List<Ticket>) responseEntityUsers.getBody();
		session.setAttribute("listOfTickets", listOfTickets);
		System.out.println("\nListOfTickets = " + listOfTickets + "\n\n");
	
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ServiceEngineer.jsp?operation=ShowAllTickets");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/SE-CloseTicket", ///{TicketId}/{newPriority}", 
			method= RequestMethod.POST)
	public ModelAndView closeTicket(HttpServletRequest request,  HttpSession session) {
		
		System.out.println("Inside: public ModelAndView closeTicket(HttpServletRequest request,  HttpSession session) {");
		String TicketId = request.getParameter("TicketId");
		
		System.out.println("\n->->Ticket ID = " + TicketId + "\n\n");
		
		/* close ticket */
		ResponseEntity<String> responseEntityCloseTicket= restTemplate.exchange(
				Constants.url + "/ticket" +  "/close" + "/" + TicketId,
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<String>() {});
		
		System.out.println("\n---> Ticket has been closed \n");
		
		if(responseEntityCloseTicket.getBody().equals("true")) {
			/* check for other priority tickets */
			ResponseEntity<String> responseEntityCheckPendingTickets = restTemplate.exchange(
					Constants.url + "/ticket" +  "/otherPendingTickets" + "/" + TicketId,
					HttpMethod.GET, null, 
					new ParameterizedTypeReference<String>() {});
			
			if(responseEntityCheckPendingTickets.getBody().equals("true")) {
				System.out.println("\n\n->There are pending tickets\n\n");
			}else {
				System.out.println("\n\n->There are no pending tickets\n\n");
			}
			
		}
		
		/* refresh the listOfTickets */
		ResponseEntity<List<Ticket>> responseEntityGetListOfTickets= restTemplate.exchange(
				Constants.url + "/ticket" +  "/getAll" + "/" + session.getAttribute("user_name"),
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Ticket>>() {});
		
		List<Ticket> listOfTickets = (List<Ticket>) responseEntityGetListOfTickets.getBody();
		session.setAttribute("listOfTickets", listOfTickets);
		System.out.println("\nListOfTickets = " + listOfTickets + "\n\n");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ServiceEngineer.jsp?operation=ShowAllTickets");
		
		return modelAndView;
	}
	
//	SE-Average_Time_Taken_Per_Engineer	
	@RequestMapping(value = "/SE-Average_Time_Taken_Per_Engineer", method= RequestMethod.GET)
	public ModelAndView Average_Time_Taken_Per_Engineer(HttpSession session) {
		System.out.println("In public ModelAndView Average_Time_Taken_Per_Engineer(HttpSession session) {\n"
				+ "user_name = " + session.getAttribute("user_name"));
		
		
		ResponseEntity<ArrayList> responseEntityAverageTimeTakenPerEngineer= restTemplate.exchange(
				Constants.url + "/serviceEngineer" +  "/getStats",  //+ "/" + session.getAttribute("user_name"),
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<ArrayList>() {});
		
		ArrayList averageTimeTakenPerEngineer = responseEntityAverageTimeTakenPerEngineer.getBody();
		
		/* set them in session */
		System.out.println("In public ModelAndView Average_Time_Taken_Per_Engineer(HttpSession session) { RESPONSE = " + averageTimeTakenPerEngineer);
		
		/* setting the  averageTimeTakenPerEngineer  into session*/
		session.setAttribute("Average_Time_Taken_Per_Engineer", averageTimeTakenPerEngineer);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ServiceEngineer.jsp?operation=DisplayAverageTimeTakenPerEngineer");
		
		return modelAndView;
	}
	
//	SE-Average_Time_Taken_Per_Severity
	@RequestMapping(value = "/SE-Average_Time_Taken_Per_Severity", method= RequestMethod.GET)
	public ModelAndView Average_Time_Taken_Per_Severity(HttpSession session) {
		System.out.println("public ModelAndView Average_Time_Taken_Per_Severity(HttpSession session) {");
			
		ResponseEntity<ArrayList> responseEntityAverageTimeTakenPerEngineer= restTemplate.exchange(
				Constants.url + "/serviceEngineer" +  "/getStatsOfSeverity",  //+ "/" + session.getAttribute("user_name"),
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<ArrayList>() {});
		
		ArrayList averageTimeTakenPerSeverity = responseEntityAverageTimeTakenPerEngineer.getBody();
		
		System.out.println("setting averageTimeTakenPerSeverity into session");
		session.setAttribute("Avg_Time_Taken_Per_Severity", averageTimeTakenPerSeverity);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ServiceEngineer.jsp?operation=DisplayAverageTimeTakenPerSeverity");
		
		return modelAndView;
	}
	
	//SE-Aging_of_Open_Tickets
	@RequestMapping(value = "/SE-Aging_of_Open_Tickets", method= RequestMethod.GET)
	public ModelAndView getAgingOfOpenTicket(HttpSession session) {
		
		ResponseEntity<List<Ticket>> responseEntityAgingOfOpenTicket= restTemplate.exchange(
				Constants.url + "/serviceEngineer" +  "/getAgingOfOpenTicket" + "/" + session.getAttribute("user_name"),
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Ticket>>() {});
		
		List<Ticket> agingOfOpenTickets = responseEntityAgingOfOpenTicket.getBody();
		
		System.out.println("Setting AgingOfOpenTickets into session response = " + agingOfOpenTickets);
		session.setAttribute("AgingOfOpenTickets", agingOfOpenTickets);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ServiceEngineer.jsp?operation=DisplayAgingOfOpenTickets");
		
		return modelAndView;
	}
}