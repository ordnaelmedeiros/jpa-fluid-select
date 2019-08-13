package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Content;

public class FieldOrder<SelectTable> {

	private Order<SelectTable> order;
	private String field;
	private String alias;

	public FieldOrder(Order<SelectTable> order, String alias, String field) {
		this.order = order;
		this.alias = alias;
		this.field = field;
	}
	
	public Order<SelectTable> asc() {
		order.add(new Content(this.alias+"."+field + " ASC"));
		return order;
	}
	
	public Order<SelectTable> desc() {
		order.add(new Content(this.alias+"."+field + " DESC"));
		return order;
	}
	
	public FieldOrder<SelectTable> fromAlias(String alias) {
		this.alias = alias;
		return this;
	}
	
}
