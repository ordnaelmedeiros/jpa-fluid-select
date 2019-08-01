package com.ordnaelmedeiros.jpafluidselect.querybuilder;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;

import lombok.Getter;

public class QueryBuilder {
	
	private Integer countParameter = 0;
	private Integer countAsTable = 0;
	
	@Getter
	private EntityManager em;
	
	public QueryBuilder(EntityManager em) {
		this.em = em;
	}
	
	public <T> Select<T> select(Class<T> klass) {
		return new Select<T>(this, klass);
	}
	
	public String createAsTable(String table) {
		this.countAsTable ++;
		return table + "_" + this.countAsTable;
	}
	
	public String createParameter(String param) {
		this.countParameter ++;
		return param.replace(".", "_") + "_" + this.countParameter;
	}
	
	public <T> AttributePath<T> path(Attribute<?, T> attribute) {
		return new AttributePath<T>(attribute);
	}
	
}
