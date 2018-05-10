package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;

import lombok.Getter;

public class Join<O1, O, T, V> {
	
	private V back;
	
	@Getter
	private javax.persistence.criteria.Join<O, T> jpaJoin;
	private CriteriaBuilder builder;
	private PredicateContainer<O, T, Join<O1, O, T, V>> on;
	
	@SuppressWarnings("rawtypes")
	private List<Join> joins;
	
	public Join<O1,O,T,V> extract(Consumer<javax.persistence.criteria.Join<O, T>> j) {
		j.accept(jpaJoin);
		return this;
	}

	public Join(CriteriaBuilder builder, javax.persistence.criteria.From<O1, O> root, SingularAttribute<O, T> atribute, JoinType joinType, V back) {
		
		this.builder = builder;
		this.back = back;
		this.jpaJoin = root.join(atribute, joinType);
		
	}
	public Join(CriteriaBuilder builder, javax.persistence.criteria.From<O1, O> root, ListAttribute<O, T> atribute, JoinType joinType, V back) {
		
		this.builder = builder;
		this.back = back;
		this.jpaJoin = root.join(atribute, joinType);
		
	}
	
	public V end() {
		return this.back;
	}
	
	public PredicateContainer<O,T,Join<O1, O,T,V>> on() {
		on = new PredicateContainer<>(builder, jpaJoin, PredicateContainer.Type.AND, this);
		return on;
	}
	
	public <A> Join<O, T, A, Join<O1, O, T, V>> innerJoin(SingularAttribute<T, A> atribute) {
		
		Join<O, T, A, Join<O1, O, T, V>> j = new Join<>(builder, jpaJoin, atribute, JoinType.INNER, this);
		this.joins.add(j);
		
		return j;
		
	}
	
	public void generatePredicate() {
		if (this.on!=null) {
			this.jpaJoin.on(on.generatePredicate());
		}
	}
	
}
