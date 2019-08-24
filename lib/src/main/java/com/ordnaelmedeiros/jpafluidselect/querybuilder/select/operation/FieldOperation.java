package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation;

import java.util.Arrays;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms.OperationTransformDate;

import lombok.Getter;
import lombok.Setter;

public class FieldOperation<ObjBack, SelectTable, Type>
		//extends FieldControl<FieldOperation<ObjBack, SelectTable>>
		implements
			OperationTransformDate<ObjBack, SelectTable>,
			ToSql {

	@Getter
	private Operations<ObjBack, SelectTable> operations;
	
	private String sql;
	
	@Setter
	private String param;
	
	public FieldOperation(Operations<ObjBack, SelectTable> operations, String sql) {
		this.sql = sql;
		this.operations = operations;
	}
	
	public FieldOperation(Operations<ObjBack, SelectTable> operations, String alias, String field) {
		this.sql = alias+"."+field;
		this.operations = operations;
	}
	
	private void createParam(Object value) {
		this.param = this.operations.getSelect().getParam().create(value);
	}
	
	public Operations<ObjBack,SelectTable> gt(Type value) {
		this.createParam(value);
		this.sql = this.sql + " > :" + this.param;
		this.operations.addField(this);
		return operations;
	}
	
	public Operations<ObjBack,SelectTable> eq(Type value) {
		this.createParam(value);
		this.sql = this.sql + " = :" + this.param;
		this.operations.addField(this);
		return this.operations;
	}
	
	@SuppressWarnings("unchecked")
	public Operations<ObjBack,SelectTable> in(Type ...value) {
		this.createParam(Arrays.asList(value));
		this.sql = this.sql + " IN :" + this.param;
		this.operations.addField(this);
		return this.operations;
	}

	@Override
	public String toSql() {
		return this.sql;// + this.operation +" :" + this.param;
	}
	
	
}
