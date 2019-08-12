package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import java.util.List;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;

public interface FluidSelect<SelectTable> {
	
	public Select<SelectTable> getSelect();
	
	default List<SelectTable> getResultList() {
		return this.getSelect().getResultList();
	}
	
	default List<Object[]> getResultObjects() {
		return this.getSelect().getResultObjects();
	}
	
}
