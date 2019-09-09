package com.ordnaelmedeiros.jpafluidselect.querybuilder.results;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilderTestBase;

public class PrintTest extends QueryBuilderTestBase {
	
	@Test
	public void printByFields() {
		
		try {
				
			String result = queryBuilder
				.select(People.class)
				.fields()
					.add("id")
					.add("name")
				.where()
					.field("id").in(1l, 2l)
				.order()
					.asc("id")
				.print()
				.resultToString();
			
			assertThat(result, notNullValue());
			assertThat(result, not(equalTo("")));
			
			assertThat(result, containsString("people0_.id"));
			assertThat(result, containsString("people0_.name"));
			assertThat(result, containsString("Leandro"));
			assertThat(result, containsString("Ivana"));
			
		} catch (Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void printByObjects() {
		
		try {
				
			String result = queryBuilder
				.select(People.class)
				.where()
					.field("id").in(1l, 2l)
				.order()
					.asc("id")
				.print()
				.resultToString();
			
			assertThat(result, notNullValue());
			assertThat(result, not(equalTo("")));
			
			//assertThat(result, containsString("people0_.id"));
			//assertThat(result, containsString("people0_.name"));
			//assertThat(result, containsString("Leandro"));
			//assertThat(result, containsString("Ivana"));
			
		} catch (Exception e) {
			fail();
		}
		
	}
		
}
