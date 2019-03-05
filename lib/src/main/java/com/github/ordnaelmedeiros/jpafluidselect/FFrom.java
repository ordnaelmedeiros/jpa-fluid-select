package com.github.ordnaelmedeiros.jpafluidselect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.query.criteria.internal.path.SingularAttributePath;

import lombok.Getter;

public class FFrom<T,R> {
	
	private Class<T> classFrom;
	private Class<R> classReturn;
	
	private EntityManager em;
	
	@Getter
	private CriteriaQuery<R> query;
	
	@Getter
	private Root<T> root;

	private PredicateContainer<T, T, FFrom<T,R>, T, R> where;
	
	@SuppressWarnings("rawtypes")
	@Getter
	private List<FJoin> joins = new ArrayList<>();
	
	private FOrder<T, T, R> order;
	private FGroupBy<T, T, R> groupBy;
	
	
	@Getter
	private CriteriaBuilder builder;
	
	public FFrom(FSelect select, Class<T> classFrom, Class<R> classReturn) {
		this.classFrom = classFrom;
		this.classReturn = classReturn;
		builder = select.getBuilder();
		em = select.getEm();
		this.query = select.getBuilder().createQuery(this.classReturn);
		this.root = query.from(this.classFrom);
		this.order = new FOrder<>(select, root, this);
		this.groupBy = new FGroupBy<>(root, this);
	}
	
	protected FFrom<T,R> count() {
		if (this.classReturn.equals(Long.class)) {
			Expression<Long> count = builder.count(this.root);
			@SuppressWarnings("unchecked")
			Expression<R> e = (Expression<R>)count;
			this.query.select(e);
		}
		return this;
	}
	
	private FSelectFields<T, R> fields = null;
	
	public FSelectFields<T, R> fields() {
		if (this.fields==null) {
			this.fields = new FSelectFields<>(this.builder, this.root, this);
		}
		return this.fields;
	}
	
	public PredicateContainer<T, T, FFrom<T,R>, T, R> where() {
		if (this.where==null) {
			this.where = new PredicateContainer<>(this.builder, this.root, PredicateContainer.Type.AND, this, this);
		}
		return this.where;
	}

