package com.ordnaelmedeiros.jpafluidselect.querybuilder.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilderTestBase;

public class OrderByAttribute extends QueryBuilderTestBase {

	@Test
	public void orderByAttribute() {
		
		List<Object[]> result = queryBuilder
			.select(People.class)
			.fields()
				.add(People_.id)
				.add(People_.name)
				.add(People_.created)
			.order()
				.asc(People_.created)
				.desc(People_.id)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(7));
		assertThat(result.get(0)[0], equalTo(3l));
		assertThat(result.get(1)[0], equalTo(2l));
		assertThat(result.get(2)[0], equalTo(1l));
		
		assertThat(result.get(3)[0], equalTo(4l));
		assertThat(result.get(4)[0], equalTo(5l));
		assertThat(result.get(5)[0], equalTo(6l));
		
		assertThat(result.get(6)[0], equalTo(7l));
		
	}
	
	@Test
	public void orderByCast() {
		
		List<Object[]> result = queryBuilder
			.select(People.class)
			.fields()
				.add(People_.id)
				.add(People_.name)
				.add(People_.created)
				.field(People_.created).cast(LocalDate.class).add()
			.order()
				.field(People_.created).cast(LocalDate.class).asc()
				.desc(People_.id)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(7));
		assertThat(result.get(0)[0], equalTo(5l));
		assertThat(result.get(1)[0], equalTo(4l));
		assertThat(result.get(2)[0], equalTo(3l));
		assertThat(result.get(3)[0], equalTo(2l));
		assertThat(result.get(4)[0], equalTo(1l));
		
		assertThat(result.get(5)[0], equalTo(6l));
		assertThat(result.get(6)[0], equalTo(7l));
		
	}
	
	@Test
	public void orderByExtract() {
		
		/*List<Object[]> result = */queryBuilder
			.select(People.class)
			.fields()
				.add(People_.id)
				.add(People_.name)
				.add(People_.created)
				.field(People_.created).year().add()
				.field(People_.created).month().add()
				.field(People_.created).day().add()
			.order()
				.field(People_.created).year().asc()
				.field(People_.id).asc()
			.print();
			//.getResultObjects();
		
		/*
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(7));
		assertThat(result.get(0)[0], equalTo(5l));
		assertThat(result.get(1)[0], equalTo(4l));
		assertThat(result.get(2)[0], equalTo(3l));
		assertThat(result.get(3)[0], equalTo(2l));
		assertThat(result.get(4)[0], equalTo(1l));
		
		assertThat(result.get(5)[0], equalTo(6l));
		assertThat(result.get(6)[0], equalTo(7l));
		*/
	}
	
}
