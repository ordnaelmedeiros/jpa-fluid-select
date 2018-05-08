package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;

public class PredicateContainer<T,D,V> {
	
	public enum Type {
		IGNORE, AND, OR
	}
	
	private Predicate predicate = null;
	
	@SuppressWarnings("rawtypes")
	private List<PredicateContainer> container = new ArrayList<>();

	private From<T, D> from;
	private Type type;
	private V back;

	private CriteriaBuilder builder;


	public PredicateContainer(CriteriaBuilder builder, From<T, D> from, Type type,  V back) {
		this.builder = builder;
		this.from = from;
		this.type = type;
		this.back = back;
	}
	
	public PredicateContainer(Predicate predicate) {
		this.predicate = predicate;
	}
	
	public V end() {
		return this.back;
	}

	public PredicateContainer<T, D, V> add(Function<From<T, D>, Predicate> toPredicate) {
		Predicate p = toPredicate.apply(this.from);
		this.container.add(new PredicateContainer<>(p));
		return this;
	}
	
	public Predicate generatePredicate() {
		
		if (this.predicate==null && this.container.isEmpty()) {
			return null;
		}
		
		if (this.predicate!=null) {
			return this.predicate;
		} else {
			
			List<Predicate> lista = new ArrayList<>();
			
			for (@SuppressWarnings("rawtypes") PredicateContainer predicateContainer : this.container) {
				Predicate p = predicateContainer.generatePredicate();
				if (p!=null) {
					lista.add(p);
				}
			}
			Predicate[] predicates = new Predicate[lista.size()];
			predicates = lista.toArray(predicates);
			
			switch (this.type) {
				case AND:
					return this.builder.and(predicates);
				case OR:
					return this.builder.or(predicates);
				default:
					return null;
			}
			
		}
		
	}
	
	public PredicateContainer<T,D,PredicateContainer<T,D,V>> or() {
		return this.or(true);
	}
	
	public PredicateContainer<T,D,PredicateContainer<T,D,V>> or(boolean condition) {
		if (condition) {
			PredicateContainer<T,D,PredicateContainer<T,D,V>> predicateContainer = new PredicateContainer<>(this.builder, this.from, PredicateContainer.Type.OR, this);
			this.container.add(predicateContainer);
			return predicateContainer;
		} else {
			PredicateContainer<T,D,PredicateContainer<T,D,V>> predicateContainer = new PredicateContainer<>(this.builder, this.from, PredicateContainer.Type.IGNORE, this);
			this.container.add(predicateContainer);
			return predicateContainer;
		}
	}

	public PredicateContainer<T,D,PredicateContainer<T,D,V>> and() {
		return this.and(true);
	}
	
	public PredicateContainer<T,D,PredicateContainer<T,D,V>> and(boolean condition) {
		if (condition) {
			PredicateContainer<T,D,PredicateContainer<T,D,V>> predicateContainer = new PredicateContainer<>(this.builder, this.from, PredicateContainer.Type.AND, this);
			this.container.add(predicateContainer);
			return predicateContainer;
		} else {
			PredicateContainer<T,D,PredicateContainer<T,D,V>> predicateContainer = new PredicateContainer<>(this.builder, this.from, PredicateContainer.Type.IGNORE, this);
			this.container.add(predicateContainer);
			return predicateContainer;
		}
	}
	
	
	private <A> Path<A> f(SingularAttribute<D, A> field) {
		return this.from.get(field);
	}
	
	private CriteriaBuilder b() {
		return this.builder;
	}
	
	private void add(Predicate p) {
		if (this.isCan) {
			if (this.isNot) {
				this.container.add(new PredicateContainer<>(b().not(p)));
			} else {
				this.container.add(new PredicateContainer<>(p));
			}
		}
		this.isNot = false;
		this.isCan = true;
	}
	
	private boolean isNot = false;
	public PredicateContainer<T,D,V> not() {
		this.isNot = true;
		return this;
	}
	
	private boolean isCan = true;
	public PredicateContainer<T,D,V> ifCan(boolean condition) {
		this.isCan = condition;
		return this;
	}
	
	public <A> PredicateContainer<T,D,V> equal(SingularAttribute<D, A> field, A value) {
		add(b().equal(f(field), value));
		return this;
	}

	public <A> PredicateContainer<T,D,V> in(SingularAttribute<D, A> field, A[] values) {
		add(b().in(f(field)).in(values));
		return this;
	}
	
	public PredicateContainer<T,D,V> like(SingularAttribute<D, String> field, String value) {
		add(b().like(f(field), value));
		return this;
	}
	
	public <A extends Comparable<A>> PredicateContainer<T,D,V> greaterThan(SingularAttribute<D, A> field, A value) {
		add(b().greaterThan(f(field), value));
		return this;
	}
	
	public <A extends Comparable<A>> PredicateContainer<T,D,V> greaterThanOrEqualTo(SingularAttribute<D, A> field, A value) {
		add(b().greaterThanOrEqualTo(f(field), value));
		return this;
	}
	
	public <A extends Comparable<A>> PredicateContainer<T,D,V> lessThan(SingularAttribute<D, A> field, A value) {
		add(b().lessThan(f(field), value));
		return this;
	}
	
	public <A extends Comparable<A>> PredicateContainer<T,D,V> lessThanOrEqualTo(SingularAttribute<D, A> field, A value) {
		add(b().lessThanOrEqualTo(f(field), value));
		return this;
	}
	
	public <A> PredicateContainer<T,D,V> equal(SingularAttribute<D, A> field) {
		add(b().isNull(f(field)));
		return this;
	}
	
	public <A extends Comparable<A>> PredicateContainer<T,D,V> equal22(SingularAttribute<D, A> field, A value1, A value2) {
		add(b().between(f(field), value1, value2));
		return this;
	}
	
}
