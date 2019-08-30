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
public class Ticket implements Serializable {

	private static final long serialVersionUID = 7766052558799951579L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Department category;

	@OneToOne
	@JoinColumn(name = "requested_by_id", referencedColumnName = "id")
	private User requested_by;

	@OneToOne
	@JoinColumn(name = "priority_id", referencedColumnName = "id")
	private Priority priority;

	@OneToOne
	@JoinColumn(name = "status_id", referencedColumnName = "id")
	private Status status;

	@OneToOne
	@JoinColumn(name = "assigned_to_id", referencedColumnName = "id")
	private User assigned_to;

	private LocalDate start_date;

	private LocalDate requested_end_date;

	private LocalDate closed_date;

	// let it take the default length
	private String message;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Department getCategory() {
		return category;
	}

	public void setCategory(Department category) {
		this.category = category;
	}

	public User getRequested_by() {
		return requested_by;
	}

	public void setRequested_by(User requested_by) {
		this.requested_by = requested_by;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getAssigned_to() {
		return assigned_to;
	}

	public void setAssigned_to(User assigned_to) {
		this.assigned_to = assigned_to;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getRequested_end_date() {
		return requested_end_date;
	}

	public void setRequested_end_date(LocalDate requested_end_date) {
		this.requested_end_date = requested_end_date;
	}

	public LocalDate getClosed_date() {
		return closed_date;
	}

	public void setClosed_date(LocalDate closed_date) {
		this.closed_date = closed_date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", category=" + category + ", requested_by=" + requested_by + ", priority="
				+ priority + ", status=" + status + ", assigned_to=" + assigned_to + ", start_date=" + start_date
				+ ", requested_end_date=" + requested_end_date + ", closed_date=" + closed_date + ", message=" + message
				+ "]";
	}
}