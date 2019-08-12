package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.Fields;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.groupby.GroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join.Join;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.Order;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.parameters.Parameters;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.where.Where;

import lombok.Getter;

public class Select<Table> {

	private Class<Table> klass;
	
	@Getter
	private QueryBuilder builder;
	
	private List<Join<?,?>> joins;
	private Where<Table> where;
	private Order<Table> order;
	private GroupBy<Table> groupBy;
	private Fields<Table> fields;
	
	@Getter
	private Parameters<Table> param;
	
	private String aliasFrom;
	
	private ResultType resultType = ResultType.CONSTRUCTOR;
	
	public Select(Class<Table> klass, QueryBuilder builder) {
		
		this.aliasFrom = builder.createTableAlias(klass.getSimpleName());
		
		this.klass = klass;
		this.builder = builder;
		
		this.where = new Where<>(this, this.aliasFrom);
		this.where.setPrefix("WHERE (");
		
		this.order = new Order<>(this, this.aliasFrom);
		this.fields = new Fields<>(this, this.aliasFrom);
		this.groupBy = new GroupBy<>(this, this.aliasFrom);
		
		this.joins = new ArrayList<>();
		this.param = new Parameters<>(this);
		
		
	}
	
	public Join<Select<Table>,Table> leftJoin(String field) {
		Join<Select<Table>, Table> join = new Join<>(this, this, this.aliasFrom, field);
		this.joins.add(join);
		return join;
	}
	
	public Operations<Select<Table>, Table> where() {
		return this.where;
	}
	
	public Order<Table> order() {
		return this.order;
	}
	
	public GroupBy<Table> groupBy() {
		return this.groupBy;
	}
	
	public Fields<Table> fields() {
		return this.fields;
	}
	
	private String toSql() {
		
		String sql = "SELECT ";
		
		sql += this.fields.toSql(resultType, klass);
		sql += " FROM " + this.klass.getName()+" " + this.aliasFrom + " \n";
		for (Join<?, ?> join : this.joins) {
			sql += join.toSql() + "\n";
		}
		sql += this.where.toSql() + "\n";
		sql += this.groupBy.toSql() + "\n";
		sql += this.order.toSql() + "\n";
		
		return sql;
	}
	
	public List<Table> getResultList() {
		
		this.resultType = ResultType.CONSTRUCTOR;
		
		String sql = this.toSql();
		System.out.println("SQL:");
		System.out.println(sql);
		
		TypedQuery<Table> query = this.builder.getEm().createQuery(sql, klass);
		
		List<Table> result = query.getResultList();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getResultObjects() {
		
		resultType = ResultType.ARRAY;
		
		String sql = this.toSql();
		System.out.println("SQL:");
		System.out.println(sql);
		
		Query query = this.builder.getEm().createQuery(sql);
		
		this.param.setParameters(query);
		
		List<Object[]> result = query.getResultList();
		
		return result;
		
	}
	
}
