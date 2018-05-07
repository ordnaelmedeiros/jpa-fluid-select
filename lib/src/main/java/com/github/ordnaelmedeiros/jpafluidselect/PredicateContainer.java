package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;

public class PredicateContainer<T,D,V> {
	
	public enum Type {
		AND, OR
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
				lista.add(predicateContainer.generatePredicate());
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
		PredicateContainer<T,D,PredicateContainer<T,D,V>> predicateContainer = new PredicateContainer<>(this.builder, this.from, PredicateContainer.Type.OR, this);
		this.container.add(predicateContainer);
		return predicateContainer;
	}

	public PredicateContainer<T,D,PredicateContainer<T,D,V>> and() {
		PredicateContainer<T,D,PredicateContainer<T,D,V>> predicateContainer = new PredicateContainer<>(this.builder, this.from, PredicateContainer.Type.AND, this);
		this.container.add(predicateContainer);
		return predicateContainer;
	}
	
	public <A> PredicateContainer<T,D,V> equal(SingularAttribute<D, A> field, A value) {
		Predicate p = this.builder.equal(this.from.get(field), value);
		this.container.add(new PredicateContainer<>(p));
		return this;
	}

	public <A> PredicateContainer<T,D,V> like(SingularAttribute<D, String> field, String value) {
		Predicate p = this.builder.like(this.from.get(field), value);
		this.container.add(new PredicateContainer<>(p));
		return this;
	}
	
}
