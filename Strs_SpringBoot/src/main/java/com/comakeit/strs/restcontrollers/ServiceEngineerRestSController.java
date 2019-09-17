package com.comakeit.strs.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.strs.entites.Ticket;
import com.comakeit.strs.exceptions.STRSNoContentException;
import com.comakeit.strs.services.ServiceEngineerServices;

@RestController
@RequestMapping("/serviceEngineer")
public class ServiceEngineerRestSController {

    @Autowired
    private ServiceEngineerServices serviceEngineerServices;

    @RequestMapping(value = "/updateTicketPriority")
    public String updateTicketPriority(@RequestBody ArrayList<String> updateTicketValues) {
        return serviceEngineerServices.updateTicketPriority(updateTicketValues);
    }

    @RequestMapping(value="/getStats")
    public ArrayList getAverageTimeTakenPerEngineer() {
    	ArrayList averageTimeTakenPerEngineer = serviceEngineerServices.getAverageTimeTakenPerEngineer();
    	if(averageTimeTakenPerEngineer == null) {
    		throw new STRSNoContentException("No Data Found : \"Average Time Taken Per Engineer\"");
    	}
        return  averageTimeTakenPerEngineer;
    }
    
    @RequestMapping(value="/getStatsOfSeverity")
    public ArrayList getAverageTimeTakenPerServerity() {
    	ArrayList averageTimeTakenPerServerity = serviceEngineerServices.getAverageTimeTakenPerServerity();
    	if(averageTimeTakenPerServerity == null) {
    		throw new STRSNoContentException("No Data Found : \"Average Time Taken Per Serverity\"");
    	}
        return  averageTimeTakenPerServerity;
    }
    
    @RequestMapping(value="/getAgingOfOpenTicket/{user_name}")
    public List<Ticket> getAgingOfOpenTicket(@PathVariable(value="user_name") String user_name) {
        return  serviceEngineerServices.getAgingOfOpenTicket(user_name);
    }
}
