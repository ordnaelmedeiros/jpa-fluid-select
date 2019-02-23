package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.SingularAttribute;

public class FGroupBy<T, D, R> {

	private FFrom<T, R> back;
	private From<T, D> from;

	public FGroupBy(From<T, D> from, FFrom<T, R> jfsForm) {
		this.from = from;
		this.back = jfsForm;
	}

	public FFrom<T, R> end() {
		return this.back;
	}

	private List<Expression<?>> groups = new ArrayList<>();

	public FGroupBy<T, D, R> add(SingularAttribute<D, ?> attribute) {
		groups.add(from.get(attribute));
		return this;
	}

	public <J, Y, A> FGroupBy<T, D, R> add(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		groups.add(join.get(attribute));
		return this;
	}
	
	public FGroupBy<T, D, R> add(Path<String> path) {
		groups.add(path);
		return this;
	}

	public boolean isEmpty() {
		return this.groups.isEmpty();
	}

	public List<Expression<?>> getList() {
		return groups;
	}

	// REDIRECT
	public CriteriaBuilder getBuilder() {
		return back.getBuilder();
	}

	public FSelectFields<T, R> fields() {
		return back.fields();
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
