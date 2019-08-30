package com.comakeit.strs.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Priority implements Serializable {

	private static final long serialVersionUID = 283077206643263033L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique= true, length= 50)
    private String value;       // LOW, MEDIUM, HIGH

    @NotNull
    @Column(unique= true, length= 10)
    private String code;        // LOW, MED, HIG

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
		return "Priority [id=" + id + ", value=" + value + ", code=" + code + "]";
	}
}