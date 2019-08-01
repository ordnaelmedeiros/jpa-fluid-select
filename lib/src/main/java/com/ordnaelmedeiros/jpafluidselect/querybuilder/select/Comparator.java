package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import java.util.function.Function;

import javax.persistence.criteria.CriteriaBuilder;

public enum Comparator {
	
	EQUAL(o -> o.getField() + " = :" + o.getParameter()),
	NOT_EQUAL(o -> o.getField() + " != :" + o.getParameter()),
	GREATER_THAN(o -> o.getField() + " > :" + o.getParameter()),
	LESS_THAN(o -> o.getField() + " < :" + o.getParameter()),
	;
	
	private Function<Operation, String> transformer;

	private Comparator(Function<Operation, String> transformer) {
		
		this.transformer = transformer;
		CriteriaBuilder ss;
		
	}
	
	public String toString(Operation operation) {
		return this.transformer.apply(operation);
	}
	
}
