package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.parameters;

import javax.persistence.Query;

public class Parameter {
	
	private String name;
	private Object value;
	
	public Parameter(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public void setParameter(Query query) {
		query.setParameter(name, value);
	}
	
}
