package com.github.ordnaelmedeiros.jpafluidselect;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

import lombok.Getter;

public class Select {

	@Getter
	private EntityManager em;
	
	@Getter
	private CriteriaBuilder builder;
	
	public Select(EntityManager em) {
		this.em = em;
		this.builder = this.em.getCriteriaBuilder();
	}
	
	public <T> From<T> from(Class<T> classe) {
		return new From<>(this, classe);
	}
	
	public Select extractToBuilder(ContainerSelect c) {
		c.setBuilder(builder);
		return this;
	}
	
}
