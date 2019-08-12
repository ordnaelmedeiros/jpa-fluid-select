package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.parameters;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;

public class Parameters<Table> {

	private Select<Table> select;
	private List<Parameter> params;
	
	public Parameters(Select<Table> select) {
		this.select = select;
		this.params = new ArrayList<>();
	}
	
	public String create(Object value) {
		String name = this.select.getBuilder().createParamAlias();
		this.params.add(new Parameter(name, value));
		return name;
	}

	public void setParameters(Query query) {
		
		this.params.stream().forEach(p -> p.setParameter(query));
		
	}
	
}
