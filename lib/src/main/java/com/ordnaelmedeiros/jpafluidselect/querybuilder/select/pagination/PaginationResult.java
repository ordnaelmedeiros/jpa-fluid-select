package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.pagination;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaginationResult<T> {
	
	/**
	 * Page number 
	 * @return NUMBER
	 */
	private Integer pageNumber;
	
	/**
	 * Number of lines on the page
	 * @return SIZE
	 */
	private Integer pageSize;
	
	/**
	 * Last page number
	 * @return LASTPAGE
	 */
	private Integer lastPage;
	
	/**
	 * Total rows in select
	 * @return TOTAL
	 */
	private Long totalRows;
	
	private List<T> data = new ArrayList<>();
	
}
