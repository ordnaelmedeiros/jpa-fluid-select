package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;

public interface FluidPagination<SelectTable> {
	
	public Select<SelectTable> getSelect();
	
	/**
	 * Set the maximum number of results to retrieve
	 * @param maxResults
	 * @return SELECT
	 */
	default Select<SelectTable> maxResults(int maxResults) {
		return this.getSelect().maxResults(maxResults);
	}
	
	
}
