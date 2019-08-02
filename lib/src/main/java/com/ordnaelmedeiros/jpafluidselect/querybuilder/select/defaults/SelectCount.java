package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.defaults;

import java.util.function.Consumer;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;

public interface SelectCount<T> {

	public Select<T> getSelect();
	
	default Long count() {
		return this.getSelect().count();
	}
	
	default Select<T> count(Consumer<Long> consumer) {
		this.getSelect().count(consumer);
		return this.getSelect();
	}
	
}
