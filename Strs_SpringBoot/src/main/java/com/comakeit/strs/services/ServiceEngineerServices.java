package com.comakeit.strs.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    
        String oldPriorityValue = ticket.getPriority().getValue(); 
        
        /* updating ticket with newPriority! */
        String newPriorityValue = (String) updateTicketValues.get(1);
        Priority newPriority = priorityRepository.getPriorityByValue(newPriorityValue);
        ticket.setPriority( newPriority ); 

        ticketRepository.save(ticket);

        if( (oldPriorityValue.equals("High") && 
                (newPriorityValue.equals("Medium") || newPriorityValue.equals("Low")) ) ||
            (oldPriorityValue.equals("Medium") &&
                    ( newPriorityValue.equals("Low") )) ){
            
            List<Ticket> otherHighPriortyTickets = ticketRepository.otherHighPriortyTickets(
                    ticket.getCategory().getName(), 
                    ticket.getAssigned_to().getName());
            
            if(otherHighPriortyTickets != null && otherHighPriortyTickets.size() >= 1) {
                
                Ticket otherHighPriortyTicket = otherHighPriortyTickets.get(0);
                
                Status statusOngoing = statusRepository.getStatus("ON_GO");
                otherHighPriortyTicket.setStatus( statusOngoing );

                Status statusPending = statusRepository.getStatus("PEND"); 
                ticket.setStatus( statusPending );
                
                /* updating ServiceEngineer table with otherHighPriorityTicket  */
                User user = ticket.getAssigned_to();
                ServiceEngineer serviceEngineer = serviceEngineerRepository.getServiceEngineerByUserId(user.getId());
                
                serviceEngineer.setPriority( (Priority) otherHighPriortyTicket.getPriority() );
                serviceEngineer.setCurrent_high_priority_ticket(otherHighPriortyTicket);
                serviceEngineer.setCurrent_ticket_start_date(otherHighPriortyTicket.getStart_date());

                ticketRepository.save(ticket); /* updating old ticket */
                ticketRepository.save(otherHighPriortyTicket); /* updating otherHighPriorityTicket ! */
                serviceEngineerRepository.save(serviceEngineer); /* updating serviceEngineer */
                
                return "true";              
            }else {
                /* if we did not find any other ticket with the higher priorities
                 * then we may have only one ticket
                 * 
                 *  In that case we will only modify the Priority of the current ticket in Ticket (already done) and ServiceEngineer table 
                 *  he/she should be working on that particular ticket itself */
            
                /* updating ServiceEngineer table with otherHighPriorityTicket  */
            	
                User user = ticket.getAssigned_to();
                ServiceEngineer serviceEngineer = serviceEngineerRepository.getServiceEngineerByUserId(user.getId());
                
                serviceEngineer.setPriority( (Priority) ticket.getPriority() );
                
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getAverageTimeTakenPerEngineer() {
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
            
            Double value = (Double)  serviceEngineerRepository.getStatsPerEngineer(user.getId());
            if(value != null)
                userNameAndStats.add( value );
            else {
                userNameAndStats.add( new String("Not yet resolved single ticket!") );
            }
            
            statsOfServiceEngineers.add( userNameAndStats );
        }
    
        return statsOfServiceEngineers;
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public ArrayList getAverageTimeTakenPerServerity() {

        List<Priority> listOfPriorities = priorityRepository.findAll();
        
        ArrayList AverageTimeTakenPerSeverity = new ArrayList();
        
        for(Priority priority : listOfPriorities ) {
        
            ArrayList priorityAndAvg = new ArrayList();
            
            priorityAndAvg.add(priority.getValue());
            
            Double average = serviceEngineerRepository.getStatsPerSeverity( priority.getId() );
            
            
            if(average != null && average == null){
                priorityAndAvg.add( "No stats to display!" );
            }
            else if(average != null)
                priorityAndAvg.add( average );
            
            AverageTimeTakenPerSeverity.add(priorityAndAvg);
        }
        
        return AverageTimeTakenPerSeverity;
    }

    public List<Ticket> getAgingOfOpenTicket(String user_name) {
        return ticketRepository.getAllOpenTickets(user_name);
    }
}