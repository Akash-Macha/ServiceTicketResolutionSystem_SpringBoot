package com.comakeit.strs.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Status implements Serializable {

	private static final long serialVersionUID = -1883571095507461094L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique= true, length= 100)
    private String value;       // ON_GOING, PENDING, CLOSED  | On Going On going | Pending | Closed

    @NotNull
    @Column(unique= true, length= 15)
    private String code;        // ON_GO, PEND, CLSD

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
		return "Status [id=" + id + ", value=" + value + ", code=" + code + "]";
	}
}