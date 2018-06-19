package com.github.ordnaelmedeiros.jpafluidselect.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class People {

	public static Long countId = 0l;
	
	@Id
	private Long id;
	
	@Column(length=200)
	private String name;
	
	@ManyToOne
	private Address address;
	
	@OneToMany(mappedBy="people")
	private List<Phone> phones;

	public People() {
		this.setId(++countId);
	}
	public People(String name) {
		this();
		this.name = name;
	}
	
	public People(String name, Address address) {
		this();
		this.name = name;
		this.address = address;
	}
	
}
