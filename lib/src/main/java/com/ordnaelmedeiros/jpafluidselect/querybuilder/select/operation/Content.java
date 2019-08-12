package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;

public class Content implements ToSql {

	private String sql;
	
	public Content(String sql) {
		this.sql = sql;
	}
	
	@Override
	public String toSql() {
		return sql;
	}

}
