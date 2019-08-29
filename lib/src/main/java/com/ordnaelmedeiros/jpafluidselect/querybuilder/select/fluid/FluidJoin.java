package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join.Join;

public interface FluidJoin<SelectTable> {
	
	public Select<SelectTable> getSelect();
	
	default Join<Select<SelectTable>, SelectTable, ?> innerJoin(String field) {
		return this.getSelect().innerJoin(field);
	}
	default <Table> Join<Select<SelectTable>, SelectTable, Table> innerJoin(PluralAttribute<SelectTable, ?, Table> field) {
		return this.getSelect().innerJoin(field);
	}
	default <Table> Join<Select<SelectTable>, SelectTable, Table> innerJoin(SingularAttribute<SelectTable, Table> field) {
		return this.getSelect().innerJoin(field);
	}
	
	default Join<Select<SelectTable>, SelectTable, ?> leftJoin(String field) {
		return this.getSelect().leftJoin(field);
	}
	default <Table> Join<Select<SelectTable>, SelectTable, Table> leftJoin(PluralAttribute<SelectTable, ?, Table> field) {
		return this.getSelect().leftJoin(field);
	}
	default <Table> Join<Select<SelectTable>, SelectTable, Table> leftJoin(SingularAttribute<SelectTable, Table> field) {
		return this.getSelect().leftJoin(field);
	}

	default Join<Select<SelectTable>, SelectTable, ?> rightJoin(String field) {
		return this.getSelect().rightJoin(field);
	}
	default <Table> Join<Select<SelectTable>, SelectTable, Table> rightJoin(PluralAttribute<SelectTable, ?, Table> field) {
		return this.getSelect().rightJoin(field);
	}
	default <Table> Join<Select<SelectTable>, SelectTable, Table> rightJoin(SingularAttribute<SelectTable, Table> field) {
		return this.getSelect().rightJoin(field);
	}
	
}
