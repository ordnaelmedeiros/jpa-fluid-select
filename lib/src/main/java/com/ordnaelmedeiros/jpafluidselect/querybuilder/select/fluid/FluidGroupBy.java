package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.groupby.GroupBy;

public interface FluidGroupBy<SelectTable> {
	
	public Select<SelectTable> getSelect();
	
	default GroupBy<SelectTable> group() {
		return this.getSelect().group();
	}

}
