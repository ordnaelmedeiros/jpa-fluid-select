package com.github.ordnaelmedeiros.jpafluidselect;

import javax.persistence.criteria.CriteriaBuilder;

import lombok.Setter;

public class ContainerSelect {

	@Setter
	private CriteriaBuilder builder;
	
	public CriteriaBuilder builder() {
		return builder;
	}
	
}
