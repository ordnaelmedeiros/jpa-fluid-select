package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Content;

public class FieldOrder<SelectTable> {

	protected Order<SelectTable> order;
	protected String field;
	protected String cast;
	protected String ordenation;

	public FieldOrder(Order<SelectTable> order, String field) {
		this.order = order;
		this.field = field;
	}
	
	private void addOrder() {
		if (this.cast==null) {
			order.add(new Content(this.field + " " + this.ordenation));
		} else {
			order.add(new Content("CAST("+this.field +" AS "+ this.cast + ") " + this.ordenation));
		}
	}
	
	public Order<SelectTable> asc() {
		this.ordenation = "ASC";
		this.addOrder();
		return order;
	}
	
	public Order<SelectTable> desc() {
		this.ordenation = "DESC";
		this.addOrder();
		return order;
	}
	/*
	public FieldOrder<SelectTable> fromAlias(String alias) {
		this.alias = alias;
		return this;
	}
	*/
	public FieldOrder<SelectTable> cast(Class<?> klass) {
		FieldOrder<SelectTable> f = new FieldOrder<>(order, "CAST("+field+" as "+klass.getName()+")");
		return f;
	}
	
	public FieldOrderDate<SelectTable> castDate() {
		FieldOrderDate<SelectTable> f = new FieldOrderDate<>(order, "CAST("+field+" as LocalDate)");
		return f;
	}
}
