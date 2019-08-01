package com.ordnaelmedeiros.jpafluidselect.querybuilder.example;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilderTestBase;

public class ExampleTest extends QueryBuilderTestBase {
	
	@Test
	public void selectAll() {
		
		List<People> list = queryBuilder
			.select(People.class)
			.getResultList();
		
		assertThat(list, notNullValue());
		//list.forEach(System.out::println);
		
	}
	
	@Test
	public void where() {
		
		List<People> list = queryBuilder
			.select(People.class)
			.where()
				.field(People_.id).eq(1l)
			.getResultList();
		
		assertThat(list, notNullValue());
		//list.forEach(System.out::println);
		
	}
	
}
