package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

public interface FluidWhere<SelectTable> {
	
	public Select<SelectTable> getSelect();
	
	default Operations<Select<SelectTable>,SelectTable,SelectTable> where() {
		return this.getSelect().where();
	}

}
