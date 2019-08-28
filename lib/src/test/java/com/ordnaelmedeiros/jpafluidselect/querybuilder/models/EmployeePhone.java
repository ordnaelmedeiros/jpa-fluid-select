package com.ordnaelmedeiros.jpafluidselect.querybuilder.models;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class EmployeePhone {

	@Id
	private Integer id;
	
	@ManyToOne(fetch=LAZY)
	private Employee employee;
	
	@Column(length=50)
	private String number;
	
}
