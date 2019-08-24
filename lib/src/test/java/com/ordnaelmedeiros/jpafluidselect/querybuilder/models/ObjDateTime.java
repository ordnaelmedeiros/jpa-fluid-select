package com.ordnaelmedeiros.jpafluidselect.querybuilder.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
//@Builder @NoArgsConstructor @AllArgsConstructor
public class ObjDateTime {

	@Id
	private Integer id;
	
	@Column
	private LocalDateTime now;
	
	public ObjDateTime() {
	}
	
	public ObjDateTime(Integer id) {
		this.id = id;
	}
	
	public ObjDateTime(Integer id, LocalDateTime now) {
		this.id = id;
		this.now = now;
	}
	
}
