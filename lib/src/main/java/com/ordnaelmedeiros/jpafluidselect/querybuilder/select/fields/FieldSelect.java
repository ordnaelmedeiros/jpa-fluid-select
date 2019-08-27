package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;

public class FieldSelect<SelectTable> extends FieldControl<FieldSelect<SelectTable>> 
		implements ToSql {
	
	private Fields<SelectTable> fields;
	
	public FieldSelect(Fields<SelectTable> fields, String aliasFrom, String field) {
		this.fields = fields;
		this.setBack(this);
		this.setSql(aliasFrom+"."+field);
	}
	
	public Fields<SelectTable> add() {
		this.fields.getList().add(this);
		return fields;
	}
	
	@Override
	public String toSql() {
		return this.getSql();
	}
	
}
