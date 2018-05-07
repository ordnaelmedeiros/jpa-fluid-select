package com.github.ordnaelmedeiros.jpafluidselect.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@Entity
@SequenceGenerator(name="seq_adress", sequenceName="seq_adress", allocationSize=1)
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_adress")
	private Long id;
	
	@Column(length=200)
	private String street;
	
	public Address() {
	}
	
	public Address(String street) {
		this.street = street;
	}
	
}
