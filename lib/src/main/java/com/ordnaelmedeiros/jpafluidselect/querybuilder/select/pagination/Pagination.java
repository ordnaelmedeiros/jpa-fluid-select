package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.pagination;

import java.util.List;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;

public class Pagination<SelectTable> {

	private Select<SelectTable> select;

	private Integer numRows = 50;

	private Integer page = 1;
	
	public Pagination(Select<SelectTable> select) {
		this.select = select;
		//this.createPage();
//		if (this.select.getMaxResults()!=null) {
//			this.maxResults = this.select.getMaxResults();
//		}
	}
	
	public Pagination<SelectTable> numRows(Integer numRows) {
		this.numRows = numRows;
		return this;
	}
	
	public Pagination<SelectTable> page(Integer page) {
		this.page = page;
		return this;
	}
	
	private <T> PaginationResult<T> createPage() {
		
		PaginationResult<T> result = new PaginationResult<>();
		
		if (this.numRows==null || this.numRows<1) {
			this.numRows = 50;
		}
		if (this.page==null || this.page<1) {
			this.page = 1;
		}
		
		result.setPageNumber(this.page);
		result.setPageSize(this.numRows);
		
		Long total = 0l;
		if (select.isDistinct()) {
			total = select.countDistinct();
		} else {
			total = select.count();
		}
		result.setTotalRows(total);
		
		long lastPage = (total/numRows);
		if (total%numRows>0) {
			lastPage++;
		}
		result.setLastPage((int)lastPage);
		
		return result;
		
	}
	
	public PaginationResult<SelectTable> getResultList() {
		
		PaginationResult<SelectTable> result = this.createPage();
		
		if (result.getTotalRows()>0) {
			List<SelectTable> results = select
					.firstResult((this.page-1)*numRows)
					.maxResults(numRows)
					.getResultList();
			
			result.setData(results);
		}
		
		this.lastRequest = result;
		
		return result;
		
	}
	
	public PaginationResult<Object[]> getResultObjects() {

		PaginationResult<Object[]> result = this.createPage();
		
		if (result.getTotalRows()>0) {
			List<Object[]> results = select
					.firstResult((this.page-1)*numRows)
					.maxResults(numRows)
					.getResultObjects();
			
			result.setData(results);
		}
		
		this.lastRequest = result;
		
		return result;
		
	}
	
	public <T> PaginationResult<T> getResultListByConstructor(Class<T> klass) {

		PaginationResult<T> result = this.createPage();
		
		if (result.getTotalRows()>0) {
			List<T> results = select
					.firstResult((this.page-1)*numRows)
					.maxResults(numRows)
					.getResultListByConstructor(klass);
			
			result.setData(results);
		}
		
		this.lastRequest = result;
		
		return result;
		
	}

	public <T> PaginationResult<T> getResultListByReflect(Class<T> klass) {

		PaginationResult<T> result = this.createPage();
		
		if (result.getTotalRows()>0) {
			List<T> results = select
					.firstResult((this.page-1)*numRows)
					.maxResults(numRows)
					.getResultListByReflect(klass);
			
			result.setData(results);
		}
		
		this.lastRequest = result;
		
		return result;
		
	}
	
	public <T> PaginationResult<T> getResultList(Class<T> klass) {

		PaginationResult<T> result = this.createPage();
		
		if (result.getTotalRows()>0) {
			List<T> results = select
					.firstResult((this.page-1)*numRows)
					.maxResults(numRows)
					.getResultList(klass);
			
			result.setData(results);
		}
		
		this.lastRequest = result;
		
		return result;
		
	}


	private PaginationResult<?> lastRequest = null;
	public boolean next() {
		if (lastRequest==null) {
			lastRequest = new PaginationResult<>();
			return true;
		} else {
			if (this.page<lastRequest.getLastPage()) {
				this.page++;
				return true;
			} else {
				return false;
			}
		}
	}
	
}
