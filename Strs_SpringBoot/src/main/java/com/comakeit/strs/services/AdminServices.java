package com.comakeit.strs.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.comakeit.strs.entites.Department;
import com.comakeit.strs.entites.Role;
import com.comakeit.strs.entites.ServiceEngineer;
import com.comakeit.strs.entites.User;
import com.comakeit.strs.repositories.IAdminRepository;
import com.comakeit.strs.repositories.IDepartmentRepository;
import com.comakeit.strs.repositories.IRoleRepository;
import com.comakeit.strs.repositories.IServiceEngineerRepository;
import com.comakeit.strs.repositories.IUserRepository;

@Service
public class AdminServices {
	
	@Autowired
	private IAdminRepository adminRepository;
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IDepartmentRepository departmentRepository;
	
	@Autowired
	private IServiceEngineerRepository serviceEngineerRepository;
	
	public List<User> getAllUses() {

		return adminRepository.getAllUses();
	}

	public String addUser(@RequestBody User user) {
		Role role = roleRepository.getRoleByCode("END_U");
		
		user.setRole( role );
		System.out.println("\nUser obj in addUser = " + user + "\n");
		
		System.out.println("in user user.setRole( roleRepository.getRoleByCode(\"END_U\") ); = " + user);
		
		try {
			userRepository.save(user);
			
			return "added";
		}catch(Exception e) {
			e.printStackTrace();
			return "notAdded";
		}
	}

	public String addServiceEngineer(@RequestBody ServiceEngineer serviceEngineer) {
		try {
			/* save SE */
			serviceEngineerRepository.save(serviceEngineer);
			
			return "added";
		}catch(Exception e) {
			e.printStackTrace();
			return "notAdded";
		}
	}

	public User addUserServiceEngineer(User user) {
		Role role = roleRepository.getRoleByCode("SER_ENGG");
		
		user.setRole( role );
		System.out.println("\nUser obj in addUser = " + user + "\n");
		
		System.out.println("in user user.setRole( roleRepository.getRoleByCode(\"END_U\") ); = " + user);
		
		try {
			userRepository.save(user);
			
			return user;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String addNewDepartment(HashMap<String, String> newDepartmentDetails) {
		System.out.println("public String addNewDepartment(HashMap<String, String> newDepartmentDetails = " + newDepartmentDetails);
		try {
			Department department = new Department();
			department.setName( newDepartmentDetails.get("newDepartmentName") );
			department.setCode( newDepartmentDetails.get("newDepartmentCode") );
			
			departmentRepository.save(department);
		}catch(Exception e) {
			e.printStackTrace();
			return "notAdded";
		}
		return "added";
	}
}
