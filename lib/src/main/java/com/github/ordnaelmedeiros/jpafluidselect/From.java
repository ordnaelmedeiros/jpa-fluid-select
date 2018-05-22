package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;

import lombok.Getter;

public class From<T,R> {
	
	private Class<T> classFrom;
	private Class<R> classReturn;
	
	private EntityManager em;
	
	@Getter
	private CriteriaQuery<R> query;
	
	@Getter
	private Root<T> root;

	private PredicateContainer<T, T, From<T,R>> where;
	
	@SuppressWarnings("rawtypes")
	@Getter
	private List<Join> joins = new ArrayList<>();
	
	private Order<T, T, From<T,R>> order;
	private GroupBy<T, T, From<T,R>> groupBy;
	
	
	@Getter
	private CriteriaBuilder builder;
	
	public From(Select select, Class<T> classFrom, Class<R> classReturn) {
		this.classFrom = classFrom;
		this.classReturn = classReturn;
		builder = select.getBuilder();
		em = select.getEm();
		this.query = select.getBuilder().createQuery(this.classReturn);
		this.root = query.from(this.classFrom);
		this.order = new Order<>(select, root, this);
		this.groupBy = new GroupBy(root, this);
	}
	
	protected From<T,R> count() {
		if (this.classReturn.equals(Long.class)) {
			Expression<Long> count = builder.count(this.root);
			@SuppressWarnings("unchecked")
			Expression<R> e = (Expression<R>)count;
			this.query.select(e);
		}
		return this;
	}
	

	protected From<T,R> multiselect() {
		
		return this;
	}
	
	private SelectFields<T, From<T,R>> fields = null;
	
	public SelectFields<T,From<T,R>> fields() {
		if (this.fields==null) {
			this.fields = new SelectFields<>(this.builder, this.root, this);
		}
		return this.fields;
	}
	
	public PredicateContainer<T, T, From<T,R>> where() {
		if (this.where==null) {
			this.where = new PredicateContainer<>(this.builder, this.root, PredicateContainer.Type.AND, this);
		}
		return this.where;
	}

	private Predicate generatePredicate() {
		
		if (this.fields!=null && !this.fields.isEmpty()) {
			this.query.multiselect(this.fields.getFields());
		}
		
		if (!this.order.isEmpty()) {
			this.query.orderBy(order.getList());
		}
		
		if (!this.groupBy.isEmpty()) {
			this.query.groupBy(this.groupBy.getList());
		}
		
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
	
	public List<R> getResultList() {
		
		Predicate predicate = generatePredicate();
		if (predicate!=null) {
			this.query.where(predicate);
		}
		List<R> result = this.em.createQuery(this.query).getResultList();
		
		return result;
		
	}
	
	public List<R> getResultList(Integer page, Integer limit) {
		
		if (page==null || page<1) {
			page = 1;
		}
		if (limit==null || limit<1) {
			limit = 20;
		}
		
		Predicate predicate = generatePredicate();
		if (predicate!=null) {
			this.query.where(predicate);
		}
		
		List<R> result = 
				this.em.createQuery(this.query)
					.setFirstResult((page-1)*limit)
					.setMaxResults(limit)
					.getResultList();
		
		return result;
		
	}
	
	public R getSingleResult() {
		
		Predicate predicate = generatePredicate();
		if (predicate!=null) {
			this.query.where(predicate);
		}
		
		R result = this.em.createQuery(this.query).getSingleResult();
		return result;
		
	}
	
	public <A> Join<T, T,A,From<T,R>> join(SingularAttribute<T, A> atribute) {
		
		Join<T, T, A, From<T,R>> j = new Join<>(builder, root, atribute, JoinType.INNER, this);
		this.joins.add(j);
		
		return j;
	}

	public <A> Join<T, T,A,From<T,R>> join(ListAttribute<T, A> atribute) {
		return this.join(JoinType.INNER, atribute);
	}
	
	public <A> Join<T, T,A,From<T,R>> join(JoinType type, ListAttribute<T, A> atribute) {
		
		Join<T, T, A, From<T,R>> j = new Join<>(builder, root, atribute, type, this);
		this.joins.add(j);
		
		return j;
	}
	
	public Order<T,T,From<T,R>> order() {
		return this.order;
	}
	
	public From<T,R> orderAsc(SingularAttribute<T, ?> attribute) {
		this.order().asc(attribute);
		return this;
	}
	
	public From<T,R> orderDesc(SingularAttribute<T, ?> attribute) {
		this.order().desc(attribute);
		return this;
	}
	
	public GroupBy<T,T,From<T,R>> group() {
		return this.groupBy;
	}

}
