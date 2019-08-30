package com.comakeit.strs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.comakeit.strs.entites.Department;
import com.comakeit.strs.entites.Priority;
import com.comakeit.strs.entites.Role;
import com.comakeit.strs.entites.ServiceEngineer;
import com.comakeit.strs.entites.Status;
import com.comakeit.strs.entites.Ticket;
import com.comakeit.strs.entites.User;
import com.comakeit.strs.repositories.IServiceEngineerRepository;
import com.comakeit.strs.repositories.IStatusRepository;
import com.comakeit.strs.repositories.ITicketRepository;
import com.comakeit.strs.repositories.IUserRepository;

@Service
public class UserServices{
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ITicketRepository ticketRepository;
	
	@Autowired
	private IServiceEngineerRepository serviceEngineerRepository; 
	
	@Autowired
	private IStatusRepository statusRepository;
	
	
	public Role validate(@RequestBody User user) {
		
		System.out.println("\n\nIn IUserRepository\n\n");
		
		return userRepository.validate(user.getUser_name(), user.getPassword());
	}
	
	public List<Department> getListOfDepartment(){
		return userRepository.getListOfDepartment();
	}
	
	public List<Priority> getListOfPriority(){
		return userRepository.getListOfPriority();
	}
	
	public List<User> getListOfUsers(){
		return userRepository.findAll();
	}
	
	public String insertTicket(@RequestBody Ticket ticket) {
		System.out.println("\nTicket in UserServices --> " + ticket + "\n");

		System.out.println("public String insertTicket(@RequestBody Ticket ticket){");
		if(checkAndAssignTicketForUnAssignedServiceEmployee(ticket)) {
			
			System.out.println("In: checkAndAssignTicketForUnAssignedServiceEmployee");
			System.out.println("\nFound a Free employee and assigned to him/her");
			return "Inserted";
		}
		
		if(ticket.getPriority().getCode().equals("HIG")) {
			System.out.println("if(ticket.getPriority().getCode().equals(\"HIG\")) {");
			
			if(checkForLowPriorityTicketServiceEmployee(ticket)) {
				System.out.println("if(checkForLowPriorityTicketServiceEmployee(ticket)) {");
				
				return "Inserted";
			}else if(checkForMediumPriorityTicketServiceEmployee(ticket)) {
				System.out.println("}else if(checkForMediumPriorityTicketServiceEmployee(ticket)) {");
				
				return "Inserted";
			}else if(checkForServiceEngineerWithLessWorkedTicketsAndAssignPutInPending(ticket)) {		
				System.out.println("}else if(checkForServiceEngineerWithLessWorkedTicketsAndAssignPutInPending(formData)) {");
				
				return "Inserted";
			}
		}
		else if(ticket.getPriority().getCode().equals("MED")) {
			if(checkForLowPriorityTicketServiceEmployee(ticket)) {
				
				return "Inserted";
			}else if(checkForServiceEngineerWithLessWorkedTicketsAndAssignPutInPending(ticket)) {
				
				return "Inserted";
			}
		}else if(checkForServiceEngineerWithLessWorkedTicketsAndAssignPutInPending(ticket)) {
			
			return "Inserted";
		}
		System.out.println("HITTT IN USER SERVICES");
		System.out.println("\n\n In UserServices ticket = " + ticket + "\n\n");
		
		return "NotInserted";
	}

	private boolean checkForServiceEngineerWithLessWorkedTicketsAndAssignPutInPending(Ticket ticket) {
//		Query getEmployeeWithLessWorkedTicket = entityManager.createQuery(""
//				+ "SELECT serviceEngineer "
//				+ "FROM ServiceEngineer serviceEngineer "
//				+ "WHERE serviceEngineer.department.code = :department "
//				+ "ORDER BY serviceEngineer.total_tickets_worked_on , serviceEngineer.current_high_priority_ticket.id ");
//		getEmployeeWithLessWorkedTicket.setParameter("department", formData.get(1));
		
		List<ServiceEngineer> listOfServiceEngineers = serviceEngineerRepository.getEmployeeWithLessWorkedTicket(
				ticket.getCategory().getCode() );
		
		System.out.println("List<ServiceEngineer> listOfServiceEngineers IN **checkForServiceEngineerWithLessWorkedTicketsAndAssignPutInPending** "
				+ listOfServiceEngineers);
		
		if(listOfServiceEngineers != null && listOfServiceEngineers.size() >= 1) {
			System.out.println("Inside : getEmployeeWithLessWorkedTicket if(listOfServiceEngineers != null && listOfServiceEngineers.size() >= 1) {");
			
			ServiceEngineer serviceEngineer = listOfServiceEngineers.get(0);
		
			
			ticket.setAssigned_to( (User) serviceEngineer.getUser() );
			ticket.setStatus( statusRepository.getStatus("PEND") );
			
			ticketRepository.save(ticket);
			
			return true;
		}
		return false;
	}

