package com.ordnaelmedeiros.jpafluidselect.querybuilder.where;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.Status;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilderTestBase;

public class WhereTest extends QueryBuilderTestBase {
	
	@Test
	public void selectTest1() {
		
		try {
				
			//List<People> list = 
			queryBuilder
				.select(People.class)
				.fields()
					.add("id")
					.add("name")
				.where()
					.field("id").gt(0l)
					.field("status").eq(Status.ACTIVE)
				.print();
			
			//assertThat(list, notNullValue());
			//assertThat(list.size(), equalTo(7));
			//list.forEach(System.out::println);
			
			System.out.println("Liiiiiiiiist");

		} catch (Exception e) {
			e.printStackTrace();
			fail();
			//throw e;
		}
		
		fail("Siccess!!!");
		
	}
		
}
