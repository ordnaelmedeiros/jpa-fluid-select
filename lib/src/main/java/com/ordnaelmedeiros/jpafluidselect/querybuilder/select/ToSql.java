package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import java.util.List;

public interface ToSql {

	String toSql();
	List<Operation> getOperations();
	
}
