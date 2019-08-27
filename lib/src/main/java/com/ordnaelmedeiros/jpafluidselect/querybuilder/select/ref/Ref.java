package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref;

import javax.persistence.metamodel.Attribute;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Ref<T> {

	private Class<T> klass;
	private String alias;
	
	public FieldRef<T> field(String att) {
		FieldRef<T> f = new FieldRef<>(alias, att);
		return f;
	}
	
	public FieldRef<T> field(Attribute<T, ?> att) {
		FieldRef<T> f = new FieldRef<>(alias, att.getName());
		return f;
	}
	
}
