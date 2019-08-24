package com.ordnaelmedeiros.jpafluidselect.querybuilder.models;

import java.time.LocalDate;
import java.time.Month;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
//@Builder @NoArgsConstructor @AllArgsConstructor
public class ObjDate {

	@Id
	private Integer id;
	
	@Column
	private LocalDate date;
	
	public ObjDate() {
	}
	
	public ObjDate(Integer id) {
		this.id = id;
	}
	
	public ObjDate(Integer id, LocalDate date) {
		this.id = id;
		this.date = date;
	}
	public ObjDate(Integer id, int year, Month month, int dayOfMonth) {
		this.id = id;
		this.date = LocalDate.of(year, month, dayOfMonth);
	}
	/*
	public ObjDate date(LocalDate date) {
		this.date = date;
		return this;
	}
	
	public ObjDate date(int year, Month month, int dayOfMonth) {
		this.date = LocalDate.of(year, month, dayOfMonth);
		return this;
	}
	*/
}
