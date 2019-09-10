package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.groupby;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.FieldControl;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;

public class FieldGroupBy<SelectTable> extends FieldControl<FieldGroupBy<SelectTable>> 
		implements ToSql {
	
	private GroupBy<SelectTable> fields;
	
	public FieldGroupBy(GroupBy<SelectTable> fields, String aliasFrom, String field) {
		this.fields = fields;
		this.setBack(this);
		this.setSql(aliasFrom+"."+field);
	}
	
	public FieldGroupBy(GroupBy<SelectTable> fields, String sql) {
		this.fields = fields;
		this.setBack(this);
		this.setSql(sql);
	}
	
	public GroupBy<SelectTable> add() {
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
