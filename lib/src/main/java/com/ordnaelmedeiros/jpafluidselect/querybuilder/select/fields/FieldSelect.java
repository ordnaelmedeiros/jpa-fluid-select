package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;

public class FieldSelect<SelectTable> extends FieldControl<FieldSelect<SelectTable>> 
		implements ToSql {
	
	private Fields<SelectTable> fields;
	
	private String field;
	
	private String alias;
	
	public String getAlias() {
		if (alias!=null) {
			return alias;
		} else {
			return field;
		}
	}
	
	public FieldSelect(Fields<SelectTable> fields, String aliasFrom, String field) {
		this.fields = fields;
		this.field = field;
		this.setBack(this);
		this.setSql(aliasFrom+"."+field);
	}
	
	public FieldSelect(Fields<SelectTable> fields, String sql) {
		this.fields = fields;
		this.setBack(this);
		this.setSql(sql);
	}
	
	public Fields<SelectTable> add() {
		this.fields.getList().add(this);
		return fields;
	}
	
	public Fields<SelectTable> alias(String alias) {
		this.alias = alias;
		this.fields.getList().add(this);
		return fields;
	}
	
	@Override
	public String toSql() {
		return this.getSql();
	}
	
}
