package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

public interface OperationBase<ObjBack, SelectTable, Type> {
	
	Operations<ObjBack, SelectTable> getOperations();
	String toSql();
	
	void createParam(Object value);
	void setSql(String sql);
	String getParam();
	Operations<ObjBack,SelectTable> end();
	
}
