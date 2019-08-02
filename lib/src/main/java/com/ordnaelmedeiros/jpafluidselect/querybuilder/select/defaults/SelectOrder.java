package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.defaults;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Order;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;

public interface SelectOrder<T> {

	public Select<T> getSelect();
	
	default Order<T> order() {
		return this.getSelect().order();
	}
	
}
