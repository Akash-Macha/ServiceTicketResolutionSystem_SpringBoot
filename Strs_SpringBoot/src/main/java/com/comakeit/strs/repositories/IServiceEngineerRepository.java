package com.comakeit.strs.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comakeit.strs.entites.ServiceEngineer;
import com.comakeit.strs.entites.User;

public interface IServiceEngineerRepository extends JpaRepository<ServiceEngineer, Integer>{

	@Query("SELECT serviceEngineer "
			+ "FROM ServiceEngineer serviceEngineer "
			+ "WHERE "
			+ "(serviceEngineer.current_high_priority_ticket IS NULL and "
			+ "serviceEngineer.department.code = :departmentCode) ")
	public List<ServiceEngineer> findUnAssignedEmployee(@Param("departmentCode") String departmentCode);
	
	@Query(""
			+ "SELECT serviceEngineer "
			+ "FROM ServiceEngineer serviceEngineer "
			+ "WHERE 	serviceEngineer.department.code = :department and "
			+ "			serviceEngineer.priority.code = :priorty_code and "
			+ "			serviceEngineer.current_high_priority_ticket.status.code = 'ON_GO'")
	public List<ServiceEngineer> findLowPriorityTicketServiceEngineers(@Param("department")String department, @Param("priorty_code") String priorty_code);
	
	
	@Query("SELECT serviceEngineer "
			+ "FROM ServiceEngineer serviceEngineer "
			+ "WHERE 	serviceEngineer.department.code = :department and "
			+ "			serviceEngineer.priority.code = :priorty_code and "
			+ "			serviceEngineer.current_high_priority_ticket.status.code = 'ON_GO'")
	public List<ServiceEngineer> getMediumPriorityTicketServiceEngineers(@Param("department") String department, @Param("priorty_code") String priorty_code);
	
	@Query("SELECT serviceEngineer "
			+ "FROM ServiceEngineer serviceEngineer "
			+ "WHERE serviceEngineer.department.code = :department "
			+ "ORDER BY serviceEngineer.total_tickets_worked_on , serviceEngineer.current_high_priority_ticket.id DESC ")
	public List<ServiceEngineer> getEmployeeWithLessWorkedTicket(@Param("department") String department);

	@Query("SELECT serviceEngineer "
			+ "FROM ServiceEngineer serviceEngineer "
			+ "WHERE serviceEngineer.user.id = :user_id")
	public ServiceEngineer getServiceEngineerByUserId(@Param("user_id") Integer user_id);


	@Query("SELECT serviceEngineer.user "
			+ "FROM ServiceEngineer serviceEngineer ")
	public List<User> getAllUserServiceEngineers();

	@Query("SELECT AVG(ticket.closed_date - ticket.start_date) "
			+ "FROM Ticket ticket "
			+ "WHERE ticket.assigned_to.id = :service_engineer_id and"
			+ "		ticket.status.code = 'CLSD'")
	public Double getStatsPerEngineer(@Param("service_engineer_id") Integer service_engineer_id);

	@Query( "SELECT AVG(ticket.closed_date - ticket.start_date) "
			+ "FROM Ticket ticket "
			+ "WHERE ticket.priority.id = :priority_id and"
			+ "		ticket.status.code = 'CLSD'")
	public Double getStatsPerSeverity(@Param("priority_id") Integer priority_id);
	
	
}