	private boolean checkForMediumPriorityTicketServiceEmployee(Ticket ticket) {

		try {
			List<ServiceEngineer> serviceEmployeeRecords = serviceEngineerRepository.getMediumPriorityTicketServiceEngineers(
					ticket.getCategory().getCode(), 
					ticket.getPriority().getCode());
			
			System.out.println("\nList<ServiceEngineer> serviceEmployeeRecords IN checkForMediumPriorityTicketServiceEmployee => " +
					serviceEmployeeRecords + "\n");
			
			if(serviceEmployeeRecords != null && serviceEmployeeRecords.size() >= 1) {
				System.out.println("Got a ServiceEmployee who is working on a MEDIUM priority Ticket ");
				ServiceEngineer mediumPriorityTicketServiceEngineer = serviceEmployeeRecords.get(0);

				Status pendingStatus = statusRepository.getStatus("PEND");
				mediumPriorityTicketServiceEngineer.getCurrent_high_priority_ticket().setStatus( pendingStatus );
				mediumPriorityTicketServiceEngineer.setCurrent_ticket_start_date(  ticket.getStart_date() );
				mediumPriorityTicketServiceEngineer.setPriority( (Priority) ticket.getPriority() );
				
				/*updating Ticket table */
				ticket.setAssigned_to( (User) mediumPriorityTicketServiceEngineer.getUser());
				
				Status statusOngoing = statusRepository.getStatus("ON_GO"); 
				ticket.setStatus( statusOngoing );
				
				/* setting this newly generated ticket to this ServiceEmployee */
				mediumPriorityTicketServiceEngineer.setCurrent_high_priority_ticket(ticket);
				
				ticketRepository.save(ticket);
				serviceEngineerRepository.save(mediumPriorityTicketServiceEngineer);
				
				System.out.println( "Current " + ticket.getPriority().getValue() + " Priroyt Ticket has been assigned to "
						+  ticket.getAssigned_to().getName() +"");
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}

	private boolean checkForLowPriorityTicketServiceEmployee(Ticket ticket) {
		List<ServiceEngineer> serviceEmployeeRecords = serviceEngineerRepository.findLowPriorityTicketServiceEngineers(
				ticket.getCategory().getCode(),
				"LOW");
		
		if(serviceEmployeeRecords != null && serviceEmployeeRecords.size() >= 1) {
			
			ServiceEngineer lowPriorytTicketServiceEngineer = serviceEmployeeRecords.get(0);
			
			/*updating ServiceEngineer table */
			Status pendingStatus = statusRepository.getStatus("PEND");
			lowPriorytTicketServiceEngineer.getCurrent_high_priority_ticket().setStatus( pendingStatus );			
			lowPriorytTicketServiceEngineer.setCurrent_ticket_start_date( ticket.getStart_date() );
			
			/* inserting Priority */
			lowPriorytTicketServiceEngineer.setPriority( (Priority) ticket.getPriority() );

			/*updating Ticket table */
			ticket.setAssigned_to( (User) lowPriorytTicketServiceEngineer.getUser());

			Status statusOngoing = statusRepository.getStatus("ON_GO"); 
			ticket.setStatus( statusOngoing );
			
			/* setting this newly generated ticket to this ServiceEmployee */
			lowPriorytTicketServiceEngineer.setCurrent_high_priority_ticket(ticket);
			
			ticketRepository.save(ticket);
			serviceEngineerRepository.save(lowPriorytTicketServiceEngineer);
			
			System.out.println("\n\n In private boolean checkForLowPriorityTicketServiceEmployee(Ticket ticket) {\n"
					+ "ticket = " + ticket + "\n" 
					+ "SE = " + lowPriorytTicketServiceEngineer);
			
			return true;
		}
		
		return false;
	}

	private boolean checkAndAssignTicketForUnAssignedServiceEmployee(Ticket ticket) {
		System.out.println("Inside = private boolean checkAndAssignTicketForUnAssignedServiceEmployee(Ticket ticket) {");
		
		List<ServiceEngineer> serviceEmployeeRecords = serviceEngineerRepository.findUnAssignedEmployee(ticket.getCategory().getCode());
		
		System.out.println("--> RESPONSE = " + serviceEmployeeRecords);
		
		if(serviceEmployeeRecords != null && serviceEmployeeRecords.size() >= 1) {
			System.out.println("Inside : if(serviceEmployeeRecords != null && serviceEmployeeRecords.size() >= 1) {");
			
			/* updating ticket table */
			ticket.setAssigned_to( serviceEmployeeRecords.get(0).getUser() );
			ticket.setStatus( (Status) userRepository.getStatus("ON_GO") );
			
			ticketRepository.save(ticket);
			
			/* updating ServiceEngineer Table */
			ServiceEngineer serviceEngineer = serviceEmployeeRecords.get(0);
			serviceEngineer.setCurrent_high_priority_ticket(ticket);
			serviceEngineer.setCurrent_ticket_start_date(ticket.getStart_date());
			serviceEngineer.setPriority( (Priority) ticket.getPriority());
			
			serviceEngineerRepository.save(serviceEngineer);
			return true;
		}
		
		return false;
	}
}
