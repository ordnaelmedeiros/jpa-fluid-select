package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.where;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

public class Where<SelectTable> extends Operations<Select<SelectTable>, SelectTable, SelectTable> {

	public Where(Select<SelectTable> select, String aliasFrom) {
		super(select, select, aliasFrom);
	}

}
