package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.SingularAttribute;

import com.github.ordnaelmedeiros.jpafluidselect.redirect.RedirectFrom;

public class SelectFields<T, R> extends RedirectFrom<T, R> {
	
	private From<T, R> back;
	private Root<T> root;
	
	private List<Selection<?>> lista = new ArrayList<>();
	
	private CriteriaBuilder builder;
	
	public SelectFields(CriteriaBuilder builder, Root<T> root, From<T, R> from) {
		super(from);
		this.builder = builder;
		this.root = root;
		this.back = from;
	}
	
	public From<T, R> end() {
		return this.back;
	}

	public <A> SelectFields<T, R> add(SingularAttribute<T, A> attribute) {
		Selection<A> s = this.root.get(attribute);
		lista.add(s);
		return this;
	}
	
	public <N> SelectFields<T, R> count(SingularAttribute<T, N> attribute) {
		Expression<Long> count = this.builder.count(this.root.get(attribute));
		lista.add(count);
		return this;
	}
	public <J, Y, A> SelectFields<T, R> count(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<Long> count = this.builder.count(join.get(attribute));
		lista.add(count);
		return this;
	}
	
	public <N> SelectFields<T, R> countDistinct(SingularAttribute<T, N> attribute) {
		Expression<Long> count = this.builder.countDistinct(this.root.get(attribute));
		lista.add(count);
		return this;
	}
	public <J, Y, A> SelectFields<T, R> countDistinct(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<Long> count = this.builder.countDistinct(join.get(attribute));
		lista.add(count);
		return this;
	}
	
	public <N extends Number> SelectFields<T, R> sum(SingularAttribute<T, N> attribute) {
		Expression<N> sum = this.builder.sum(this.root.get(attribute));
		lista.add(sum);
		return this;
	}
	public <J, Y, A extends Number> SelectFields<T, R> sum(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<A> count = this.builder.sum(join.get(attribute));
		lista.add(count);
		return this;
	}
	
	public <N extends Number> SelectFields<T, R> min(SingularAttribute<T, N> attribute) {
		Expression<N> sum = this.builder.min(this.root.get(attribute));
		lista.add(sum);
		return this;
	}
	public <J, Y, A extends Number> SelectFields<T, R> min(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<A> count = this.builder.min(join.get(attribute));
		lista.add(count);
		return this;
	}
	
	public <N extends Number> SelectFields<T, R> max(SingularAttribute<T, N> attribute) {
		Expression<N> sum = this.builder.max(this.root.get(attribute));
		lista.add(sum);
		return this;
	}
	public <J, Y, A extends Number> SelectFields<T, R> max(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<A> count = this.builder.max(join.get(attribute));
		lista.add(count);
		return this;
	}
	
	
	public boolean isEmpty() {
		return this.lista.isEmpty();
	}
	
	public List<Selection<?>> getFields() {
		return this.lista;
	}

	public <J, Y, A> SelectFields<T, R> add(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Selection<A> s = join.get(attribute);
		lista.add(s);
		return this;
	}
	
}
