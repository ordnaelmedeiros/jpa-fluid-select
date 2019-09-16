package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import java.util.function.Consumer;

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

	/**
	 * multiselect
	 * @param consumer 
	 * @return back
	 */
	default Select<SelectTable> fields(Consumer<Fields<SelectTable>> consumer) {
		return this.getSelect().fields(consumer);
	}
	
}
