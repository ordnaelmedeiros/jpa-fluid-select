package com.ordnaelmedeiros.jpafluidselect.querybuilder.results;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilderTestBase;

public class ReturnTest extends QueryBuilderTestBase {
	
	@Test
	public void singleReturnOrigin() {
		
		try {
				
			People result = queryBuilder
				.select(People.class)
				.where()
					.field("id").eq(1l)
				.getSingleResult();
			
			assertThat(result, notNullValue());
			
			assertThat(result.getId(), equalTo(1l));
			assertThat(result.getName(), equalTo("Leandro"));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void listReturnOrigin() {
		
		try {
			
			List<People> result = queryBuilder
				.select(People.class)
				.where()
					.field("id").in(1l, 2l)
				.order()
					.asc("id")
				.getResultList();
			
			assertThat(result, notNullValue());
			assertThat(result.size(), equalTo(2));
			
			assertThat(result.get(0).getId(), equalTo(1l));
			assertThat(result.get(0).getName(), equalTo("Leandro"));
			
			assertThat(result.get(1).getId(), equalTo(2l));
			assertThat(result.get(1).getName(), equalTo("Ivana"));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void singleReturnFieldEntity() {
		
		try {
				
			Address result = queryBuilder
				.select(People.class)
				.fields()
					.add("address")
				.where()
					.field("id").eq(1l)
				.getSingleResult(Address.class);
			
			assertThat(result, notNullValue());
			
			assertThat(result.getId(), equalTo(1l));
			assertThat(result.getStreet(), equalTo("One"));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void listReturnFieldEntity() {
		
		try {
			
			List<Address> result = queryBuilder
				.select(People.class)
				.fields()
					.add("address")
				.where()
					.field("id").in(1l, 2l)
				.order()
					.asc("id")
				.getResultList(Address.class);
			
			assertThat(result, notNullValue());
			assertThat(result.size(), equalTo(2));
			
			assertThat(result.get(0).getId(), equalTo(1l));
			assertThat(result.get(0).getStreet(), equalTo("One"));
			
			assertThat(result.get(1).getId(), equalTo(2l));
			assertThat(result.get(1).getStreet(), equalTo("Two"));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void singleReturnFieldPrimitive() {
		
		try {
				
			String result = queryBuilder
				.select(People.class)
				.fields()
					.add("name")
				.where()
					.field("id").eq(1l)
				.getSingleResult(String.class);
			
			assertThat(result, notNullValue());
			assertThat(result, equalTo("Leandro"));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void listReturnFieldPrimitive() {
		
		try {
			
			List<String> result = queryBuilder
				.select(People.class)
				.fields()
					.add("name")
				.where()
					.field("id").in(1l, 2l)
				.order()
					.asc("id")
				.getResultList(String.class);
			
			assertThat(result, notNullValue());
			assertThat(result.size(), equalTo(2));
			
			assertThat(result.get(0), equalTo("Leandro"));
			assertThat(result.get(1), equalTo("Ivana"));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void singleReturnFieldByConstructor() {
		
		try {
				
			People result = queryBuilder
				.select(People.class)
				.fields()
					.add("id")
					.add("name")
				.where()
					.field("id").eq(1l)
				.getSingleResultByConstructor(People.class);
			
			assertThat(result, notNullValue());
			assertThat(result.getId(), equalTo(1l));
			assertThat(result.getName(), equalTo("Leandro"));
			assertThat(result.getCreated(), nullValue());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void listReturnFieldByConstructor() {
		
		try {
			
			List<People> result = queryBuilder
				.select(People.class)
				.fields()
					//.add("id")
					.add("name")
				.where()
					.field("id").in(1l, 2l)
				.order()
					.asc("id")
				.getResultListByConstructor(People.class);
			
			assertThat(result, notNullValue());
			assertThat(result.size(), equalTo(2));
			
			//assertThat(result.get(0).getId(), equalTo(1l));
			assertThat(result.get(0).getName(), equalTo("Leandro"));
			assertThat(result.get(0).getCreated(), nullValue());
			
			//assertThat(result.get(1).getId(), equalTo(2l));
			assertThat(result.get(1).getName(), equalTo("Ivana"));
			assertThat(result.get(1).getCreated(), nullValue());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	

	@Test
	public void singleReturnFieldByTransform() {
		
		try {
				
			PeopleDTO result = queryBuilder
				.select(People.class)
				.fields()
					.field(People_.id).alias("peopleId")
					.field("name").alias("peopleName")
				.where()
					.field("id").eq(1l)
				.getSingleResultByReflect(PeopleDTO.class);
			
			assertThat(result, notNullValue());
			assertThat(result.getPeopleId(), equalTo(1l));
			assertThat(result.getPeopleName(), equalTo("Leandro"));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void listReturnFieldByTransform() {
		
		try {
			
			List<PeopleDTO> result = queryBuilder
				.select(People.class)
				.fields()
					.field("id").alias("peopleId")
					.field("name").alias("peopleName")
				.where()
					.field("id").in(1l, 2l)
				.order()
					.asc("id")
				.getResultListByReflect(PeopleDTO.class);
			
			assertThat(result, notNullValue());
			assertThat(result.size(), equalTo(2));
			
			assertThat(result.get(0).getPeopleId(), equalTo(1l));
			assertThat(result.get(0).getPeopleName(), equalTo("Leandro"));
			
			assertThat(result.get(1).getPeopleId(), equalTo(2l));
			assertThat(result.get(1).getPeopleName(), equalTo("Ivana"));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
}
