package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.FieldOperation;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

public interface OperationTransformDate<ObjBack, SelectTable> {
	
	Operations<ObjBack, SelectTable> getOperations();
	String toSql();
	
	default FieldOperation<ObjBack, SelectTable, Integer> year() {
		return new FieldOperation<>(this.getOperations(), "YEAR("+this.toSql()+")");
	}

	default FieldOperation<ObjBack, SelectTable, Integer> month() {
		return new FieldOperation<>(this.getOperations(), "MONTH("+this.toSql()+")");
	}

	default FieldOperation<ObjBack, SelectTable, Integer> day() {
		return new FieldOperation<>(this.getOperations(), "DAY("+this.toSql()+")");
	}
	
	
}
