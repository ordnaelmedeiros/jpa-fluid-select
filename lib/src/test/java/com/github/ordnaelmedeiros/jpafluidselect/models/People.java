package com.github.ordnaelmedeiros.jpafluidselect.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@Entity
@SequenceGenerator(name="g", sequenceName="seq_people", allocationSize=1)
public class People {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="g")
	private Long id;
	
	@Column(length=200)
	private String name;
	
	@ManyToOne
	private Address address;
	
	@OneToMany(mappedBy="people")
	private List<Phone> phones;

	public People() {
	}
	public People(String name) {
		this.name = name;
	}
	
	public People(String name, Address address) {
		this.name = name;
		this.address = address;
	}
	
}
