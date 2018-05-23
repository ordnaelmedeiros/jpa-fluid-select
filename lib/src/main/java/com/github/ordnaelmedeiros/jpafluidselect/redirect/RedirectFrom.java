package com.github.ordnaelmedeiros.jpafluidselect.redirect;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;

import com.github.ordnaelmedeiros.jpafluidselect.From;
import com.github.ordnaelmedeiros.jpafluidselect.GroupBy;
import com.github.ordnaelmedeiros.jpafluidselect.Join;
import com.github.ordnaelmedeiros.jpafluidselect.Order;
import com.github.ordnaelmedeiros.jpafluidselect.PredicateContainer;
import com.github.ordnaelmedeiros.jpafluidselect.SelectFields;

public class RedirectFrom<T, R> {

	private From<T, R> from;

	public RedirectFrom(From<T, R> from) {
		this.from = from;
	}

	public CriteriaBuilder getBuilder() {
		return from.getBuilder();
	}

	public SelectFields<T, R> fields() {
		return from.fields();
	}

	public GroupBy<T, T, R> group() {
		return from.group();
	}

	public Order<T, T, R> order() {
		return from.order();
	}

	public PredicateContainer<T,T,From<T,R>> where() {
		return this.from.where();
	}
	
	public From<T, R> orderAsc(SingularAttribute<T, ?> attribute) {
		return from.orderAsc(attribute);
	}

	public From<T, R> orderDesc(SingularAttribute<T, ?> attribute) {
		return from.orderDesc(attribute);
	}

	public <A> Join<T, T, A, From<T, R>> join(ListAttribute<T, A> atribute) {
		return this.from.join(atribute);
	}

	public <A> Join<T, T, A, From<T, R>> join(JoinType type, ListAttribute<T, A> atribute) {
		return this.from.join(type, atribute);
	}

	public <A> Join<T, T, A, From<T, R>> join(SingularAttribute<T, A> atribute) {
		return this.from.join(atribute);
	}

	public List<R> getResultList() {
		return from.getResultList();
	}

	public List<R> getResultList(Integer page, Integer limit) {
		return from.getResultList(page, limit);
	}

	public R getSingleResult() {
		return from.getSingleResult();
	}

}
