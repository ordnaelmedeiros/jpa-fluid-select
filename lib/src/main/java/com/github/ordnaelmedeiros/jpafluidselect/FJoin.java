package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;

import lombok.Getter;

public class FJoin<O1, O, T, V, F1, F2> {
	
	private V back;
	
	@Getter
	private Join<O, T> jpaJoin;
	private CriteriaBuilder builder;
	private PredicateContainer<O, T, FJoin<O1, O, T, V, F1, F2>, F1, F2> on;
	
	@Getter
	private String name;
	
	@SuppressWarnings("rawtypes")
	private List<FJoin> joins = new ArrayList<>();

	private FFrom<F1, F2> fFrom;
	
	public FJoin(CriteriaBuilder builder, From<O1, O> root, String atribute, JoinType joinType, V back, FFrom<F1, F2> fFrom) {
		
		this.builder = builder;
		this.back = back;
		this.fFrom = fFrom;
		this.jpaJoin = root.join(atribute, joinType);
		this.name = atribute;
		
	}

	public FJoin(CriteriaBuilder builder, From<O1, O> root, SingularAttribute<O, T> atribute, JoinType joinType, V back, FFrom<F1, F2> fFrom) {
		
		this.builder = builder;
		this.back = back;
		this.fFrom = fFrom;
		this.jpaJoin = root.join(atribute, joinType);
		this.name = atribute.getName();
		
	}
	public FJoin(CriteriaBuilder builder, From<O1, O> root, ListAttribute<O, T> atribute, JoinType joinType, V back, FFrom<F1, F2> fFrom) {
		
		this.builder = builder;
		this.back = back;
		this.fFrom = fFrom;
		this.jpaJoin = root.join(atribute, joinType);
		this.name = atribute.getName();
		
	}
	
	public FJoin<O1, O, T, V, F1, F2> extractJoin(Consumer<Join<O, T>> j) {
		j.accept(jpaJoin);
		return this;
	}

	public V end() {
		return this.back;
	}
	
	public <A> Path<A> get(String attributeName) {
		return this.jpaJoin.get(attributeName);
	}
	public <A> Path<A> get(SingularAttribute<T, A> attribute) {
		return this.jpaJoin.get(attribute);
	}
	
	public PredicateContainer<O,T,FJoin<O1, O,T,V, F1, F2>, F1, F2> on() {
		on = new PredicateContainer<>(builder, jpaJoin, PredicateContainer.Type.AND, this, this.fFrom);
		return on;
	}
	public FJoin<O1,O,T,V,F1,F2> on(Consumer<PredicateContainer<O,T,FJoin<O1, O,T,V, F1, F2>, F1, F2>> consumer) {
		consumer.accept(this.on());
		return this;
	}
	
	public void generatePredicate() {
		if (this.on!=null) {
			this.jpaJoin.on(on.generatePredicate());
		}
		for (@SuppressWarnings("rawtypes") FJoin fJoin : this.joins) {
			fJoin.generatePredicate();
		}
	}
	
	// REDIRECT
	public CriteriaBuilder getBuilder() {
		return fFrom.getBuilder();
	}

	public FSelectFields<F1, F2> fields() {
		return fFrom.fields();
	}
	public FJoin<O1,O,T,V,F1,F2> fields(Consumer<FSelectFields<F1, F2>> consumer) {
		fFrom.fields(consumer);
		return this;
	}

	public FGroupBy<F1, F1, F2> group() {
		return fFrom.group();
	}
	public FJoin<O1,O,T,V,F1,F2> group(Consumer<FGroupBy<F1, F1, F2>> consumer) {
		consumer.accept(fFrom.group());
		return this;
	}

	public FOrder<F1, F1, F2> order() {
		return fFrom.order();
	}
	public FJoin<O1,O,T,V,F1,F2> order(Consumer<FOrder<F1, F1, F2>> consumer) {
		consumer.accept(fFrom.order());
		return this;
	}
	
