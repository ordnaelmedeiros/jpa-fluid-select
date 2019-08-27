package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation;

import java.util.Arrays;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationBetween;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationEqual;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationGreaterOrEqualThan;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationGreaterThan;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationIsNull;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationLessOrEqualThan;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationLessThan;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms.OperationTransformCast;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms.OperationTransformDate;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms.OperationTransformString;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms.OperationTransformTime;

import lombok.Getter;
import lombok.Setter;

public class FieldOperation<ObjBack, SelectTable, Type>
		//extends FieldControl<FieldOperation<ObjBack, SelectTable>>
		implements
			OperationTransformDate<ObjBack, SelectTable>,
			OperationTransformTime<ObjBack, SelectTable>,
			OperationTransformCast<ObjBack, SelectTable>,
			OperationTransformString<ObjBack, SelectTable>,
			
			OperationEqual<ObjBack, SelectTable, Type>,
			OperationLessThan<ObjBack, SelectTable, Type>,
			OperationLessOrEqualThan<ObjBack, SelectTable, Type>,
			OperationGreaterThan<ObjBack, SelectTable, Type>,
			OperationGreaterOrEqualThan<ObjBack, SelectTable, Type>,
			OperationIsNull<ObjBack, SelectTable, Type>,
			OperationBetween<ObjBack, SelectTable, Type>,
			
			ToSql {

	@Getter
	private Operations<ObjBack, SelectTable> operations;
	
	@Setter
	private String sql;
	
	public FieldOperation(Operations<ObjBack, SelectTable> operations, String sql) {
		this.sql = sql;
		this.operations = operations;
	}
	
	public FieldOperation(Operations<ObjBack, SelectTable> operations, String alias, String field) {
		this.sql = alias+"."+field;
		this.operations = operations;
	}
	
	public String createParam(Object value) {
		return this.operations.getSelect().getParam().create(value);
	}
	
	@SuppressWarnings("unchecked")
	public Operations<ObjBack,SelectTable> in(Type ...value) {
		this.createParam(Arrays.asList(value));
		//this.sql = this.sql + " IN :" + this.param;
		this.operations.addField(this);
		return this.operations;
	}

	@Override
	public String toSql() {
		return this.sql;// + this.operation +" :" + this.param;
	}
	
	public Operations<ObjBack,SelectTable> end() {
		this.operations.addField(this);
		return this.operations;
	}

}
