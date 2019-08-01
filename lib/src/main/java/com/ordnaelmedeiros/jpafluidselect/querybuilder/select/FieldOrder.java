package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import lombok.Getter;

public class FieldOrder<T, ValueType> {
	
	private Order<T> orderParent;
	
	@Getter
	private String field;
	
	@Getter
	private String order;
	
	public FieldOrder(Order<T> order, String field) {
		this.orderParent = order;
		this.field = field;
	}
	
	public Order<T> asc() {
		this.order = "ASC";
		this.orderParent.getOrders().add(this);
		return orderParent;
	}
	
	public Order<T> desc() {
		this.order = "DESC";
		this.orderParent.getOrders().add(this);
		return orderParent;
	}
	
}
