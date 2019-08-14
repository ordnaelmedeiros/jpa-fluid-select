package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.metamodel.SingularAttribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidEnd;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidGroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidSelect;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidWhere;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Container;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Content;

public class Order<SelectTable> extends Container
		implements
			FluidEnd<Select<SelectTable>>,
			FluidSelect<SelectTable>,
			FluidWhere<SelectTable>,
			FluidGroupBy<SelectTable> {

	private String aliasOrigin;
	
	private Select<SelectTable> select;

	public Order(Select<SelectTable> select, String aliasOrigin) {
		this.aliasOrigin = aliasOrigin;
		this.setPrefix("ORDER BY ");
		this.setDelimiter(", ");
		this.select = select;
	}
	
	@Override
	public Select<SelectTable> getSelect() {
		return this.select;
	}

	@Override
	public Select<SelectTable> end() {
		return this.select;
	}

	public Order<SelectTable> asc(String field) {
		this.add(new Content(this.aliasOrigin+"."+field + " ASC"));
		return this;
	}
	public Order<SelectTable> desc(String field) {
		this.add(new Content(this.aliasOrigin+"."+field + " DESC"));
		return this;
	}
	
	public Order<SelectTable> asc(SingularAttribute<SelectTable, ?> field) {
		this.add(new Content(this.aliasOrigin+"."+field.getName()+ " ASC"));
		return this;
	}
	public Order<SelectTable> desc(SingularAttribute<SelectTable, ?> field) {
		this.add(new Content(this.aliasOrigin+"."+field.getName()+ " DESC"));
		return this;
	}
	
	public FieldOrder<SelectTable> field(String field) {
		FieldOrder<SelectTable> f = new FieldOrder<>(this, this.aliasOrigin+"."+field);
		return f;
	}
	public FieldOrder<SelectTable> field(SingularAttribute<SelectTable, ?> field) {
		FieldOrder<SelectTable> f = new FieldOrder<>(this, this.aliasOrigin+"."+field.getName());
		return f;
	}

	public FieldOrderDate<SelectTable> fieldDate(SingularAttribute<SelectTable, LocalDate> field) {
		FieldOrderDateTime<SelectTable> f = new FieldOrderDateTime<>(this, this.aliasOrigin+"."+field.getName());
		return f;
	}
	
	public FieldOrderDateTime<SelectTable> fieldDateTime(SingularAttribute<SelectTable, LocalDateTime> field) {
		FieldOrderDateTime<SelectTable> f = new FieldOrderDateTime<>(this, this.aliasOrigin+"."+field.getName());
		return f;
	}
	
}
