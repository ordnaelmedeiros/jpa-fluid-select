package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import javax.persistence.metamodel.Attribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;

import lombok.Getter;
import lombok.Setter;

public class FieldOperation<OperationBack, Back, T, ValueType> {

	private Back back;
	
	private OperationGroup<OperationBack, T> where;
	private String field;
	private QueryBuilder queryBuilder;
	private String cast = null;
	private String extract = null;
	
	@Getter @Setter
	private boolean not = false;
	private boolean ignore;
	
	public FieldOperation(Back back, QueryBuilder queryBuilder, OperationGroup<OperationBack, T> where, String field) {
		this.back = back;
		this.queryBuilder = queryBuilder;
		this.where = where;
		this.field = field;
	}
	
	private void add(Operation op) {
		if (!ignore) {
			this.where.add(op);
		}
	}
	
	public OperationGroup<OperationBack, T> eq(ValueType value) {
		this.add(Operation.equal(queryBuilder, field, value).cast(cast).extract(extract).not(not));
		return this.where;
	}
	
	public OperationGroup<OperationBack, T> gt(ValueType value) {
		this.add(Operation.greaterThan(queryBuilder, field, value).cast(cast).extract(extract));
		return this.where;
	}
	
	public OperationGroup<OperationBack, T> lt(ValueType value) {
		this.add(Operation.lessThan(queryBuilder, field, value).cast(cast).extract(extract));
		return this.where;
	}
	
	public <A> FieldOperation<OperationBack, Back, T, A> cast(Class<A> cast) {
		FieldOperation<OperationBack, Back, T, A> fieldOperation = new FieldOperation<>(this.back, queryBuilder, this.where, field);
		fieldOperation.cast = cast.getName();
		fieldOperation.ignore = this.ignore;
		return fieldOperation;
	}
	
	public <A> FieldOperation<OperationBack, Back, T, A> extract(String extract, Class<A> klass) {
		FieldOperation<OperationBack, Back, T, A> fieldOperation = new FieldOperation<>(this.back, queryBuilder, this.where, field);
		fieldOperation.extract = extract;
		fieldOperation.ignore = this.ignore;
		return fieldOperation;
	}
	
	public FieldOperation<OperationBack, Back, T, Object> to(String field) {
		
		String path = this.field + "." + field;
		FieldOperation<OperationBack, Back, T, Object> fieldOperation = new FieldOperation<>(this.back, queryBuilder, this.where, path);
		fieldOperation.ignore = this.ignore;
		return fieldOperation;
		
	}
	
	public <A> FieldOperation<OperationBack, Back, T, A> to(Attribute<ValueType, A> att) {
		String path = this.field + "." + att.getName();
		FieldOperation<OperationBack, Back, T, A> fieldOperation = new FieldOperation<>(this.back, queryBuilder, this.where, path);
		fieldOperation.ignore = this.ignore;
		return fieldOperation;
	}
	
	public FieldOperation<OperationBack, Back, T,ValueType> not() {
		this.not = true;
		return this;
	}

	public FieldOperation<OperationBack, Back, T, ValueType> ignoreIf(boolean ignore) {
		this.ignore = ignore;
		return this;
	}
	
	public Back end() {
		return this.back;
	}
	
}
