package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.SingularAttribute;


public class FOrder<T, D, R> {

	private FFrom<T, R> fFrom;
	private From<T, D> from;
	private FSelect select;

	public FOrder(FSelect select, From<T, D> from, FFrom<T, R> back) {
		this.select = select;
		this.from = from;
		this.fFrom = back;
	}

	private CriteriaBuilder b() {
		return select.getBuilder();
	}

	public com.github.ordnaelmedeiros.jpafluidselect.FFrom<T, R> end() {
		return this.fFrom;
	}

	private List<javax.persistence.criteria.Order> orders = new ArrayList<>();

	public FOrder<T, D, R> asc(SingularAttribute<D, ?> attribute) {
		orders.add(b().asc(from.get(attribute)));
		return this;
	}

	public FOrder<T, D, R> desc(SingularAttribute<D, ?> attribute) {
		orders.add(b().desc(from.get(attribute)));
		return this;
	}

	public <J, Y, A> FOrder<T, D, R> desc(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		orders.add(b().desc(join.get(attribute)));
		return this;
	}

	public <J, Y, A> FOrder<T, D, R> asc(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		orders.add(b().asc(join.get(attribute)));
		return this;
	}
	
	public FOrder<T, D, R> asc(Path<String> path) {
		orders.add(b().asc(path));
		return this;
	}

	public FOrder<T, D, R> desc(Path<String> path) {
		orders.add(b().desc(path));
		return this;
	}

	public boolean isEmpty() {
		return this.orders.isEmpty();
	}

	public List<javax.persistence.criteria.Order> getList() {
		return orders;
	}
	
	// REDIRECT
	public CriteriaBuilder getBuilder() {
		return fFrom.getBuilder();
	}

	public FSelectFields<T, R> fields() {
		return fFrom.fields();
	}

	public FGroupBy<T, T, R> group() {
		return fFrom.group();
	}

	public PredicateContainer<T,T,FFrom<T,R>, T, R> where() {
		return this.fFrom.where();
	}
	/*
	public <A> FJoin<T, T, A, FFrom<T, R>, T, R> join(ListAttribute<T, A> atribute) {
		return this.fFrom.join(atribute);
	}

	public <A> FJoin<T, T, A, FFrom<T, R>, T, R> join(JoinType type, ListAttribute<T, A> atribute) {
		return this.fFrom.join(type, atribute);
	}

	public <A> FJoin<T, T, A, FFrom<T, R>, T, R> join(SingularAttribute<T, A> atribute) {
		return this.fFrom.join(atribute);
	}
	*/
	public List<R> getResultList() {
		return fFrom.getResultList();
	}

	public List<R> getResultList(Integer page, Integer limit) {
		return fFrom.getResultList(page, limit);
	}

	public R getSingleResult() {
		return fFrom.getSingleResult();
	}

	public List<R> getResultList(Class<R> trasnformClass) throws Exception {
		return fFrom.getResultList(trasnformClass);
	}
	
	public R getSingleResult(Class<R> trasnformClass) throws Exception {
		return fFrom.getSingleResult(trasnformClass);
	}

}
