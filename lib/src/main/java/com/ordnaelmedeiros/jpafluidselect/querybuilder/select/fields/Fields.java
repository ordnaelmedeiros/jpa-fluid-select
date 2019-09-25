package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.metamodel.SingularAttribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ResultType;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidEnd;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidGroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidJoin;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidOrder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidPagination;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidSelect;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidWhere;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

import lombok.Getter;

public class Fields<SelectTable> implements 
		FluidEnd<Select<SelectTable>>,
		FluidSelect<SelectTable>,
		FluidOrder<SelectTable>,
		FluidWhere<SelectTable>,
		FluidJoin<SelectTable>,
		FluidGroupBy<SelectTable>,
		FluidPagination<SelectTable> {

	@Getter
	private Select<SelectTable> select;
	
	@Getter
	private List<FieldSelect<?>> list;
	
	private String aliasFrom; 
	
	public Fields(Select<SelectTable> select, String aliasFrom) {
		this.select = select;
		this.aliasFrom = aliasFrom;
		this.list = new ArrayList<>();
	}
	
	@Override
	public Select<SelectTable> end() {
		return this.select;
	}

	public Fields<SelectTable> add(String field) {
		this.list.add(new FieldSelect<>(this, this.aliasFrom, field));
		return this;
	}
	public Fields<SelectTable> add(SingularAttribute<SelectTable, ?> field) {
		this.list.add(new FieldSelect<>(this, this.aliasFrom, field.getName()));
		return this;
	}
	
	public Fields<SelectTable> add(FieldRef<?> field) {
		this.list.add(new FieldSelect<>(this, field.toSql()));
		return this;
	}
	
	public FieldSelect<SelectTable> field(String field) {
		return new FieldSelect<>(this, this.aliasFrom, field);
	}
	public FieldSelect<SelectTable> field(SingularAttribute<SelectTable, ?> field) {
		FieldSelect<SelectTable> f = new FieldSelect<>(this, this.aliasFrom, field.getName());
		return f;
	}
	
	public FieldSelect<SelectTable> field(FieldRef<?> field) {
		FieldSelect<SelectTable> f = new FieldSelect<>(this, field.toSql());
		return f;
	}
	
	public Fields<SelectTable> count() {
		this.list.add(new FieldSelect<SelectTable>(this, "COUNT(*)"));
		return this;
	}
	

	public Fields<SelectTable> jpql(ToSql toSql) {
		this.list.add(new FieldSelect<SelectTable>(this, "("+toSql.toSql()+")"));
		return this;
	}
	
	
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	public String toSql(ResultType resultType, Class<?> resultClass) {
		
		if (ResultType.COUNT.equals(resultType)) {
			return " COUNT(*) ";
		} else if (this.isEmpty()) {
			return " "+this.aliasFrom+" ";
		} else {
			if (ResultType.CONSTRUCTOR.equals(resultType)) {
				StringJoiner sql = new StringJoiner(", ", "new " + resultClass.getName() + "(", ") ");
				this.list.forEach(f -> sql.add(f.toSql()));
				return sql.toString();
			} else {
				StringJoiner sql = new StringJoiner(", ");
				this.list.forEach(f -> sql.add(f.toSql()));
				return sql.toString();
			}
		}
		
	}

}
