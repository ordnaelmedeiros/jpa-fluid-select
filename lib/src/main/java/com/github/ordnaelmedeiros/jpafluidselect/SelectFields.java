package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.SingularAttribute;

public class SelectFields<T, B> {
	
	private B back;
	private Root<T> root;
	
	private List<Selection<?>> lista = new ArrayList<>();
	
	public SelectFields(Root<T> root, B back) {
		this.root = root;
		this.back = back;
	}
	
	public B end() {
		return this.back;
	}

	public <A> SelectFields<T, B> add(SingularAttribute<T, A> attribute) {
		Selection<A> s = this.root.get(attribute);
		lista.add(s);
		return this;
	}
	
	public boolean isEmpty() {
		return this.lista.isEmpty();
	}
	
	public List<Selection<?>> getFields() {
		return this.lista;
	}

	public <J, Y, A> SelectFields<T,B> add(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Selection<A> s = join.get(attribute);
		lista.add(s);
		return this;
	}
	
}