	public PredicateContainer<F1,F1,FFrom<F1,F2>, F1, F2> where() {
		return this.fFrom.where();
	}
	public FJoin<O1,O,T,V,F1,F2> where(Consumer<PredicateContainer<F1,F1,FFrom<F1,F2>, F1, F2>> consumer) {
		consumer.accept(fFrom.where());
		return this;
	}
	
	/*
	public <A> FJoin<F1, F1, A, FFrom<F1, F2>, F1, F2> join(ListAttribute<F1, A> atribute) {
		return this.fFrom.join(atribute);
	}

	public <A> FJoin<F1, F1, A, FFrom<F1, F2>, F1, F2> join(JoinType type, ListAttribute<F1, A> atribute) {
		return this.fFrom.join(type, atribute);
	}

	public <A> FJoin<F1, F1, A, FFrom<F1, F2>, F1, F2> join(SingularAttribute<F1, A> atribute) {
		return this.fFrom.join(atribute);
	}
	*/
	
	public List<F2> getResultList() {
		return fFrom.getResultList();
	}

	public List<F2> getResultList(Integer page, Integer limit) {
		return fFrom.getResultList(page, limit);
	}

	public F2 getSingleResult() {
		return fFrom.getSingleResult();
	}

	public List<F2> getResultList(Class<F2> trasnformClass) throws Exception {
		return fFrom.getResultList(trasnformClass);
	}
	
	public F2 getSingleResult(Class<F2> trasnformClass) throws Exception {
		return fFrom.getSingleResult(trasnformClass);
	}
	
	
	
	
	public <A> FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> join(JoinType type, SingularAttribute<T, A> atribute) {
		FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> j = new FJoin<>(builder, jpaJoin, atribute, type, this, this.fFrom);
		this.joins.add(j);
		return j;
	}
	public <A> FJoin<O1,O,T,V,F1,F2> join(JoinType type, SingularAttribute<T, A> atribute, Consumer<FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2>> consumer) {
		consumer.accept(this.join(type, atribute));
		return this;
	}
	
	public <A> FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> join(JoinType type, ListAttribute<T, A> atribute) {
		FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> j = new FJoin<>(builder, jpaJoin, atribute, type, this, this.fFrom);
		this.joins.add(j);
		return j;
	}
	public <A> FJoin<O1,O,T,V,F1,F2> join(JoinType type, ListAttribute<T, A> atribute, Consumer<FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2>> consumer) {
		consumer.accept(this.join(type, atribute));
		return this;
	}
	
	public <A> FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> join(SingularAttribute<T, A> atribute) {
		return this.join(JoinType.INNER, atribute);
	}
	public <A> FJoin<O1,O,T,V,F1,F2> join(SingularAttribute<T, A> atribute, Consumer<FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2>> consumer) {
		consumer.accept(this.join(atribute));
		return this;
	}
	
	public <A> FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> join(ListAttribute<T, A> atribute) {
		return this.join(JoinType.INNER, atribute);
	}
	public <A> FJoin<O1,O,T,V,F1,F2> join(ListAttribute<T, A> atribute, Consumer<FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2>> consumer) {
		consumer.accept(this.join(atribute));
		return this;
	}
	

	public <A> FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> join(JoinType type, String atribute) {
		FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> j = new FJoin<>(builder, jpaJoin, atribute, type, this, this.fFrom);
		this.joins.add(j);
		return j;
	}
	
	public <A> FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> join(String atribute) {
		return this.join(JoinType.INNER, atribute);
	}
	public <A> FJoin<O1,O,T,V,F1,F2> join(String atribute, Consumer<FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2>> consumer) {
		consumer.accept(this.join(JoinType.INNER, atribute));
		return this;
	}
	public <A> FJoin<O1,O,T,V,F1,F2> join(JoinType type, String atribute, Consumer<FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2>> consumer) {
		consumer.accept(this.join(type, atribute));
		return this;
	}

	@SuppressWarnings("rawtypes")
	public FJoin getJoin(String name) {
		if (this.joins==null || this.joins.isEmpty()) {
			return null;
		}
		for (FJoin fJoin : this.joins) {
			if (fJoin.getName().equals(name)) {
				return fJoin;
			}
		}
		return null;
	}
}
