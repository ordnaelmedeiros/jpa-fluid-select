package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;

import lombok.Getter;

public class From<T> {
	
	private Class<T> classe;
	private EntityManager em;
	
	@Getter
	private CriteriaQuery<T> query;
	
	@Getter
	private Root<T> root;

	private PredicateContainer<T, T, From<T>> where;
	
	@SuppressWarnings("rawtypes")
	@Getter
	private List<Join> joins = new ArrayList<>();
	

	@Getter
	private CriteriaBuilder builder;
	
	public From(Select select, Class<T> classe) {
		this.classe = classe;
		builder = select.getBuilder();
		em = select.getEm();
		this.query = select.getBuilder().createQuery(this.classe);
		this.root = query.from(this.classe);
	}
	
	public PredicateContainer<T, T, From<T>> where() {
		if (this.where==null) {
			this.where = new PredicateContainer<>(this.builder, this.root, PredicateContainer.Type.AND, this);
		}
		return this.where;
	}

	private Predicate generatePredicate() {
		
		if (!this.joins.isEmpty()) {
			for (@SuppressWarnings("rawtypes") Join join : this.joins) {
				join.generatePredicate();
			}
		}
		
		if (this.where!=null) {
			return this.where.generatePredicate();
		} else {
			return null;
		}
	}
	
	public List<T> getResultList() {
		
		Predicate predicate = generatePredicate();
		if (predicate!=null) {
			this.query.where(predicate);
		}
		List<T> result = this.em.createQuery(this.query).getResultList();
		
		return result;
		
	}
	
	public T getSingleResult() {
		
		Predicate predicate = generatePredicate();
		if (predicate!=null) {
			this.query.where(predicate);
		}
		
		T result = this.em.createQuery(this.query).getSingleResult();
		
		return result;
		
	}
	
	public <A> Join<T, T,A,From<T>> join(SingularAttribute<T, A> atribute) {
		
		Join<T, T, A, From<T>> j = new Join<>(builder, root, atribute, JoinType.INNER, this);
		this.joins.add(j);
		
		return j;
	}

	public <A> Join<T, T,A,From<T>> join(ListAttribute<T, A> atribute) {
		return this.join(JoinType.INNER, atribute);
	}
	
	public <A> Join<T, T,A,From<T>> join(JoinType type, ListAttribute<T, A> atribute) {
		
		Join<T, T, A, From<T>> j = new Join<>(builder, root, atribute, type, this);
		this.joins.add(j);
		
		return j;
	}

	
}
