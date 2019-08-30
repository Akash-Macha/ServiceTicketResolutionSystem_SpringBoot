package com.comakeit.strs.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.comakeit.strs.entites.Priority;
import com.comakeit.strs.entites.ServiceEngineer;
import com.comakeit.strs.entites.Status;
import com.comakeit.strs.entites.Ticket;
import com.comakeit.strs.entites.User;
import com.comakeit.strs.repositories.IPriorityRepository;
import com.comakeit.strs.repositories.IServiceEngineerRepository;
import com.comakeit.strs.repositories.IStatusRepository;
import com.comakeit.strs.repositories.ITicketRepository;

@Service
public class ServiceEngineerServices {

	@Autowired
	private ITicketRepository ticketRepository;
	
	@Autowired
	private IServiceEngineerRepository serviceEngineerRepository;
	
	@Autowired
	private IPriorityRepository priorityRepository;
	
	@Autowired
	private IStatusRepository statusRepository; 

	public String updateTicketPriority(ArrayList<String> updateTicketValues) {
		
		Ticket ticket = ticketRepository.findTicketById( Integer.parseInt( updateTicketValues.get(0)  ) );
	
		System.out.println("\npublic String updateTicketPriority(ArrayList<String> updateTicketValues) {\n");
		System.out.println("TICKET IN " + ticket + "\n");
		
		String newPriorityValue = (String) updateTicketValues.get(1);

		Priority newPriority = priorityRepository.getPriorityByValue(newPriorityValue);
		ticket.setPriority( newPriority ); 

		ticketRepository.save(ticket);
		
		if( (ticket.getPriority().getValue().equals("High") && 
				(newPriorityValue.equals("Medium") || newPriorityValue.equals("Low")) ) ||
				
			(ticket.getPriority().getValue().equals("Medium") &&
					newPriorityValue.equals("Low")) ){
			
			List<Ticket> otherHighPriortyTickets = ticketRepository.getHighPriorityTicket(
					ticket.getCategory().getName(), 
					ticket.getAssigned_to().getName());
			
			if(otherHighPriortyTickets != null && otherHighPriortyTickets.size() >= 1) {
				System.out.println("We have other HIHG Priority Ticekt !!! ");
				
				Ticket otherHighPriortyTicket = otherHighPriortyTickets.get(0);
				
				Status statusOngoing = statusRepository.getStatus("ON_GO");
				otherHighPriortyTicket.setStatus( statusOngoing );

				Status statusPending = statusRepository.getStatus("PEND"); 
				ticket.setStatus( statusPending );
				
				/* updating ServiceEngineer table with otherHighPriorityTicket  */
				User user = ticket.getAssigned_to();

				ServiceEngineer serviceEngineer = serviceEngineerRepository.getServiceEngineerByUserId(user.getId()); //(ServiceEngineer) getServiceEngineer.getResultList().get(0);
				
				serviceEngineer.setPriority( (Priority) otherHighPriortyTicket.getPriority() );
				serviceEngineer.setCurrent_high_priority_ticket(otherHighPriortyTicket);
				serviceEngineer.setCurrent_ticket_start_date(otherHighPriortyTicket.getStart_date());

				serviceEngineerRepository.save(serviceEngineer);
				
				return "true";				
			}else {
				/* if we did not find any other ticket with the higher priorities
				 * then we may have only one ticket
				 * 
				 *  In that case we will only modify the Priority of the current ticket in Ticket (already done) and ServiceEngineer table 
				 *  he/she should be working on that particular ticket itself */
				System.out.println("ELSE BLOCK after if(otherHighPriortyTickets != null && otherHighPriortyTickets.size() >= 1) {");
			
				/* updating ServiceEngineer table with otherHighPriorityTicket  */
				User user = ticket.getAssigned_to();
				ServiceEngineer serviceEngineer = serviceEngineerRepository.getServiceEngineerByUserId(user.getId());
				
				serviceEngineer.setPriority( (Priority) ticket.getPriority() );
			
				System.out.println("CHECK");
				
				serviceEngineerRepository.save(serviceEngineer);
				
				return "true";
			}

			
		} else {
			/*
			 *  Low -> High | Low -> Medium | Medium -> High 
			 * 
			 *  Just change the Priority of the ticket and
			 *  this respective serviceEngineer
			 */
				/* updating ServiceEngineer table with otherHighPriorityTicket  */
			User user = ticket.getAssigned_to();
			ServiceEngineer serviceEngineer = serviceEngineerRepository.getServiceEngineerByUserId(user.getId());
			
			serviceEngineer.setPriority( (Priority) ticket.getPriority() );

			serviceEngineerRepository.save(serviceEngineer);
			
			return "true";
		}
	}

	public ArrayList getAverageTimeTakenPerEngineer() {//String user_name) {
		/*
		 * Get All the serviceEngineers
		 * calculate the AVG time 
		 * Put them in an ArrayList
		 */
		List<User> listOfServiceEngineer = serviceEngineerRepository.getAllUserServiceEngineers();
		
		ArrayList userNameAndStats = null;
		ArrayList statsOfServiceEngineers = new ArrayList();
		
		for(User user: listOfServiceEngineer) {
			
			userNameAndStats = new ArrayList();
			
			userNameAndStats.add(user.getName());
			
			Double value = (Double)  serviceEngineerRepository.getStatsPerEngineer(user.getId()); //.getResultList().get(0);
			if(value != null)
				userNameAndStats.add( value );
			else {
				userNameAndStats.add( new String("Not yet resolved single ticket!") );
			}
			
			statsOfServiceEngineers.add( userNameAndStats );
		}
	
		return statsOfServiceEngineers;
	}

	public ArrayList getAverageTimeTakenPerServerity() {

		List<Priority> listOfPriorities = priorityRepository.findAll();
		
		ArrayList AverageTimeTakenPerSeverity = new ArrayList();
		
		for(Priority priority : listOfPriorities ) {
//			Query getStatsPerSeverity = entityManager.createQuery(""
//					+ "SELECT AVG(ticket.closed_date - ticket.start_date) "
//					+ "FROM Ticket ticket "
//					+ "WHERE ticket.priority.id = :priority_id and"
//					+ "		ticket.status.code = 'CLSD'");
			
//			getStatsPerSeverity.setParameter("priority_id", priority.getId());
			
			ArrayList priorityAndAvg = new ArrayList();
			
			priorityAndAvg.add(priority.getValue());
			
			Double average = serviceEngineerRepository.getStatsPerSeverity( priority.getId() );
			
			System.out.println("CHECK average -> " + average);
			
			if(average != null && average == null){
				priorityAndAvg.add( "No stats to display!" );
			}
			else if(average != null)
				priorityAndAvg.add( average );
			
			AverageTimeTakenPerSeverity.add(priorityAndAvg);
		}
		System.out.println("**********************\n");
		System.out.println("AverageTimeTakenPerSeverity = " + AverageTimeTakenPerSeverity);
		System.out.println("**********************\n");
		
		return AverageTimeTakenPerSeverity;
	}

	public List<Ticket> getAgingOfOpenTicket(String user_name) {
		
		return ticketRepository.getAllOpenTickets(user_name);
	}
}