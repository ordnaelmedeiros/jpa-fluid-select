package com.github.ordnaelmedeiros.jpafluidselect.models;

import static javax.persistence.FetchType.LAZY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	
	@OneToMany(mappedBy="country", fetch=LAZY)
	private List<Address> address;
	
	public Country() {
		this.setId(++countId);
	}
	
	public Country(String name) {
		this();
		this.name = name;
	}
	
	public Country(Long id) {
		this.id = id;
	}
	
}
