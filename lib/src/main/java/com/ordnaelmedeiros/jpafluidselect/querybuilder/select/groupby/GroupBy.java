package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.groupby;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidEnd;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidSelect;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidWhere;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Container;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Content;

public class GroupBy<SelectTable> extends Container
		implements
			FluidEnd<Select<SelectTable>>,
			FluidSelect<SelectTable>,
			FluidWhere<SelectTable> {

	private String aliasOrigin;
	
	private Select<SelectTable> select;

	public GroupBy(Select<SelectTable> select, String aliasOrigin) {
		this.aliasOrigin = aliasOrigin;
		this.setPrefix("GROUP BY ");
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

	public GroupBy<SelectTable> add(String field) {
		this.add(new Content(this.aliasOrigin+"."+field));
		return this;
	}

}