	@SuppressWarnings("unchecked")
	private Predicate generatePredicate() {
		
		if (this.fields!=null && !this.fields.isEmpty()) {
			if (this.fields.size()==1) {
				this.query.select((Selection<? extends R>)this.fields.getFields().get(0));
			} else {
				this.query.multiselect(this.fields.getFields());
			}
		}
		
		if (!this.order.isEmpty()) {
			this.query.orderBy(order.getList());
		}
		
		if (!this.groupBy.isEmpty()) {
			this.query.groupBy(this.groupBy.getList());
		}
		
		if (!this.joins.isEmpty()) {
			for (@SuppressWarnings("rawtypes") FJoin join : this.joins) {
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
	
	private <E> E transform(Object[] r, Class<E> classe) throws Exception {
		
		E o = classe.newInstance();
		int index = 0;
		for (Selection<?> selection : this.fields().getFields()) {
			
			String cName = "";
			if (selection.getAlias()!=null) {
				cName = selection.getAlias();
			} else if (selection instanceof SingularAttributePath) {
				SingularAttributePath<?> sPath = (SingularAttributePath<?>) selection;
				cName = sPath.getAttribute().getName();
			}
			
			Field field = classe.getDeclaredField(cName);
			field.setAccessible(true);
			field.set(o, r[index]);
			index++;
		}
		
		return o;
		
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> getResultList(Class<E> trasnformClass) throws Exception {
		
		if (!this.classReturn.equals(Object[].class)) {
			throw new Exception("Use fromCustomFields");
		}
		
		if (this.fields().getFields().size()==0) {
			throw new Exception("Use .fields()");
		}
		
		List<E> list = new ArrayList<>();
		List<Object[]> resultList = (List<Object[]>) this.getResultList();
		
		for (Object[] r : resultList) {
			E transform = this.transform(r, trasnformClass);
			list.add(transform);
		}
		
		return list;
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
	
	public <E> E getSingleResult(Class<E> trasnformClass) throws Exception {
		
		if (!this.classReturn.equals(Object[].class)) {
			throw new Exception("Use fromCustomFields");
		}
		
		if (this.fields().getFields().size()==0) {
			throw new Exception("Use .fields()");
		}
		
		Object[] obj = (Object[]) this.getSingleResult();
		if (obj==null) {
			return null;
		}
		
		E transform = this.transform(obj, trasnformClass);
		
		return transform;
		
	}
	
	public R getSingleResult() {
		
		Predicate predicate = generatePredicate();
		if (predicate!=null) {
			this.query.where(predicate);
		}
		
		try {
			
			R result = this.em.createQuery(this.query).getSingleResult();
			return result;
			
		} catch (NoResultException e) {
			
			return null;
			
		}
		
	}
	
	public FFrom<T, R> print() {
		
		List<R> listReturn = this.getResultList();
		
		int cLength = 15; 
		
		List<String> columns = new ArrayList<>();
		
		if (this.classReturn.equals(Object[].class)) {
			
			for (Selection<?> selection : this.fields.getFields()) {
				String cName = "";
				if (selection.getAlias()!=null) {
					cName = selection.getAlias();
				} else if (selection instanceof SingularAttributePath) {
					SingularAttributePath<?> sPath = (SingularAttributePath<?>) selection;
					cName = sPath.getAttribute().getName();
				}
				columns.add(cName);
			}
			
		} else {
			
			for (Field field : this.classReturn.getDeclaredFields()) {
				columns.add(field.getName());
			}
			
		}
		
		System.out.print("|");
		for (String c : columns) {
			System.out.print(String.format("%-"+cLength+"s|", c));
		}
		
		if (this.classReturn.equals(Object[].class)) {
			@SuppressWarnings("unchecked")
			List<Object[]> list = (List<Object[]>) listReturn;
			
			for (Object[] lObj : list) {
				System.out.println();
				System.out.print("|");
				for (Object object : lObj) {
					if (object==null) {
						System.out.print(String.format("%"+cLength+"s|", ""));
					} else {
						String valor = object.toString();
						if (valor.length()>cLength) {
							System.out.print(String.format("%"+cLength+"s|", valor.substring(0,cLength)));
						} else {
							System.out.print(String.format("%"+cLength+"s|", valor));
						}
					}
				}
			}
			
		} else {
			for (R o : listReturn) {
				
				System.out.println();
				System.out.print("|");
				for (Field field : this.classReturn.getDeclaredFields()) {
					
					if (Collection.class.isAssignableFrom(field.getType())) {
						
						System.out.print(String.format("%"+cLength+"s|", ""));
						
					} else {
						
						field.setAccessible(true);
						
						Object object = null;
						try {
							object = field.get(o);
						} catch (Exception e) {
							object = null;
						}
						
						if (object==null) {
							System.out.print(String.format("%"+cLength+"s|", ""));
						} else {
							String valor = object.toString();
							if (valor.length()>cLength) {
								System.out.print(String.format("%"+cLength+"s|", valor.substring(0,cLength)));
							} else {
								System.out.print(String.format("%"+cLength+"s|", valor));
							}
						}
					}
						
				}
			}
		}
		System.out.println();
		
		return this;
		
	}
	
	public <A> FJoin<T, T,A,FFrom<T,R>, T, R> join(JoinType type, SingularAttribute<T, A> atribute) {
		
		FJoin<T, T, A, FFrom<T,R>, T, R> j = new FJoin<>(builder, root, atribute, type, this, this);
		this.joins.add(j);
		
		return j;
	}

	public <A> FJoin<T, T,A,FFrom<T,R>, T, R> join(JoinType type, ListAttribute<T, A> atribute) {
		
		FJoin<T, T, A, FFrom<T,R>, T, R> j = new FJoin<>(builder, root, atribute, type, this, this);
		this.joins.add(j);
		
		return j;
	}
	
	public <A> FJoin<T, T,A,FFrom<T,R>, T, R> join(SingularAttribute<T, A> atribute) {
		return this.join(JoinType.INNER, atribute);
	}

	public <A> FJoin<T, T,A,FFrom<T,R>, T, R> join(ListAttribute<T, A> atribute) {
		return this.join(JoinType.INNER, atribute);
	}
	
	
	public FOrder<T,T,R> order() {
		return this.order;
	}
	
	public FGroupBy<T,T,R> group() {
		return this.groupBy;
	}

	public FFrom<T,R> distinct() {
		this.query.distinct(true);
		return this;
	}

}
