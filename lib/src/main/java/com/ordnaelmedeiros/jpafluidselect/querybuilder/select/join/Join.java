package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join;

import java.util.ArrayList;
import java.util.List;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidEnd;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidGroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidOrder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidSelect;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidWhere;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

import lombok.Getter;

public class Join<ObjBack, SelectTable> 
		implements
			ToSql,
			FluidEnd<ObjBack>,
			FluidSelect<SelectTable>,
			FluidWhere<SelectTable>,
			FluidOrder<SelectTable>,
			FluidGroupBy<SelectTable> {

	private ObjBack objBack;
	
	private List<Join<?,?>> joins;
	
	private Operations<Join<ObjBack, SelectTable>, SelectTable> on;
	
	@Getter
	private Select<SelectTable> select;

	private String field;

	@Getter
	private String aliasJoin;

	private String aliasOrigin;

	public Join(ObjBack objBack, Select<SelectTable> select, String aliasOrigin, String field) {
		
		this.aliasOrigin = aliasOrigin;
		this.aliasJoin = select.getBuilder().createTableAlias(field);
		
		this.objBack = objBack;
		this.select = select;
		this.field = field;
		
		this.joins = new ArrayList<>();
		this.on = new Operations<>(this, this.getSelect(), this.aliasJoin);
		this.on.setPrefix("ON (");
	}
	
	public Join<Join<ObjBack, SelectTable>, SelectTable> leftJoin(String field) {
		Join<Join<ObjBack, SelectTable>, SelectTable> join = new Join<>(this, this.getSelect(), this.aliasJoin, field);
		this.joins.add(join);
		return join;
	}
	
	public Operations<Join<ObjBack,SelectTable>,SelectTable> on() {
		return this.on;
	}
	
	@Override
	public ObjBack end() {
		return this.objBack;
	}
	
	@Override
	public String toSql() {
		String sql = "LEFT JOIN "+aliasOrigin+"." + field + " " + aliasJoin + " \n";
		sql += this.on.toSql() + "\n";
		for (Join<?, ?> join : this.joins) {
			sql += join.toSql() + " \n";
		}
		return sql;
	}
	
}
