package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import java.util.function.Consumer;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join.Join;

public interface FluidJoin<SelectTable> {
	
	public Select<SelectTable> getSelect();
	
	
	// inner join
	
	default Join<Select<SelectTable>, SelectTable, ?> innerJoin(String field) {
		return this.getSelect().innerJoin(field);
	}
	default Select<SelectTable> innerJoin(String field, Consumer<Join<Select<SelectTable>, SelectTable, ?>> consumer) {
		return this.getSelect().innerJoin(field, consumer);
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> innerJoin(PluralAttribute<SelectTable, ?, Table> field) {
		return this.getSelect().innerJoin(field);
	}
	default <Table> Select<SelectTable> innerJoin(PluralAttribute<SelectTable, ?, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		return this.getSelect().innerJoin(field, consumer);
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> innerJoin(SingularAttribute<SelectTable, Table> field) {
		return this.getSelect().innerJoin(field);
	}
	default <Table> Select<SelectTable> innerJoin(SingularAttribute<SelectTable, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		return this.getSelect().innerJoin(field, consumer);
	}
	
	
	// left join
	
	default Join<Select<SelectTable>, SelectTable, ?> leftJoin(String field) {
		return this.getSelect().leftJoin(field);
	}
	default Select<SelectTable> leftJoin(String field, Consumer<Join<Select<SelectTable>, SelectTable, ?>> consumer) {
		return this.getSelect().leftJoin(field, consumer);
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> leftJoin(PluralAttribute<SelectTable, ?, Table> field) {
		return this.getSelect().leftJoin(field);
	}
	default <Table> Select<SelectTable> leftJoin(PluralAttribute<SelectTable, ?, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		return this.getSelect().leftJoin(field, consumer);
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> leftJoin(SingularAttribute<SelectTable, Table> field) {
		return this.getSelect().leftJoin(field);
	}
	default <Table> Select<SelectTable> leftJoin(SingularAttribute<SelectTable, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		return this.getSelect().leftJoin(field, consumer);
	}
	
	
	// right join
	
	default Join<Select<SelectTable>, SelectTable, ?> rightJoin(String field) {
		return this.getSelect().rightJoin(field);
	}
	default Select<SelectTable> rightJoin(String field, Consumer<Join<Select<SelectTable>, SelectTable, ?>> consumer) {
		return this.getSelect().rightJoin(field, consumer);
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> rightJoin(PluralAttribute<SelectTable, ?, Table> field) {
		return this.getSelect().rightJoin(field);
	}
	default <Table> Select<SelectTable> rightJoin(PluralAttribute<SelectTable, ?, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		return this.getSelect().rightJoin(field, consumer);
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> rightJoin(SingularAttribute<SelectTable, Table> field) {
		return this.getSelect().rightJoin(field);
	}
	default <Table> Select<SelectTable> rightJoin(SingularAttribute<SelectTable, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		return this.getSelect().rightJoin(field, consumer);
	}
	
}
