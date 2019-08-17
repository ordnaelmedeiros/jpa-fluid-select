package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation;

import java.util.Arrays;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.FieldControl;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;

import lombok.Getter;
import lombok.Setter;

public class FieldOperation<ObjBack, SelectTable>
		extends FieldControl<FieldOperation<ObjBack, SelectTable>>
		implements ToSql {

	private Operations<ObjBack, SelectTable> operations;
	
	private String operation;
	
	@Setter
	private String param;
	
	@Getter
	private Object value;
	
	public FieldOperation(Operations<ObjBack, SelectTable> operations, String alias, String field) {
		super(alias, field);
		this.setBack(this);
		this.operations = operations;
	}
	
	public Operations<ObjBack,SelectTable> gt(Object value) {
		this.value = value;
		this.operation = " > ";
		this.operations.addField(this);
		return operations;
	}
	
	public Operations<ObjBack,SelectTable> eq(Object value) {
		this.value = value;
		this.operation = " = ";
		this.operations.addField(this);
		return this.operations;
	}
	
	public Operations<ObjBack,SelectTable> in(Object ...value) {
		this.value = Arrays.asList(value);
		this.operation = " IN ";
		this.operations.addField(this);
		return this.operations;
	}

	@Override
	public String toSql() {
		return this.getSql() + this.operation +" :" + this.param;
	}
	
}
