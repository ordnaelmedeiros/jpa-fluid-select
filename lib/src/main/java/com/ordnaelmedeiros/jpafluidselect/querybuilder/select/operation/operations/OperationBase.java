package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

public interface OperationBase<ObjBack, SelectTable, Table, Type> {
	
	Operations<ObjBack, SelectTable, Table> getOperations();
	String toSql();
	
	String createParam(Object value);
	void setSql(String sql);
	Operations<ObjBack, SelectTable, Table> end();
	
}
