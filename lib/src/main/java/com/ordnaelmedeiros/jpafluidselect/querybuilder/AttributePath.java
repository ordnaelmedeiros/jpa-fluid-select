package com.ordnaelmedeiros.jpafluidselect.querybuilder;

import java.util.StringJoiner;

import javax.persistence.metamodel.Attribute;

public class AttributePath<T> {
	
	private StringJoiner text = new StringJoiner(".");
	
	private AttributePath() {
	}
	
	public AttributePath(Attribute<?, T> attribute) {
		this.text.add(attribute.getName());
	}
	
	public <A> AttributePath<A> to(Attribute<T, A> attribute) {
		AttributePath<A> a = new AttributePath<>();
		a.text = this.text;
		a.text.add(attribute.getName());
		return a;
	}
	
	public String toString() {
		return this.text.toString();
	}
	
}
