package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.FieldOperation;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

public interface OperationTransformTime<ObjBack, SelectTable> {
	
	Operations<ObjBack, SelectTable> getOperations();
	String toSql();
	
	default FieldOperation<ObjBack, SelectTable, Integer> hour() {
		return new FieldOperation<>(this.getOperations(), "HOUR("+this.toSql()+")");
	}

	default FieldOperation<ObjBack, SelectTable, Integer> minute() {
		return new FieldOperation<>(this.getOperations(), "MINUTE("+this.toSql()+")");
	}

	default FieldOperation<ObjBack, SelectTable, Integer> second() {
		return new FieldOperation<>(this.getOperations(), "SECOND("+this.toSql()+")");
	}
	
	
}
