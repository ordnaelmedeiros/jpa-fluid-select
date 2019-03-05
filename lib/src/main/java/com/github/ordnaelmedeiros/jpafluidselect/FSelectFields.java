package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.SingularAttribute;

public class FSelectFields<T, R> {
	
	private FFrom<T, R> back;
	private Root<T> root;
	
	private List<Selection<?>> lista = new ArrayList<>();
	
	private CriteriaBuilder builder;
	
	public FSelectFields(CriteriaBuilder builder, Root<T> root, FFrom<T, R> from) {
		this.builder = builder;
		this.root = root;
		this.back = from;
	}
	
	public FFrom<T, R> end() {
		return this.back;
	}
	
	public FSelectFields<T,R> alias(String alias) {
		lista.get(lista.size()-1).alias(alias);
		return this;
	}
	
	public <A> FSelectFields<T, R> add(SingularAttribute<T, A> attribute) {
		Selection<A> s = this.root.get(attribute);
		lista.add(s);
		return this;
	}
	
	public <J, Y, A> FSelectFields<T, R> add(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Selection<A> s = join.get(attribute);
		lista.add(s);
		return this;
	}
	
	public <N> FSelectFields<T, R> count(SingularAttribute<T, N> attribute) {
		Expression<Long> count = this.builder.count(this.root.get(attribute));
		lista.add(count);
		return this;
	}
	public <J, Y, A> FSelectFields<T, R> count(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<Long> count = this.builder.count(join.get(attribute));
		lista.add(count);
		return this;
	}
	
	public <N> FSelectFields<T, R> countDistinct(SingularAttribute<T, N> attribute) {
		Expression<Long> count = this.builder.countDistinct(this.root.get(attribute));
		lista.add(count);
		return this;
	}
	public <J, Y, A> FSelectFields<T, R> countDistinct(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<Long> count = this.builder.countDistinct(join.get(attribute));
		lista.add(count);
		return this;
	}
	
	public <N extends Number> FSelectFields<T, R> sum(SingularAttribute<T, N> attribute) {
		Expression<N> sum = this.builder.sum(this.root.get(attribute));
		lista.add(sum);
		return this;
	}
	public <J, Y, A extends Number> FSelectFields<T, R> sum(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<A> count = this.builder.sum(join.get(attribute));
		lista.add(count);
		return this;
	}
	
	public <N extends Number> FSelectFields<T, R> min(SingularAttribute<T, N> attribute) {
		Expression<N> sum = this.builder.min(this.root.get(attribute));
		lista.add(sum);
		return this;
	}
	public <J, Y, A extends Number> FSelectFields<T, R> min(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<A> count = this.builder.min(join.get(attribute));
		lista.add(count);
		return this;
	}
	
	public <N extends Number> FSelectFields<T, R> max(SingularAttribute<T, N> attribute) {
		Expression<N> sum = this.builder.max(this.root.get(attribute));
		lista.add(sum);
		return this;
	}
	public <J, Y, A extends Number> FSelectFields<T, R> max(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		Expression<A> count = this.builder.max(join.get(attribute));
		lista.add(count);
		return this;
	}
	
	public int size() {
		return this.lista.size();
	}
	
	public boolean isEmpty() {
		return this.lista.isEmpty();
	}
	
	public List<Selection<?>> getFields() {
		return this.lista;
	}
	
	// REDIRECT
	public CriteriaBuilder getBuilder() {
		return back.getBuilder();
	}

	public FGroupBy<T, T, R> group() {
		return back.group();
	}

	public FOrder<T, T, R> order() {
		return back.order();
	}

	public PredicateContainer<T,T,FFrom<T,R>, T, R> where() {
		return this.back.where();
	}
	/*
	public <A> FJoin<T, T, A, FFrom<T, R>, T, R> join(ListAttribute<T, A> atribute) {
		return this.back.join(atribute);
	}

	public <A> FJoin<T, T, A, FFrom<T, R>, T, R> join(JoinType type, ListAttribute<T, A> atribute) {
		return this.back.join(type, atribute);
	}

	public <A> FJoin<T, T, A, FFrom<T, R>, T, R> join(SingularAttribute<T, A> atribute) {
		return this.back.join(atribute);
	}
	*/
	public List<R> getResultList() {
		return back.getResultList();
	}

	public List<R> getResultList(Integer page, Integer limit) {
		return back.getResultList(page, limit);
	}

	public R getSingleResult() {
		return back.getSingleResult();
	}

	public List<R> getResultList(Class<R> trasnformClass) throws Exception {
		return back.getResultList(trasnformClass);
	}
	
	public R getSingleResult(Class<R> trasnformClass) throws Exception {
		return back.getSingleResult(trasnformClass);
	}
	
}
