package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidEnd;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidFields;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidGroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidOrder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidSelect;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidWhere;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.Ref;

import lombok.Getter;
import lombok.Setter;

public class Join<ObjBack, SelectTable, Table> 
		implements
			ToSql,
			FluidEnd<ObjBack>,
			FluidSelect<SelectTable>,
			FluidWhere<SelectTable>,
			FluidOrder<SelectTable>,
			FluidFields<SelectTable>,
			FluidGroupBy<SelectTable> {

	private ObjBack objBack;
	
	private List<Join<?,?,?>> joins;
	
	private Operations<Join<ObjBack, SelectTable, Table>, SelectTable, Table> on;
	
	@Getter
	private Select<SelectTable> select;

	private String field;

	@Getter
	private String aliasJoin;

	private String aliasOrigin;
	
	@Setter
	private JoinType joinType  = JoinType.INNER;

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
	
	public Operations<Join<ObjBack,SelectTable, Table>,SelectTable, Table> on() {
		return this.on;
	}
	
	@Override
	public ObjBack end() {
		return this.objBack;
	}
	
	@Override
	public String toSql() {
		String sql = this.joinType+" JOIN "+aliasOrigin+"." + field + " " + aliasJoin + " \n";
		sql += this.on.toSql() + "\n";
		for (Join<?, ?, ?> join : this.joins) {
			sql += join.toSql() + " \n";
		}
		return sql;
	}

	public Join<ObjBack, SelectTable, Table> alias(String value) {
		this.aliasJoin = value;
		this.on.setOriginAlias(value);
		return this;
	}

	public Join<ObjBack, SelectTable, Table> ref(Ref<Table> ref) {
		
		Objects.requireNonNull(ref, "Create instance of Ref: Ref<Entity> ref = new Ref<>()");
		//ref.setKlass(this.klass);
		ref.setAlias(this.aliasJoin);
		
		return this;
	}
	
	
	public Join<Join<ObjBack, SelectTable, Table>, SelectTable, ?> innerJoin(String field) {
		Join<Join<ObjBack, SelectTable, Table>, SelectTable, ?> join = new Join<>(this, this.getSelect(), this.aliasJoin, field);
		join.setJoinType(JoinType.INNER);
		this.joins.add(join);
		return join;
	}
	public Join<ObjBack,SelectTable,Table> innerJoin(String field, Consumer<Join<Join<ObjBack, SelectTable, Table>, SelectTable, ?>> consumer) {
		consumer.accept(this.innerJoin(field));
		return this;
	}
	
	public <T> Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> innerJoin(PluralAttribute<Table, ?, T> field) {
		Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> join = new Join<>(this, getSelect(), this.aliasJoin, field.getName());
		join.setJoinType(JoinType.INNER);
		this.joins.add(join);
		return join;
	}
	public <T> Join<ObjBack,SelectTable,Table> innerJoin(PluralAttribute<Table, ?, T> field, Consumer<Join<Join<ObjBack, SelectTable, Table>, SelectTable, T>> consumer) {
		consumer.accept(this.innerJoin(field));
		return this;
	}
	
	public <T> Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> innerJoin(SingularAttribute<Table, T> field) {
		Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> join = new Join<>(this, getSelect(), this.aliasJoin, field.getName());
		join.setJoinType(JoinType.INNER);
		this.joins.add(join);
		return join;
	}
	public <T> Join<ObjBack,SelectTable,Table> innerJoin(SingularAttribute<Table, T> field, Consumer<Join<Join<ObjBack, SelectTable, Table>, SelectTable, T>> consumer) {
		consumer.accept(this.innerJoin(field));
		return this;
	}
	
	public Join<Join<ObjBack, SelectTable, Table>, SelectTable, ?> leftJoin(String field) {
		Join<Join<ObjBack, SelectTable, Table>, SelectTable, ?> join = new Join<>(this, this.getSelect(), this.aliasJoin, field);
		join.setJoinType(JoinType.LEFT);
		this.joins.add(join);
		return join;
	}
	public Join<ObjBack,SelectTable,Table> leftJoin(String field, Consumer<Join<Join<ObjBack, SelectTable, Table>, SelectTable, ?>> consumer) {
		consumer.accept(this.leftJoin(field));
		return this;
	}
	
	public <T> Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> leftJoin(PluralAttribute<Table, ?, T> field) {
		Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> join = new Join<>(this, getSelect(), this.aliasJoin, field.getName());
		join.setJoinType(JoinType.LEFT);
		this.joins.add(join);
		return join;
	}
	public <T> Join<ObjBack,SelectTable,Table> leftJoin(PluralAttribute<Table, ?, T> field, Consumer<Join<Join<ObjBack, SelectTable, Table>, SelectTable, T>> consumer) {
		consumer.accept(this.leftJoin(field));
		return this;
	}
	
	public <T> Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> leftJoin(SingularAttribute<Table, T> field) {
		Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> join = new Join<>(this, getSelect(), this.aliasJoin, field.getName());
		join.setJoinType(JoinType.LEFT);
		this.joins.add(join);
		return join;
	}
	public <T> Join<ObjBack,SelectTable,Table> leftJoin(SingularAttribute<Table, T> field, Consumer<Join<Join<ObjBack, SelectTable, Table>, SelectTable, T>> consumer) {
		consumer.accept(this.leftJoin(field));
		return this;
	}

	public Join<Join<ObjBack, SelectTable, Table>, SelectTable, ?> rightJoin(String field) {
		Join<Join<ObjBack, SelectTable, Table>, SelectTable, ?> join = new Join<>(this, this.getSelect(), this.aliasJoin, field);
		join.setJoinType(JoinType.RIGHT);
		this.joins.add(join);
		return join;
	}
	public Join<ObjBack,SelectTable,Table> rightJoin(String field, Consumer<Join<Join<ObjBack, SelectTable, Table>, SelectTable, ?>> consumer) {
		consumer.accept(this.rightJoin(field));
		return this;
	}
	
	
	public <T> Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> rightJoin(PluralAttribute<Table, ?, T> field) {
		Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> join = new Join<>(this, getSelect(), this.aliasJoin, field.getName());
		join.setJoinType(JoinType.RIGHT);
		this.joins.add(join);
		return join;
	}
	public <T> Join<ObjBack,SelectTable,Table> rightJoin(PluralAttribute<Table, ?, T> field, Consumer<Join<Join<ObjBack, SelectTable, Table>, SelectTable, T>> consumer) {
		consumer.accept(this.rightJoin(field));
		return this;
	}
	
	public <T> Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> rightJoin(SingularAttribute<Table, T> field) {
		Join<Join<ObjBack, SelectTable, Table>, SelectTable, T> join = new Join<>(this, getSelect(), this.aliasJoin, field.getName());
		join.setJoinType(JoinType.RIGHT);
		this.joins.add(join);
		return join;
	}
	public <T> Join<ObjBack,SelectTable,Table> rightJoin(SingularAttribute<Table, T> field, Consumer<Join<Join<ObjBack, SelectTable, Table>, SelectTable, T>> consumer) {
		consumer.accept(this.rightJoin(field));
		return this;
	}
	
}
