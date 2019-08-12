package com.ordnaelmedeiros.jpafluidselect.querybuilder.example;

import static org.junit.Assert.fail;

import java.util.StringJoiner;
import java.util.stream.Stream;

import org.junit.Test;

import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilderTestBase;

public class ExampleTest extends QueryBuilderTestBase {
	
	@Test
	public void selectTest1() {
		
		try {
				
			//List<People> list = 
			queryBuilder
				.select(People.class)
				.fields()
					//.field("id").end()
					.add("name")
					.add("created")
					.count()
					.sum("id")
					
				.leftJoin("address")
					.on()
						.field("id").gt(0l)
					.end()
					.leftJoin("country")
					.end()
				.end()
				.leftJoin("phones")
					.on()
						.field("id").gt(0l)
					.end()
				.end()
				.where()
					.field("id").gt(0l)
					.field("id").gt(0l)
					.orGroup()
						.field("id").gt(0l)
						.field("id").gt(5l)
				.order()
					.asc("name")
					.asc("created")
					//.desc("id")
				.groupBy()
					.add("name")
					.add("created")
					//.add("id")
				//.getResultList();
				.getResultObjects()
				.stream()
				.forEach(o -> {
					StringJoiner str = new StringJoiner(" \t- ");
					Stream.of(o).map(Object::toString).forEach(str::add);
					System.out.println(str.toString());
					
				})
				;
			
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
