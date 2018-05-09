package com.github.ordnaelmedeiros.jpafluidselect.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Address {

	public static Long countId = 0l;
	
	@Id
	private Long id;
	
	@Column(length=200)
	private String street;
	
	public Address() {
		this.setId(++countId);
	}
	
	public Address(String street) {
		this();
		this.street = street;
	}
	
}
