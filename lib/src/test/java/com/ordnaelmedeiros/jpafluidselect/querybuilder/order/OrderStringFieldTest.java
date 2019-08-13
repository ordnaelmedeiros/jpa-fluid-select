package com.ordnaelmedeiros.jpafluidselect.querybuilder.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilderTestBase;

public class OrderStringFieldTest extends QueryBuilderTestBase {
	
	@Test
	public void orderByFieldsInOriginTable() {
		
		List<People> result = queryBuilder
			.select(People.class)
			.order()
				.desc("status")
				.asc("id")
			//.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(7));
		
		assertThat(result.get(0).getId(), equalTo(3l));
		
		assertThat(result.get(1).getId(), equalTo(1l));
		assertThat(result.get(2).getId(), equalTo(2l));
		assertThat(result.get(3).getId(), equalTo(4l));
		
	}
	
	@Test
	public void orderByFieldsInJoinTable() {
		
		List<People> result = queryBuilder
			.select(People.class)
			.order()
				.asc("address.id")
			//.print()
			.getResultList()
			;
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(7));
		
		assertThat(result.get(0).getId(), equalTo(1l));
		assertThat(result.get(1).getId(), equalTo(2l));
		assertThat(result.get(2).getId(), equalTo(3l));
		assertThat(result.get(3).getId(), equalTo(4l));
		
	}
	
	@Test
	public void orderByFieldsInAlias() {
		
		List<People> result = queryBuilder
			.select(People.class)
			.leftJoin("address")
				.leftJoin("country").alias("cc1")
				.end()
			.end()
			.leftJoin("address")
				.leftJoin("country").alias("cc2")
					.on()
						.field("id").gt(100l)
					.end()
				.end()
			.end()
//			.fields()
//				.add("id")
//				.add("name")
//				.add("address.country.id")
			.order()
				.field("id").fromAlias("cc1").desc()
				.asc("id")
			//.print()
			.getResultList()
			;
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(7));
		
		assertThat(result.get(0).getId(), equalTo(4l));
		assertThat(result.get(1).getId(), equalTo(6l));
		assertThat(result.get(2).getId(), equalTo(7l));
		assertThat(result.get(3).getId(), equalTo(1l));
		assertThat(result.get(4).getId(), equalTo(2l));
		assertThat(result.get(5).getId(), equalTo(3l));
		assertThat(result.get(6).getId(), equalTo(5l));
		
	}
	
}
