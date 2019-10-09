package com.comakeit.strs.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.strs.entites.Ticket;
import com.comakeit.strs.services.ServiceEngineerServices;

@RestController
@RequestMapping("/serviceEngineer")
public class ServiceEngineerRestController {

    @Autowired
    private ServiceEngineerServices serviceEngineerServices;

    /**
     * 
     * @param updateTicketValues: [ TicketId: String, newPriorityValue: String ]
     * @return "true" if successfully updated, else "false"
     */
    @RequestMapping(value = "/updateTicketPriority",
    		method=RequestMethod.PUT)

    public ResponseEntity<?> updateTicketPriority(@RequestBody ArrayList<String> updateTicketValues) {
        
    	String response = serviceEngineerServices.updateTicketPriority(updateTicketValues); 
    	
    	if(response.equals("true"))
    		return ResponseEntity.ok().build();

    	// the below line, should not be executed!
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @SuppressWarnings("rawtypes")
	@RequestMapping(value="/getStatsOfEngineers")
    public ArrayList getAverageTimeTakenPerEngineers() {
        return  serviceEngineerServices.getAverageTimeTakenPerEngineers();
    }
    
    @SuppressWarnings("rawtypes")
	@RequestMapping(value="/getStatsOfSeverity")
    public ArrayList getAverageTimeTakenPerServerity() {
        return  serviceEngineerServices.getAverageTimeTakenPerServerity();
    }
    
    @RequestMapping(value="/getAgingOfOpenTicket/{user_name}")
    public List<Ticket> getAgingOfOpenTicket(@PathVariable(value="user_name") String user_name) {
        return  serviceEngineerServices.getAgingOfOpenTicket(user_name);
    }
}
