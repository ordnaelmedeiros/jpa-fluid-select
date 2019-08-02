package com.ordnaelmedeiros.jpafluidselect.querybuilder.example;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilderTestBase;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;

import lombok.Getter;
import lombok.Setter;

public class ExampleTest extends QueryBuilderTestBase {
	
	@Test
	public void selectAll() {
		
		List<People> list = queryBuilder
			.select(People.class)
			.getResultList();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), equalTo(7));
		//list.forEach(System.out::println);
		
	}
	
	@Test
	public void selectWhere() {
		
		List<People> list = queryBuilder
			.select(People.class)
			.where()
				.field(People_.id).eq(1l)
			.getResultList();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), equalTo(1));
		assertThat(list.get(0).getId(), equalTo(1l));
		//list.forEach(System.out::println);
		
	}
	
	@Test
	public void selectWhereNot() {
		
		List<People> list = queryBuilder
			.select(People.class)
			.where()
				.field(People_.id).lt(4l)
				.field(People_.id).not().eq(1l)
			.order()
				.field(People_.id).asc()
			.getResultList();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), equalTo(2));
		assertThat(list.get(0).getId(), equalTo(2l));
		assertThat(list.get(1).getId(), equalTo(3l));
		//list.forEach(System.out::println);
		
	}
	
	@Test
	public void selectOrdeBy() {
		
		List<People> list = queryBuilder
			.select(People.class)
			.order()
				.field(People_.id).desc()
			.getResultList();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), equalTo(7));
		assertThat(list.get(0).getId(), equalTo(7l));
		assertThat(list.get(1).getId(), equalTo(6l));
		//list.forEach(System.out::println);
		
	}
	
	@Test
	public void selectCount() {
		
		Long count = queryBuilder
			.select(People.class)
			.where()
				.field(People_.id).lt(4l)
			.count();
		
		assertThat(count, notNullValue());
		assertThat(count, equalTo(3l));
		//list.forEach(System.out::println);
		
	}
	
	@Test
	public void selectCountAndResult() {
		
		Select<People> select = queryBuilder
			.select(People.class)
			.where()
				.field(People_.id).lt(4l)
			.order()
				.field(People_.id).desc()
			.getSelect();
			
		Long count = select.count();
		List<People> list = select.getResultList();
		
		//list.forEach(System.out::println);
		//System.out.println("Count: " + count);
		
		assertThat(count, notNullValue());
		assertThat(count, equalTo(3l));
		assertThat(list.get(0).getId(), equalTo(3l));
		assertThat(list.get(1).getId(), equalTo(2l));
		assertThat(list.get(2).getId(), equalTo(1l));
		//list.forEach(System.out::println);
		
	}
	
}
