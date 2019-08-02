package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.TypedQuery;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;

import lombok.Getter;

public class Select<T> {

	@Getter
	private QueryBuilder queryBuilder;
	private Class<T> klass;
	
	private Where<T> where;
	
	private Order<T> order;
	
	public Select(QueryBuilder qb, Class<T> klass) {
		this.queryBuilder = qb;
		this.klass = klass;
		this.where = new Where<T>(this, this.queryBuilder);
		this.order = new Order<T>(this, this.queryBuilder);
	}
	
	public Where<T> where() {
		return this.where;
	}
	
	public Order<T> order() {
		return this.order;
	}
	
	public List<T> getResultList() {
		
		String sql = "select c from " + this.klass.getName() + " AS c ";
		if (!this.where.getOperations().isEmpty()) {
			StringJoiner strWhere = new StringJoiner(" and ");
			for (Operation o : this.where.getOperations()) {
				strWhere.add(o.toComparator());
			}
			sql += " where "+strWhere.toString();
		}

		if (!this.order.getOrders().isEmpty()) {
			StringJoiner strOrder = new StringJoiner(", ");
			for (FieldOrder<?, ?> o : this.order.getOrders()) {
				strOrder.add(o.getField() + " " + o.getOrder());
			}
			sql += " order by " + strOrder.toString();
		}
		
		System.out.println(sql);
		TypedQuery<T> query = this.getQueryBuilder().getEm().createQuery(sql, this.klass);
		
		if (!this.where.getOperations().isEmpty()) {
			for (Operation o : this.where.getOperations()) {
				o.createParameter(query);
			}
		}
		
		return query.getResultList();
		
	}
	
	public Long count() {
		
		String sql = "select count(*) from " + this.klass.getName() + " AS c ";
		if (!this.where.getOperations().isEmpty()) {
			StringJoiner strWhere = new StringJoiner(" and ");
			for (Operation o : this.where.getOperations()) {
				strWhere.add(o.toComparator());
			}
			sql += " where "+strWhere.toString();
		}
		
		System.out.println(sql);
		TypedQuery<Long> query = this.getQueryBuilder().getEm().createQuery(sql, Long.class);
		
		if (!this.where.getOperations().isEmpty()) {
			for (Operation o : this.where.getOperations()) {
				o.createParameter(query);
			}
		}
		
		return query.getSingleResult();
		
	}
	
	public Select<T> count(Consumer<Long> consumer) {
		consumer.accept(this.count());
		return this;
	}
	
}
