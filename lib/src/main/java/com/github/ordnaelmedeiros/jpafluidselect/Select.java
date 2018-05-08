package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.function.Consumer;

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
	
	public <T> From<T, T> from(Class<T> classFrom) {
		return new From<>(this, classFrom, classFrom);
	}
	
	public <T> From<T, Long> fromCount(Class<T> classFrom) {
		return new From<>(this, classFrom, Long.class).count();
	}
	
	public Select extractBuilder(Consumer<CriteriaBuilder> b) {
		b.accept(this.builder);
		return this;
	}
	
}
