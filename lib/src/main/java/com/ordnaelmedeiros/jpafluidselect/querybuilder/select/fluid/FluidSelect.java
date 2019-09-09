package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import java.util.List;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;

public interface FluidSelect<SelectTable> {
	
	public Select<SelectTable> getSelect();
	
	default List<SelectTable> getResultList() {
		return this.getSelect().getResultList();
	}
	default <T> List<T> getResultList(Class<T> klass) {
		return this.getSelect().getResultList(klass);
	}
	default <T> List<T> getResultListByConstructor(Class<T> klass) {
		return this.getSelect().getResultListByConstructor(klass);
	}
	default <T> List<T> getResultListByReflect(Class<T> klass) {
		return this.getSelect().getResultListByReflect(klass);
	}
	
	
	default SelectTable getSingleResult() {
		return this.getSelect().getSingleResult();
	}
	default <T> T getSingleResult(Class<T> klass) {
		return this.getSelect().getSingleResult(klass);
	}
	default <T> T getSingleResultByConstructor(Class<T> klass) {
		return this.getSelect().getSingleResultByConstructor(klass);
	}
	default <T> T getSingleResultByReflect(Class<T> klass) {
		return this.getSelect().getSingleResultByReflect(klass);
	}
	
	
	
	default List<Object[]> getResultObjects() {
		return this.getSelect().getResultObjects();
	}
	
	default Select<SelectTable> print() {
		return this.getSelect().print();
	}
	
	default String resultToString() {
		return this.getSelect().resultToString();
	}
	
}
