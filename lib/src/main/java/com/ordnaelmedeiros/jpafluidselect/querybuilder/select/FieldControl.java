package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

public class FieldControl<Back> {

	private Back back;
	private String sql;
	
	public FieldControl(String alias, String field) {
		this.sql = alias+"."+field;
	}
	
	protected void setBack(Back back) {
		this.back = back;
	}
	
	protected String getSql() {
		return this.sql;
	}
	
	public Back cast(Class<?> klass) {
		this.sql = "CAST("+this.sql+" AS "+klass.getName()+")";
		return back;
	}
	
	public Back year() {
		this.sql = "YEAR(" + this.sql + ")";
		return back;
	}
	
	public Back month() {
		this.sql = "MONTH(" + this.sql + ")";
		return back;
	}
	
	public Back day() {
		this.sql = "DAY(" + this.sql + ")";
		return back;
	}
	
	public Back hour() {
		this.sql = "HOUR(" + this.sql + ")";
		return back;
	}
	
	public Back minute() {
		this.sql = "MINUTE(" + this.sql + ")";
		return back;
	}
	
	public Back second() {
		this.sql = "SECOND(" + this.sql + ")";
		return back;
	}
	
}
