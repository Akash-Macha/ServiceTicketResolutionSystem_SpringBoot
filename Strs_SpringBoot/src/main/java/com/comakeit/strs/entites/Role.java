package com.comakeit.strs.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = 4197329505375013579L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; // 1, 2, 3

	@NotNull
	@Column(unique = true, length = 100)
	private String name; // user, service_engineer

	@NotNull
	@Column(unique = true, length = 15)
	private String code; // USR, SER_ENG

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", code=" + code + "]";
	}
}
