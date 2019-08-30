package com.comakeit.strs.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.comakeit.strs.entites.Priority;
import com.comakeit.strs.entites.ServiceEngineer;
import com.comakeit.strs.entites.Status;
import com.comakeit.strs.entites.Ticket;
import com.comakeit.strs.entites.User;
import com.comakeit.strs.repositories.IServiceEngineerRepository;
import com.comakeit.strs.repositories.IStatusRepository;
import com.comakeit.strs.repositories.ITicketRepository;
import com.comakeit.strs.repositories.IUserRepository;

@Service
public class TicketServices {

	@Autowired
	private ITicketRepository ticketRepository;
	
	@Autowired
	private IStatusRepository statusRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IServiceEngineerRepository serviceEngineerRepository;
	
	
	public List<Ticket> getAllTickets(@RequestBody String user_name){
		System.out.println("Inside: public List<Ticket> getAllTickets(@PathVariable(value=\"user_name\") String user_name){");
			
		return  ticketRepository.getAlltickets(user_name);
	}

	public String closeTicket(String ticket_id) {
		
		Optional<Ticket> optionalTicket = ticketRepository.findById( Integer.parseInt(ticket_id) );
		
		if(optionalTicket.isPresent()) {
			Ticket ticket = optionalTicket.get();
			
			ticket.setClosed_date( LocalDate.now() );
			ticket.setStatus( (Status) statusRepository.getStatus("CLSD") );
			
			
			/* upadting serviceEngineer */
			User user= ticket.getAssigned_to();
			ServiceEngineer serviceEngineer = serviceEngineerRepository.getServiceEngineerByUserId( (Integer) user.getId() );
			
			System.out.println("\nClosed Ticket Assigned serviceEngineer = " + serviceEngineer + "\n");
			
			serviceEngineer.setCurrent_high_priority_ticket(null);
			serviceEngineer.setCurrent_ticket_start_date(null);
			serviceEngineer.setPriority(null);
			serviceEngineer.setTotal_tickets_worked_on( serviceEngineer.getTotal_tickets_worked_on() + 1 );
			
			ticketRepository.save(ticket);
			serviceEngineerRepository.save(serviceEngineer);
			
			return "true";
		}
		
		return "false";
	}


	public String checkPendingTicket(String ticket_id) {
		Optional<Ticket> optionalTicket = ticketRepository.findById( Integer.parseInt(ticket_id) );
		
		if(optionalTicket.isPresent()) {
			Ticket ticket = optionalTicket.get();
			Integer user_id = ticket.getAssigned_to().getId();
			
			List<Ticket> listOfPendingTickets = ticketRepository.getListOfPendingTickets(user_id );
			
			if(listOfPendingTickets != null && listOfPendingTickets.size() >= 1) {
				
				Ticket pendingTicket = listOfPendingTickets.get(0);
				pendingTicket.setStatus( (Status) statusRepository.getStatus("ON_GO") );
				
				/* updating ServiceEngineer Table */
				ServiceEngineer serviceEngineer = serviceEngineerRepository.getServiceEngineerByUserId(user_id);
				serviceEngineer.setCurrent_high_priority_ticket( (Ticket) pendingTicket );
				serviceEngineer.setCurrent_ticket_start_date( ticket.getStart_date() );
				serviceEngineer.setPriority( (Priority) ticket.getPriority() ); 

				ticketRepository.save(ticket);
				serviceEngineerRepository.save(serviceEngineer);
				
				return "true";
			}
		}else {
			System.out.println("\n** THERE ARE NO PENDING TICKETS ***\n");
		}
		return "false";
	}
}
