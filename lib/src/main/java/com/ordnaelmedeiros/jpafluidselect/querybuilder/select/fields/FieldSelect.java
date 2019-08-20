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
	
	/*
	private Fields<SelectTable> fields;
	
	private String field;

	private String aliasFrom;

	private String literal;

	public FieldSelect(Fields<SelectTable> fields, String aliasFrom, String field) {
		this.fields = fields;
		this.aliasFrom = aliasFrom;
		this.field = field;
	}
	
	public FieldSelect(Fields<SelectTable> fields, String literal) {
		this.fields = fields;
		this.literal = literal;
	}
	
	public Fields<SelectTable> end() {
		this.fields.getList().add(this);
		return fields;
	}

	@Override
	public String toSql() {
		if (this.literal!=null) {
			return this.literal;
		} else {
			return this.aliasFrom+"."+this.field;
		}
	}
	*/
}
