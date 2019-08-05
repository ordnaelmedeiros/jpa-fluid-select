package com.github.ordnaelmedeiros.jpafluidselect.models;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Address {

	public static Long countId = 0l;
	
	@Id
	private Long id;
	
	@Column(length=200)
	private String street;
	
	@ManyToOne(fetch=LAZY)
	private Country country;
	
	public Address() {
		this.setId(++countId);
	}
	
	public Address(Country country, String street) {
		this();
		this.country = country;
		this.street = street;
	}
	
}
