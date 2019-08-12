package com.ordnaelmedeiros.jpafluidselect.querybuilder;

import java.util.Objects;

import javax.persistence.EntityManager;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;

import lombok.Getter;

public class QueryBuilder {
	
	private Integer aliasCountParam = 0;
	private Integer aliasCountTable = 0;
	
	@Getter
	private EntityManager em;
	
	public QueryBuilder(EntityManager em) {
		this.em = em;
	}
	
	/*
	public <T> AttributePath<T> path(Attribute<?, T> attribute) {
		return new AttributePath<T>(attribute);
	}
	*/
	//// novoooooooooooo

	public <Table> Select<Table> select(Class<Table> klass) {
		return new Select<>(klass, this);
	}
	
	public String createTableAlias(String ref) {
		
		Objects.requireNonNull(ref);
		ref = ref.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
		
		return ref+(this.aliasCountTable++)+"_";
		
	}
	
	public String createParamAlias() {
		return "param"+(this.aliasCountParam++);
	}
	
}
