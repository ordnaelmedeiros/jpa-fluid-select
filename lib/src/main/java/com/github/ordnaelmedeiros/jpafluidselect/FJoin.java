package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;

import lombok.Getter;

public class FJoin<O1, O, T, V, F1, F2> {
	
	private V back;
	
	@Getter
	private Join<O, T> jpaJoin;
	private CriteriaBuilder builder;
	private PredicateContainer<O, T, FJoin<O1, O, T, V, F1, F2>, F1, F2> on;
	
	@SuppressWarnings("rawtypes")
	private List<FJoin> joins = new ArrayList<>();

	private FFrom<F1, F2> fFrom;
	
	public FJoin(CriteriaBuilder builder, From<O1, O> root, SingularAttribute<O, T> atribute, JoinType joinType, V back, FFrom<F1, F2> fFrom) {
		
		this.builder = builder;
		this.back = back;
		this.fFrom = fFrom;
		this.jpaJoin = root.join(atribute, joinType);
		
	}
	public FJoin(CriteriaBuilder builder, From<O1, O> root, ListAttribute<O, T> atribute, JoinType joinType, V back, FFrom<F1, F2> fFrom) {
		
		this.builder = builder;
		this.back = back;
		this.fFrom = fFrom;
		this.jpaJoin = root.join(atribute, joinType);
		
	}
	
	public FJoin<O1, O, T, V, F1, F2> extractJoin(Consumer<Join<O, T>> j) {
		j.accept(jpaJoin);
		return this;
	}

	public V end() {
		return this.back;
	}
	
	public PredicateContainer<O,T,FJoin<O1, O,T,V, F1, F2>, F1, F2> on() {
		on = new PredicateContainer<>(builder, jpaJoin, PredicateContainer.Type.AND, this, this.fFrom);
		return on;
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

	public FGroupBy<F1, F1, F2> group() {
		return fFrom.group();
	}

	public FOrder<F1, F1, F2> order() {
		return fFrom.order();
	}
	
	public PredicateContainer<F1,F1,FFrom<F1,F2>, F1, F2> where() {
		return this.fFrom.where();
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
	public <A> FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> join(JoinType type, ListAttribute<T, A> atribute) {
		FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> j = new FJoin<>(builder, jpaJoin, atribute, type, this, this.fFrom);
		this.joins.add(j);
		return j;
	}
	
	public <A> FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> join(SingularAttribute<T, A> atribute) {
		return this.join(JoinType.INNER, atribute);
	}
	public <A> FJoin<O, T, A, FJoin<O1, O, T, V, F1, F2>, F1, F2> join(ListAttribute<T, A> atribute) {
		return this.join(JoinType.INNER, atribute);
	}
	
}
