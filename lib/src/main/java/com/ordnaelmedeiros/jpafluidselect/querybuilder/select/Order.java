package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.Attribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.defaults.SelectCount;

import lombok.Getter;

public class Order<T> implements 
	SelectCount<T> {

	@Getter
	private List<FieldOrder<T, ?>> orders = new ArrayList<>();
	
	@Getter
	private Select<T> select;
	
	private QueryBuilder queryBuilder;

	public Order(Select<T> select, QueryBuilder queryBuilder) {
		this.select = select;
		this.queryBuilder = queryBuilder;
	}

	public FieldOrder<T, Object> field(String field) {
		return new FieldOrder<T, Object>(this, field);
	}
	
	public <Y> FieldOrder<T, Y> field(Attribute<T, Y> att) {
		return new FieldOrder<T, Y>(this, att.getName());
	}
	
	public List<T> getResultList() {
		return this.select.getResultList();
	}
	
}
