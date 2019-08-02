package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import javax.persistence.metamodel.Attribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;

import lombok.Getter;
import lombok.Setter;

public class FieldOperation<T, ValueType> {

	private Where<T> where;
	private String field;
	private QueryBuilder queryBuilder;
	private String cast = null;
	private String extract = null;
	
	@Getter @Setter
	private boolean not = false;
	
	public FieldOperation(QueryBuilder queryBuilder, Where<T> where, String field) {
		this.queryBuilder = queryBuilder;
		this.where = where;
		this.field = field;
	}
	
	public Where<T> eq(ValueType value) {
		this.where.add(Operation.equal(queryBuilder, field, value).cast(cast).extract(extract).not(not));
		return this.where;
	}
	
	public Where<T> gt(ValueType value) {
		this.where.add(Operation.greaterThan(queryBuilder, field, value).cast(cast).extract(extract));
		return this.where;
	}
	
	public Where<T> lt(ValueType value) {
		this.where.add(Operation.lessThan(queryBuilder, field, value).cast(cast).extract(extract));
		return this.where;
	}

	public <A> FieldOperation<T, A> cast(Class<A> cast) {
		FieldOperation<T, A> fieldOperation = new FieldOperation<T, A>(queryBuilder, this.where, field);
		fieldOperation.cast = cast.getName();
		return fieldOperation;
	}
	
	public <A> FieldOperation<T, A> extract(String extract, Class<A> klass) {
		FieldOperation<T, A> fieldOperation = new FieldOperation<T, A>(queryBuilder, this.where, field);
		fieldOperation.extract = extract;
		return fieldOperation;
	}
	
	public FieldOperation<T, Object> to(String field) {
		
		String path = this.field + "." + field;
		FieldOperation<T, Object> fieldOperation = new FieldOperation<T, Object>(queryBuilder, this.where, path);
		return fieldOperation;
		
	}
	
	public <A> FieldOperation<T, A> to(Attribute<ValueType, A> att) {
		String path = this.field + "." + att.getName();
		FieldOperation<T, A> fieldOperation = new FieldOperation<T, A>(queryBuilder, this.where, path);
		return fieldOperation;
	}

	public FieldOperation<T,ValueType> not() {
		this.not = true;
		return this;
	}
	
}
