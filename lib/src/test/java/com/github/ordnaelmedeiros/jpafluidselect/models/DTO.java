package com.github.ordnaelmedeiros.jpafluidselect.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DTO {
	
	private String peopleName;
	private String peopleStreet;
	
	public DTO(String peopleName, String peopleStreet) {
		this.peopleName = peopleName;
		this.peopleStreet = peopleStreet;
	}
	
}
