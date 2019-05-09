package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

import lombok.Getter;

public class FSelect {

	public static boolean noResultException = false;
	
	@Getter
	private EntityManager em;
	
	@Getter
	private CriteriaBuilder builder;
	
	public FSelect(EntityManager em) {
		this.em = em;
		this.builder = this.em.getCriteriaBuilder();
	}
	
	public <T> FFrom<T, T> from(Class<T> classFrom) {
		return new FFrom<>(this, classFrom, classFrom);
	}
	
	public <T> FFrom<T, Long> fromCount(Class<T> classFrom) {
		return new FFrom<>(this, classFrom, Long.class).count();
	}
	
	public <T> FFrom<T, Object[]> fromCustomFields(Class<T> classFrom) {
		return new FFrom<>(this, classFrom, Object[].class);
	}
	
	public <T, E> FFrom<T, E> fromCustomFields(Class<T> classFrom, Class<E> classRetorno) {
		return new FFrom<>(this, classFrom, classRetorno);
	}
	
	public FSelect extractBuilder(Consumer<CriteriaBuilder> b) {
		b.accept(this.builder);
		return this;
	}
	
}
