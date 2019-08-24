package com.ordnaelmedeiros.jpafluidselect.querybuilder.models;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
//@Builder @NoArgsConstructor @AllArgsConstructor
public class ObjTime {

	@Id
	private Integer id;
	
	@Column
	private LocalTime now;
	
	public ObjTime() {
	}
	
	public ObjTime(Integer id) {
		this.id = id;
	}
	
	public ObjTime(Integer id, LocalTime now) {
		this.id = id;
		this.now = now;
	}
	public ObjTime(Integer id, int hour, int min, int sec) {
		this.id = id;
		this.now = LocalTime.of(hour, min, sec);
	}
	
}
