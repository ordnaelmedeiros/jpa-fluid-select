package com.github.ordnaelmedeiros.jpafluidselect.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Country {

	public static Long countId = 0l;
	
	@Id
	private Long id;
	
	@Column(length=300)
	private String name;
	
	public Country() {
		this.setId(++countId);
	}
	
	public Country(String name) {
		this();
		this.name = name;
	}
	
}
