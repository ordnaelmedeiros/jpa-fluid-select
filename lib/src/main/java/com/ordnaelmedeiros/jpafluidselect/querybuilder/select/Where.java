package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.defaults.SelectAll;

public class Where<T>
	extends
		OperationGroup<Select<T>, T>
	implements
		SelectAll<T>
	{

	public Where(Select<T> select, QueryBuilder queryBuilder) {
		super(select, select, queryBuilder);
		this.setInitSql(" WHERE ( ");
	}

}
