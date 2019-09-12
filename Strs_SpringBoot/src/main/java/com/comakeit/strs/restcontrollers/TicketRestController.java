package com.comakeit.strs.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.strs.entites.Ticket;
import com.comakeit.strs.services.TicketServices;


@RestController
@RequestMapping("ticket")
public class TicketRestController {
    
    @Autowired
    private TicketServices ticketServices;
    
    @RequestMapping(value="/getAll/{user_name}")
    public List<Ticket> getAllTickets(@PathVariable(value="user_name") String user_name){
        return  ticketServices.getAllTickets(user_name);
    }
    
    @RequestMapping(value="/close/{ticket_id}")
    public String closeTicket(@PathVariable(value="ticket_id") String ticket_id){
        return  ticketServices.closeTicket(ticket_id);
    }
    
    @RequestMapping(value="/otherPendingTickets/{ticket_id}")
    public String checkPendingTicket(@PathVariable(value="ticket_id") String ticket_id){
        return  ticketServices.checkPendingTicket(ticket_id);
    }
    
}