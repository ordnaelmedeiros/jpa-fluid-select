package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationBetween;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationEqual;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationGreaterOrEqualThan;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationGreaterThan;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationIn;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationIsNull;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationLessOrEqualThan;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationLessThan;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationLike;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationNotEqual;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationNotEqualOrIsNull;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations.OperationNotInOrIsNull;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms.OperationTransformCast;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms.OperationTransformDate;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms.OperationTransformFunction;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms.OperationTransformString;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms.OperationTransformTime;

import lombok.Getter;
import lombok.Setter;

public class FieldOperation<ObjBack, SelectTable, Table, Type>
		//extends FieldControl<FieldOperation<ObjBack, SelectTable>>
		implements
			OperationTransformDate<ObjBack, SelectTable, Table>,
			OperationTransformTime<ObjBack, SelectTable, Table>,
			OperationTransformCast<ObjBack, SelectTable, Table>,
			OperationTransformString<ObjBack, SelectTable, Table>,
			OperationTransformFunction<ObjBack, SelectTable, Table>,
			
			OperationEqual<ObjBack, SelectTable, Table, Type>,
			OperationNotEqual<ObjBack, SelectTable, Table, Type>,
			OperationNotEqualOrIsNull<ObjBack, SelectTable, Table, Type>,
			OperationLessThan<ObjBack, SelectTable, Table, Type>,
			OperationLessOrEqualThan<ObjBack, SelectTable, Table, Type>,
			OperationGreaterThan<ObjBack, SelectTable, Table, Type>,
			OperationGreaterOrEqualThan<ObjBack, SelectTable, Table, Type>,
			OperationIsNull<ObjBack, SelectTable, Table, Type>,
			OperationBetween<ObjBack, SelectTable, Table, Type>,
			OperationIn<ObjBack, SelectTable, Table, Type>,
			OperationNotInOrIsNull<ObjBack, SelectTable, Table, Type>,
			OperationLike<ObjBack, SelectTable, Table, Type>,
			
			ToSql {

	@Getter
	private Operations<ObjBack, SelectTable, Table> operations;
	
	@Setter
	private String sql;
	
	public FieldOperation(Operations<ObjBack, SelectTable, Table> operations, String sql) {
		this.sql = sql;
		this.operations = operations;
	}
	
	public FieldOperation(Operations<ObjBack, SelectTable, Table> operations, String alias, String field) {
		this.sql = alias+"."+field;
		this.operations = operations;
	}
	
	public String createParam(Object value) {
		return this.operations.getSelect().getParam().create(value);
	}
	
	@Override
	public String toSql() {
		return this.sql;// + this.operation +" :" + this.param;
	}
	
	public Operations<ObjBack,SelectTable, Table> end() {
		this.operations.addField(this);
		return this.operations;
	}

	public FieldOperation<ObjBack, SelectTable, Table, Type> not() {
		this.sql += " NOT ";
		return this;
	}
	
}
