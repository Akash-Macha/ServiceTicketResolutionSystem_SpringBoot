package com.comakeit.strs.entites;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ServiceEngineer implements Serializable {

	private static final long serialVersionUID = -3880929539870409296L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer service_engineer_id;
	
	/*optional = false,   goto optional documentation [ ctrl + click ] */
	/* https://stackoverflow.com/a/13027444 */
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", unique= true)
	private User user;

	@OneToOne
	@JoinColumn(name = "area_of_expertise_id", referencedColumnName = "id")
	private Department department; // area_of_expertise;

	private Integer total_tickets_worked_on; // should start from 0

	private LocalDate current_ticket_start_date;
	
	@OneToOne
	@JoinColumn(name= "current_high_priority_ticket_id", referencedColumnName= "id")
	private Ticket current_high_priority_ticket;

	
	@OneToOne
	@JoinColumn(name= "priority_id", referencedColumnName= "id")
	private Priority priority;

	public Integer getService_engineer_id() {
		return service_engineer_id;
	}

	public void setService_engineer_id(Integer service_engineer_id) {
		this.service_engineer_id = service_engineer_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getTotal_tickets_worked_on() {
		return total_tickets_worked_on;
	}

	public void setTotal_tickets_worked_on(Integer total_tickets_worked_on) {
		this.total_tickets_worked_on = total_tickets_worked_on;
	}

	public LocalDate getCurrent_ticket_start_date() {
		return current_ticket_start_date;
	}

	public void setCurrent_ticket_start_date(LocalDate current_ticket_start_date) {
		this.current_ticket_start_date = current_ticket_start_date;
	}

	public Ticket getCurrent_high_priority_ticket() {
		return current_high_priority_ticket;
	}

	public void setCurrent_high_priority_ticket(Ticket current_high_priority_ticket) {
		this.current_high_priority_ticket = current_high_priority_ticket;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ServiceEngineer [service_engineer_id=" + service_engineer_id + ", user=" + user + ", department="
				+ department + ", total_tickets_worked_on=" + total_tickets_worked_on + ", current_ticket_start_date="
				+ current_ticket_start_date + ", current_high_priority_ticket=" + current_high_priority_ticket
				+ ", priority=" + priority + "]";
	}
}