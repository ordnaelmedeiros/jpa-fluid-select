package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.Attribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.defaults.SelectAll;

import lombok.Getter;

public class OperationGroup<Back, T> implements SelectAll<T> {

	private Back back;
	
	@Getter
	protected Select<T> select;
	
	protected QueryBuilder queryBuilder;
	
	@Getter
	private List<Operation> operations = new ArrayList<>();

	public void add(Operation op) {
		this.operations.add(op);
	}

	public FieldOperation<Back, OperationGroup<Back, T>, T, Object> field(String field) {
		return new FieldOperation<>(this, queryBuilder, this, field);
	}
	
	public <ValueType> FieldOperation<Back, OperationGroup<Back, T>, T, ValueType> field(Attribute<T, ValueType> field) {
		return new FieldOperation<>(this, queryBuilder, this, field.getName());
	}
	
	public OperationGroup(Back back, Select<T> select, QueryBuilder queryBuilder) {
		this.back = back;
		this.select = select;
		this.queryBuilder = queryBuilder;
	}

	public OperationGroup<OperationGroup<Back, T>, T> orGroup() {
		return new OperationGroup<>(this, select, queryBuilder);
	}
	
	public Back end() {
		return this.back;
	}
	
}
