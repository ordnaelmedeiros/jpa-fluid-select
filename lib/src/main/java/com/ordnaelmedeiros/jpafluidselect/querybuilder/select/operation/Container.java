package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;

import lombok.Setter;

public class Container implements ToSql {

	@Setter
	private String prefix = "";
	
	@Setter
	private String delimiter = "";
	
	@Setter
	private String suffix = "";
	
	private List<ToSql> childrens;
	
	public void add(ToSql toSql) {
		this.childrens.add(toSql);
	}
	
	public Container() {
		this.childrens = new ArrayList<>();
	}
	
	public String toSql() {
		if (this.childrens.isEmpty()) {
			return "";
		} else {
			StringJoiner sql = new StringJoiner(delimiter, prefix, suffix);
			for (ToSql toSql : this.childrens) {
				String text = toSql.toSql();
				if (text!=null) {
					sql.add(text);
				}
			}
			return sql.toString();
		}
	}

}
