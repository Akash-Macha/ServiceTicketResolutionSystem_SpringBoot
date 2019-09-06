package com.comakeit.strs.restApiControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.strs.entites.Priority;
import com.comakeit.strs.services.PriorityServices;

@RestController
@RequestMapping("priority")
public class PriorityRestController {
	
	@Autowired
	private PriorityServices priorityServices;  
	
//	getAllPriorities
	@RequestMapping(value="/getAllPriorities")
	public List<Priority> getAllPriorities(){
		return priorityServices.getListOfDepartment(); 
	}
}
