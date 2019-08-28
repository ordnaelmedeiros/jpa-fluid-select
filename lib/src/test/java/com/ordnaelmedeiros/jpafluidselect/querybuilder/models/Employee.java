package com.ordnaelmedeiros.jpafluidselect.querybuilder.models;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Employee {

	@Id
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private LocalDate birth;
	
	@OneToMany(mappedBy="employee", fetch=LAZY)
	private List<EmployeePhone> phones;
	
}
