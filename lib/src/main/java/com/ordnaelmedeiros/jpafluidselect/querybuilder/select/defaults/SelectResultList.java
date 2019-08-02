package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.defaults;

import java.util.List;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;

public interface SelectResultList<T> {

	public Select<T> getSelect();
	
	default List<T> getResultList() {
		return this.getSelect().getResultList();
	}
	
}
