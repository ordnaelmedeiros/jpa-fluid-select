package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join.joins;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join.Join;

public interface JoinImpl<SelectTable> {

	public String getAliasFrom();
	public Select<SelectTable> getSelect();
	public List<Join<?,?,?>> getJoins();

	default Join<Select<SelectTable>, SelectTable, ?> innerJoin(String field) {
		Join<Select<SelectTable>, SelectTable, ?> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field);
		join.setJoinType(JoinType.INNER);
		this.getJoins().add(join);
		return join;
	}
	default Select<SelectTable> innerJoin(String field, Consumer<Join<Select<SelectTable>, SelectTable, ?>> consumer) {
		consumer.accept(this.innerJoin(field));
		return getSelect();
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> innerJoin(PluralAttribute<SelectTable, ?, Table> field) {
		Join<Select<SelectTable>, SelectTable, Table> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field.getName());
		join.setJoinType(JoinType.INNER);
		this.getJoins().add(join);
		return join;
	}
	default <Table> Select<SelectTable> innerJoin(PluralAttribute<SelectTable, ?, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		consumer.accept(this.innerJoin(field));
		return getSelect();
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> innerJoin(SingularAttribute<SelectTable, Table> field) {
		Join<Select<SelectTable>, SelectTable, Table> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field.getName());
		join.setJoinType(JoinType.INNER);
		this.getJoins().add(join);
		return join;
	}
	default <Table> Select<SelectTable> innerJoin(SingularAttribute<SelectTable, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		consumer.accept(this.innerJoin(field));
		return getSelect();
	}
	
	default Join<Select<SelectTable>, SelectTable, ?> leftJoin(String field) {
		Join<Select<SelectTable>, SelectTable, ?> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field);
		join.setJoinType(JoinType.LEFT);
		this.getJoins().add(join);
		return join;
	}
	default Select<SelectTable> leftJoin(String field, Consumer<Join<Select<SelectTable>, SelectTable, ?>> consumer) {
		consumer.accept(this.leftJoin(field));
		return getSelect();
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> leftJoin(PluralAttribute<SelectTable, ?, Table> field) {
		Join<Select<SelectTable>, SelectTable, Table> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field.getName());
		join.setJoinType(JoinType.LEFT);
		this.getJoins().add(join);
		return join;
	}
	default <Table> Select<SelectTable> leftJoin(PluralAttribute<SelectTable, ?, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		consumer.accept(this.leftJoin(field));
		return getSelect();
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> leftJoin(SingularAttribute<SelectTable, Table> field) {
		Join<Select<SelectTable>, SelectTable, Table> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field.getName());
		join.setJoinType(JoinType.LEFT);
		this.getJoins().add(join);
		return join;
	}
	default <Table> Select<SelectTable> leftJoin(SingularAttribute<SelectTable, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		consumer.accept(this.leftJoin(field));
		return getSelect();
	}
	
	default Join<Select<SelectTable>, SelectTable, ?> rightJoin(String field) {
		Join<Select<SelectTable>, SelectTable, ?> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field);
		join.setJoinType(JoinType.RIGHT);
		this.getJoins().add(join);
		return join;
	}
	default Select<SelectTable> rightJoin(String field, Consumer<Join<Select<SelectTable>, SelectTable, ?>> consumer) {
		consumer.accept(this.rightJoin(field));
		return getSelect();
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> rightJoin(PluralAttribute<SelectTable, ?, Table> field) {
		Join<Select<SelectTable>, SelectTable, Table> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field.getName());
		join.setJoinType(JoinType.RIGHT);
		this.getJoins().add(join);
		return join;
	}
	default <Table> Select<SelectTable> rightJoin(PluralAttribute<SelectTable, ?, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		consumer.accept(this.rightJoin(field));
		return getSelect();
	}
	
	default <Table> Join<Select<SelectTable>, SelectTable, Table> rightJoin(SingularAttribute<SelectTable, Table> field) {
		Join<Select<SelectTable>, SelectTable, Table> join = new Join<>(getSelect(), getSelect(), getAliasFrom(), field.getName());
		join.setJoinType(JoinType.RIGHT);
		this.getJoins().add(join);
		return join;
	}
	default <Table> Select<SelectTable> rightJoin(SingularAttribute<SelectTable, Table> field, Consumer<Join<Select<SelectTable>, SelectTable, Table>> consumer) {
		consumer.accept(this.rightJoin(field));
		return getSelect();
	}
	
}
