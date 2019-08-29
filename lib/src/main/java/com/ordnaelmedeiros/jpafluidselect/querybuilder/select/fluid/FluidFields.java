package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.Fields;

public interface FluidFields<SelectTable> {
	
	public Select<SelectTable> getSelect();
	
	/**
	 * multiselect
	 * @return back
	 */
	default Fields<SelectTable> fields() {
		return this.getSelect().fields();
	}

}
