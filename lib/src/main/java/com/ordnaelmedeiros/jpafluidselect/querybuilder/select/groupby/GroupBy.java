package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.groupby;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.metamodel.SingularAttribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidEnd;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidFields;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidGroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidJoin;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidOrder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidSelect;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidWhere;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Container;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Content;

import lombok.Getter;

public class GroupBy<SelectTable> implements 
		FluidEnd<Select<SelectTable>>,
		FluidSelect<SelectTable>,
		FluidOrder<SelectTable>,
		FluidWhere<SelectTable>,
		FluidJoin<SelectTable>,
		FluidFields<SelectTable>,
		FluidGroupBy<SelectTable> {

	@Getter
	private Select<SelectTable> select;
	
	@Getter
	private List<ToSql> list;
	
	private String aliasFrom; 
	
	public GroupBy(Select<SelectTable> select, String aliasFrom) {
		this.select = select;
		this.aliasFrom = aliasFrom;
		this.list = new ArrayList<>();
	}
	
	@Override
	public Select<SelectTable> end() {
		return this.select;
	}

	public GroupBy<SelectTable> add(String field) {
		this.list.add(new FieldGroupBy<>(this, this.aliasFrom, field));
		return this;
	}
	public GroupBy<SelectTable> add(SingularAttribute<SelectTable, ?> field) {
		this.list.add(new FieldGroupBy<>(this, this.aliasFrom, field.getName()));
		return this;
	}
	
	public FieldGroupBy<SelectTable> field(String field) {
		return new FieldGroupBy<>(this, this.aliasFrom, field);
	}
	public FieldGroupBy<SelectTable> field(SingularAttribute<SelectTable, ?> field) {
		FieldGroupBy<SelectTable> f = new FieldGroupBy<>(this, this.aliasFrom, field.getName());
		return f;
	}

	public GroupBy<SelectTable> count() {
		this.list.add(new Content("count(*)"));
		return this;
	}
	public GroupBy<SelectTable> sum(String field) {
		
		Container con = new Container();
		con.setPrefix("sum(");
		con.setSuffix(")");
		
		FieldGroupBy<SelectTable> fieldSelect = new FieldGroupBy<>(this, this.aliasFrom, field);
		con.add(fieldSelect);
		
		this.list.add(con);
		return this;
	}
	
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	public String toSql() {
		
		if (this.isEmpty()) {
			return " ";
		} else {
			StringJoiner sql = new StringJoiner(", ");
			this.list.forEach(f -> sql.add(f.toSql()));
			return "GROUP BY " + sql.toString();
		}
		
	}

}
