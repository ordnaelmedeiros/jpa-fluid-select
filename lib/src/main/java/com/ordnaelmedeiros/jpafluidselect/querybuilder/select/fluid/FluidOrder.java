package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.Order;

public interface FluidOrder<SelectTable> {
	
	public Select<SelectTable> getSelect();
	
	default Order<SelectTable> order() {
		return this.getSelect().order();
	}

}
