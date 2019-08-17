package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order;

import javax.persistence.metamodel.SingularAttribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidEnd;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidGroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidSelect;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidWhere;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Container;

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
	
	public FieldOrder<SelectTable> field(String field) {
		FieldOrder<SelectTable> f = new FieldOrder<>(this, this.aliasOrigin, field);
		return f;
	}
	public FieldOrder<SelectTable> field(SingularAttribute<SelectTable, ?> field) {
		FieldOrder<SelectTable> f = new FieldOrder<>(this, this.aliasOrigin, field.getName());
		return f;
	}
	
	public Order<SelectTable> asc(String field) {
		this.field(field).asc();
		return this;
	}
	public Order<SelectTable> desc(String field) {
		this.field(field).desc();
		return this;
	}
	
	public Order<SelectTable> asc(String ...fields) {
		for (String f : fields) {
			this.asc(f);
		}
		return this;
	}
	public Order<SelectTable> desc(String ...fields) {
		for (String f : fields) {
			this.desc(f);
		}
		return this;
	}
	
	public Order<SelectTable> asc(SingularAttribute<SelectTable, ?> field) {
		this.field(field).asc();
		return this;
	}
	public Order<SelectTable> desc(SingularAttribute<SelectTable, ?> field) {
		this.field(field).desc();
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Order<SelectTable> asc(SingularAttribute<SelectTable, ?> ...fields) {
		for (SingularAttribute<SelectTable, ?> f : fields) {
			this.asc(f);
		}
		return this;
	}
	@SuppressWarnings("unchecked")
	public Order<SelectTable> desc(SingularAttribute<SelectTable, ?> ...fields) {
		for (SingularAttribute<SelectTable, ?> f : fields) {
			this.desc(f);
		}
		return this;
	}
	
	
}
