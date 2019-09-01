package com.comakeit.strs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comakeit.strs.entites.Ticket;



public interface ITicketRepository extends JpaRepository<Ticket, Integer>{

	
	@Query(""
			+ "SELECT ticket " 
			+ "FROM Ticket ticket "
			+ "WHERE ticket.requested_by.user_name = :user_name or ticket.assigned_to.user_name = :user_name "
			+ "ORDER BY ticket.status.id, ticket.id DESC")
	public List<Ticket> getAlltickets(@Param("user_name") String user_name);

	@Query("SELECT ticket "
			+ "FROM Ticket ticket "
			+ "WHERE ticket.id = :ticket_id")
	public Ticket findTicketById(@Param("ticket_id") Integer ticket_id);
	
	
	@Query("SELECT ticket "
			+ "FROM Ticket ticket "
			+ "WHERE ticket.status.value = 'Pending' and "
			+ "		ticket.category.name = :category and "
			+ "		ticket.assigned_to.name = :currentServiceEmployee "
			+ "ORDER BY ticket.priority.id DESC ")
	public List<Ticket> getHighPriorityTicket(@Param("category") String category, @Param("currentServiceEmployee") String currentServiceEmployee);

	@Query("SELECT ticket "
			+ "FROM Ticket ticket "
			+ "WHERE ticket.assigned_to.id = :user_id and "
			+ "		 ticket.status.code = 'PEND' "
			+ "ORDER BY ticket.priority.id DESC, ticket.start_date ")
	public List<Ticket> getListOfPendingTickets(@Param("user_id") Integer user_id);

	@Query("SELECT ticket "
			+ "FROM Ticket ticket "
			+ "WHERE ticket.assigned_to.user_name = :user_name and "
			+ "			ticket.status.code = 'ON_GO' or "
			+ "			ticket.status.code = 'PEND' ")
	public List<Ticket> getAllOpenTickets(@Param("user_name") String user_name);
	
}
