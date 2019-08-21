package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.FieldControl;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;

public class FieldRef<SelectTable> extends FieldControl<FieldRef<SelectTable>> 
		implements ToSql {
	
	public FieldRef(String aliasFrom, String field) {
		this.setBack(this);
		this.setSql(aliasFrom+"."+field);
	}
	
	@Override
	public String toSql() {
		return this.getSql();
	}
	
}
