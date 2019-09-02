package com.comakeit.strs.restApiControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.strs.entites.Status;
import com.comakeit.strs.repositories.IStatusRepository;

@RestController
@RequestMapping("/status")
public class StatusRestController {

	@Autowired
	private IStatusRepository statusRepository;
	
	@RequestMapping(value="/getAllStatuses")///{user_name}")
	public List<Status> getAverageTimeTakenPerEngineer() {// @PathVariable(value="user_name") String user_name){
		return statusRepository.findAll();
	}
}
