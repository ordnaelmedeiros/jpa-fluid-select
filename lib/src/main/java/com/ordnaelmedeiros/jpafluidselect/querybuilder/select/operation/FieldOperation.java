package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;

import lombok.Getter;
import lombok.Setter;

public class FieldOperation<ObjBack, SelectTable> implements ToSql {

	private String field;
	
	@Setter
	private String param;
	
	@Getter
	private Object value;
	
	private Operations<ObjBack, SelectTable> operations;

	public FieldOperation(Operations<ObjBack, SelectTable> operations, String field) {
		this.operations = operations;
		this.field = field;
	}

	public Operations<ObjBack,SelectTable> gt(Object value) {
		this.value = value;
		this.operations.addField(this);
		return this.operations;
	}
	
	@Override
	public String toSql() {
		return this.operations.getOriginAlias() + "."+field + " > :" + this.param;
	}
	
}
