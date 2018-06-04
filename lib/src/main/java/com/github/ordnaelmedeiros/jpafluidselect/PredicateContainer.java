package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;

public class PredicateContainer<T, D, V, F1, F2> {
	
	public enum Type {
		IGNORE, AND, OR
	}
	
	private Predicate predicate = null;
	
	@SuppressWarnings("rawtypes")
	private List<PredicateContainer> container = new ArrayList<>();

	private From<T, D> from;
	private Type type;
	private V back;
	
	private FFrom<F1, F2> fFrom;

	private CriteriaBuilder builder;


	public PredicateContainer(CriteriaBuilder builder, From<T, D> from, Type type,  V back, FFrom<F1, F2> fFrom) {
		this.builder = builder;
		this.from = from;
		this.type = type;
		this.back = back;
		this.fFrom = fFrom;
	}
	
	public PredicateContainer(Predicate predicate) {
		this.predicate = predicate;
	}
	
	public V end() {
		return this.back;
	}

	public PredicateContainer<T, D, V, F1, F2> add(Function<From<T, D>, Predicate> toPredicate) {
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
	
	public PredicateContainer<T, D, PredicateContainer<T, D, V, F1, F2>, F1, F2> orGroup() {
		return this.group(Type.OR);
	}
	
	public PredicateContainer<T, D, PredicateContainer<T, D, V, F1, F2>, F1, F2> andGroup() {
		return this.group(Type.AND);
	}
	
	public PredicateContainer<T, D, PredicateContainer<T, D, V, F1, F2>, F1, F2> group(Type t) {
		
		if (!isCan) {
			t = PredicateContainer.Type.IGNORE;
		}
		
		PredicateContainer<T, D, PredicateContainer<T, D, V, F1, F2>, F1, F2> predicateContainer = new PredicateContainer<>(this.builder, this.from, t, this, this.fFrom);
		
		this.isNot = false;
		this.isCan = true;
		
		this.container.add(predicateContainer);
		return predicateContainer;
		
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
				p = b().not(p);
			}
			
			this.container.add(new PredicateContainer<>(p));
			
		}
		this.isNot = false;
		this.isCan = true;
		
	}
	
	private boolean isNot = false;
	public PredicateContainer<T, D, V, F1, F2> not() {
		this.isNot = true;
		return this;
	}
	
	private boolean isCan = true;
	public PredicateContainer<T, D, V, F1, F2> ifCan(boolean condition) {
		this.isCan = condition;
		return this;
	}
	
	public <A> PredicateContainer<T, D, V, F1, F2> equal(SingularAttribute<D, A> field, A value) {
		add(b().equal(f(field), value));
		return this;
	}

	public PredicateContainer<T, D, V, F1, F2> iEqual(SingularAttribute<D, String> field, String value) {
		add(b().equal(b().upper(b().trim(f(field))), value.trim().toUpperCase()));
		return this;
	}
	
	public <A> PredicateContainer<T, D, V, F1, F2> in(SingularAttribute<D, A> field, A[] values) {
		add(f(field).in(values));
		return this;
	}
	
	public PredicateContainer<T, D, V, F1, F2> like(SingularAttribute<D, String> field, String value) {
		add(b().like(f(field), value));
		return this;
	}
	
	public PredicateContainer<T, D, V, F1, F2> iLike(SingularAttribute<D, String> field, String value) {
		add(b().like(b().upper(b().trim(f(field))), value.trim().toUpperCase()));
		return this;
	}
	
	public <A extends Comparable<A>> PredicateContainer<T, D, V, F1, F2> greaterThan(SingularAttribute<D, A> field, A value) {
		add(b().greaterThan(f(field), value));
		return this;
	}
	
	public <A extends Comparable<A>> PredicateContainer<T, D, V, F1, F2> greaterThanOrEqualTo(SingularAttribute<D, A> field, A value) {
		add(b().greaterThanOrEqualTo(f(field), value));
		return this;
	}
	
	public <A extends Comparable<A>> PredicateContainer<T, D, V, F1, F2> lessThan(SingularAttribute<D, A> field, A value) {
		add(b().lessThan(f(field), value));
		return this;
	}
	
	public <A extends Comparable<A>> PredicateContainer<T, D, V, F1, F2> lessThanOrEqualTo(SingularAttribute<D, A> field, A value) {
		add(b().lessThanOrEqualTo(f(field), value));
		return this;
	}
	
	public <A> PredicateContainer<T, D, V, F1, F2> isNull(SingularAttribute<D, A> field) {
		add(b().isNull(f(field)));
		return this;
	}
	
	public <A extends Comparable<A>> PredicateContainer<T, D, V, F1, F2> between(SingularAttribute<D, A> field, A value1, A value2) {
		add(b().between(f(field), value1, value2));
		return this;
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

	
}
