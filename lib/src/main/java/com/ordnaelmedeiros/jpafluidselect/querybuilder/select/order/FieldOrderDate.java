package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order;

public class FieldOrderDate<SelectTable> extends FieldOrder<SelectTable> {

	public FieldOrderDate(Order<SelectTable> order, String field) {
		super(order, field);
	}

	public FieldOrder<SelectTable> year() {
		FieldOrder<SelectTable> f = new FieldOrder<SelectTable>(order, "YEAR("+field+")");
		return f;
	}
	
}
