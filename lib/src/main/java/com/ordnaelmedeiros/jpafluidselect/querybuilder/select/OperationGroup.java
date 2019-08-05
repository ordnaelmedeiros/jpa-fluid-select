package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.metamodel.Attribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.defaults.SelectAll;

import lombok.Getter;
import lombok.Setter;

public class OperationGroup<Back, T> implements SelectAll<T>, ToSql {

	@Setter
	private String initSql = " ( ";
	
	@Setter
	private String finalSql = " ) ";
	
	@Setter
	private String innerSql = " AND ";
	
	
	private Back back;
	
	@Getter
	protected Select<T> select;
	
	protected QueryBuilder queryBuilder;
	
	private List<ToSql> childrens = new ArrayList<>();

	public void add(Operation op) {
		//this.operations.add(op);
		this.childrens.add(op);
	}

	public FieldOperation<Back, OperationGroup<Back, T>, T, Object> field(String field) {
		return new FieldOperation<>(this, queryBuilder, this, field);
	}
	
	public <ValueType> FieldOperation<Back, OperationGroup<Back, T>, T, ValueType> field(Attribute<T, ValueType> field) {
		return new FieldOperation<>(this, queryBuilder, this, field.getName());
	}
	
	public OperationGroup(Back back, Select<T> select, QueryBuilder queryBuilder) {
		this.back = back;
		this.select = select;
		this.queryBuilder = queryBuilder;
	}

	public OperationGroup<OperationGroup<Back, T>, T> orGroup() {
		OperationGroup<OperationGroup<Back,T>,T> group = new OperationGroup<>(this, select, queryBuilder);
		group.setInnerSql(" OR ");
		this.childrens.add(group);
		return group;
	}
	
	public Back end() {
		return this.back;
	}
	
	public String toSql() {
		
		if (!this.childrens.isEmpty()) {
		
			String sql = this.initSql;
			StringJoiner strWhere = new StringJoiner(this.innerSql);
			for (ToSql o : this.childrens) {
				strWhere.add(o.toSql());
			}
			sql += strWhere.toString() + this.finalSql;
			
			return sql;
			
		} else {
			return "";
		}
	}
	
	public List<Operation> getOperations() {
		List<Operation> lista = new ArrayList<>();
		for (ToSql sql : this.childrens) {
			lista.addAll(sql.getOperations());
		}
		return lista;
	}
	
}
