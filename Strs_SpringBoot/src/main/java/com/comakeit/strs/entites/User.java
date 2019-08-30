package com.comakeit.strs.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Entity
public class User implements Serializable {

	private static final long serialVersionUID = -7703488403050501998L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull //we can have duplicate names ! but not duplicate user_names
	private String name;

	@NotNull
	@Column(unique= true)
	private String user_name;
	
	@NotNull // we can have duplicate password
	private String password;

	/* https://stackoverflow.com/a/13027444 */
	// (optional=false, targetEntity = Role.class, cascade = {CascadeType.ALL})
	@ManyToOne
	@JoinColumn(name="role_id",referencedColumnName="id")
	private Role role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", user_name=" + user_name + ", password=" + password + ", roleEntity="
				+ role + "]";
	}
}
