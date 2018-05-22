package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.SingularAttribute;

public class SelectFields<T, B> {
	
	private B back;
	private Root<T> root;
	
	private List<Selection<?>> lista = new ArrayList<>();
	
	private CriteriaBuilder builder;
	
	public SelectFields(CriteriaBuilder builder, Root<T> root, B back) {
		this.builder = builder;
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
	
	public <N> SelectFields<T,B> count(SingularAttribute<T, N> attribute) {
		Expression<Long> count = this.builder.count(this.root.get(attribute));
		lista.add(count);
		return this;
	}
	public <J, Y, A> SelectFields<T,B> count(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<Long> count = this.builder.count(join.get(attribute));
		lista.add(count);
		return this;
	}
	

	public <N> SelectFields<T,B> countDistinct(SingularAttribute<T, N> attribute) {
		Expression<Long> count = this.builder.countDistinct(this.root.get(attribute));
		lista.add(count);
		return this;
	}
	public <J, Y, A> SelectFields<T,B> countDistinct(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<Long> count = this.builder.countDistinct(join.get(attribute));
		lista.add(count);
		return this;
	}
	
	public <N extends Number> SelectFields<T,B> sum(SingularAttribute<T, N> attribute) {
		Expression<N> sum = this.builder.sum(this.root.get(attribute));
		lista.add(sum);
		return this;
	}
	public <J, Y, A extends Number> SelectFields<T,B> sum(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<A> count = this.builder.sum(join.get(attribute));
		lista.add(count);
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
