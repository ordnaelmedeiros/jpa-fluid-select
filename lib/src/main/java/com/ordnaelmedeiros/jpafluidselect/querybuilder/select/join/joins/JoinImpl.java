package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join.joins;

import java.util.List;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join.Join;

public interface JoinImpl<SelectTable> {

	public String getAliasFrom();
	public Select<SelectTable> getSelect();
	public List<Join<?,?,?>> getJoins();
	
	default Join<Select<SelectTable>, SelectTable, ?> leftJoin(String field) {
		Join<Select<SelectTable>, SelectTable, ?> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field);
		this.getJoins().add(join);
		return join;
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> leftJoin(PluralAttribute<SelectTable, ?, Table> field) {
		Join<Select<SelectTable>, SelectTable, Table> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field.getName());
		this.getJoins().add(join);
		return join;
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> leftJoin(SingularAttribute<SelectTable, Table> field) {
		Join<Select<SelectTable>, SelectTable, Table> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field.getName());
		this.getJoins().add(join);
		return join;
	}
	
}
