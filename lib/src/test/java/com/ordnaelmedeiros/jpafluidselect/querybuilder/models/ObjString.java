package com.ordnaelmedeiros.jpafluidselect.querybuilder.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
//@Builder @NoArgsConstructor @AllArgsConstructor
public class ObjString {

	@Id
	private Integer id;
	
	@Column
	private String text;
	
	public ObjString() {
	}
	
	public ObjString(Integer id) {
		this.id = id;
	}
	
	public ObjString(Integer id, String text) {
		this.id = id;
		this.text = text;
	}
	
}
