package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.Attribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.defaults.SelectCount;

import lombok.Getter;

public class Where<T> implements 
		SelectCount<T> {
	
	@Getter
	private Select<T> select;
	
	@Getter
	private List<Operation> operations = new ArrayList<>();

	private QueryBuilder queryBuilder;
	
	public FieldOperation<T, Object> field(String field) {
		return new FieldOperation<T, Object>(queryBuilder, this, field);
	}
	
	public <ValueType> FieldOperation<T, ValueType> field(Attribute<T, ValueType> field) {
		return new FieldOperation<T, ValueType>(queryBuilder, this, field.getName());
	}
	
	public void add(Operation op) {
		this.operations.add(op);
	}
	
	public Where(Select<T> select, QueryBuilder queryBuilder) {
		this.select = select;
		this.queryBuilder = queryBuilder;
	}
	
	public List<T> getResultList() {
		return this.select.getResultList();
	}
	
	public Order<T> order() {
		return this.select.order();
	}
	
}
