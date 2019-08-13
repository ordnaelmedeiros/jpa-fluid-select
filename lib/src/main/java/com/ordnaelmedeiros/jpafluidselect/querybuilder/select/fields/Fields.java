package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ResultType;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidEnd;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidGroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidJoin;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidOrder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidSelect;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidWhere;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Container;

import lombok.Getter;

public class Fields<SelectTable> implements 
		FluidEnd<Select<SelectTable>>,
		FluidSelect<SelectTable>,
		FluidOrder<SelectTable>,
		FluidWhere<SelectTable>,
		FluidJoin<SelectTable>,
		FluidGroupBy<SelectTable> {

	@Getter
	private Select<SelectTable> select;
	
	@Getter
	private List<ToSql> list;
	
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
	
	public FieldSelect<SelectTable> field(String field) {
		return new FieldSelect<>(this, this.aliasFrom, field);
	}

	public Fields<SelectTable> count() {
		this.list.add(new FieldSelect<>(this, "count(*)"));
		return this;
	}
	public Fields<SelectTable> sum(String field) {
		
		Container con = new Container();
		con.setPrefix("sum(");
		con.setSuffix(")");
		
		FieldSelect<SelectTable> fieldSelect = new FieldSelect<>(this, this.aliasFrom, field);
		con.add(fieldSelect);
		
		this.list.add(con);
		return this;
	}
	
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	public String toSql(ResultType resultType, Class<?> resultClass) {
		
		if (this.isEmpty()) {
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
