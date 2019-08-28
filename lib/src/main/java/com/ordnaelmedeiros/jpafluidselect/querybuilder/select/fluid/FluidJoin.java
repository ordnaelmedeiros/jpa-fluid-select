package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join.Join;

public interface FluidJoin<SelectTable> {
	
	public Select<SelectTable> getSelect();
	
	default Join<Select<SelectTable>, SelectTable, ?> leftJoin(String field) {
		return this.getSelect().leftJoin(field);
	}

}
