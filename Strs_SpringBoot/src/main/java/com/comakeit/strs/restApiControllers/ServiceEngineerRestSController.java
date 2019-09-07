package com.comakeit.strs.restApiControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.strs.entites.Ticket;
import com.comakeit.strs.services.ServiceEngineerServices;

@RestController
@RequestMapping("/serviceEngineer")
public class ServiceEngineerRestSController {

    @Autowired
    private ServiceEngineerServices serviceEngineerServices;

    @RequestMapping(value = "/updateTicketPriority")
    public String updateTicketPriority(@RequestBody ArrayList<String> updateTicketValues) {
        String status = serviceEngineerServices.updateTicketPriority(updateTicketValues);

        return status;
    }

    @RequestMapping(value="/getStats")
    public ArrayList getAverageTimeTakenPerEngineer() {
        return  serviceEngineerServices.getAverageTimeTakenPerEngineer();
    }
    
    @RequestMapping(value="/getStatsOfSeverity")
    public ArrayList getAverageTimeTakenPerServerity() {
        return  serviceEngineerServices.getAverageTimeTakenPerServerity();
    }
    
    @RequestMapping(value="/getAgingOfOpenTicket/{user_name}")
    public List<Ticket> getAgingOfOpenTicket(@PathVariable(value="user_name") String user_name) {
        return  serviceEngineerServices.getAgingOfOpenTicket(user_name);
    }
}
