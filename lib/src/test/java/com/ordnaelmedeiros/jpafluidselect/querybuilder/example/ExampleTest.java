package com.ordnaelmedeiros.jpafluidselect.querybuilder.example;

import static java.time.Month.SEPTEMBER;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

import org.junit.Test;

import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address_;
import com.github.ordnaelmedeiros.jpafluidselect.models.Country;
import com.github.ordnaelmedeiros.jpafluidselect.models.Country_;
import com.github.ordnaelmedeiros.jpafluidselect.models.DTO;
import com.github.ordnaelmedeiros.jpafluidselect.models.DTO2;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;
import com.github.ordnaelmedeiros.jpafluidselect.models.Phone_;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilderTestBase;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.Ref;

public class ExampleTest extends QueryBuilderTestBase {
	
	@Test
	public void all() {
		
		List<People> list = queryBuilder
			.select(People.class)
			.getResultList();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(7));
		
	}
	
	@Test
	public void where() {
		
		People p = queryBuilder
			.select(People.class)
			.where()
				.field(People_.id).eq(1l)
			.getSingleResult();
		
		assertThat(p, notNullValue());
		
	}
	
	@Test
	public void orderBy() {
		
		List<People> list1 = queryBuilder
			.select(People.class)
			.order().desc(People_.id)
			.getResultList();
		
		List<People> list2 = queryBuilder
			.select(People.class)
			.order()
				.asc(People_.name)
				.desc(People_.id)
			.getResultList();
		
		assertThat(list1, notNullValue());
		assertThat(list2, notNullValue());
		
	}
	
	@Test
	public void count() {
		
		Long count = queryBuilder
			.select(People.class)
			.fields().count()
			.where()
				.field(People_.name).ilike("%le%")
			.getSingleResult(Long.class);
		
		assertThat(count, is(2l));
		
	}
	
	@Test
	public void not() {
		
		List<People> list = queryBuilder
			.select(People.class)
			.where()
				.field(People_.id).not().in(1l, 2l)
			.getResultList();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(5));
		
	}
	
	@Test
	public void conditional() {
		
		boolean isUpdate = false; 
		
		List<People> list = queryBuilder
			.select(People.class)
			.where(w -> {
				if (isUpdate) {
					w.field(People_.id).ne(1l);
				}
				w.field(People_.name).eq("Leandro");
			})
			.getResultList();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(2));
		
	}
	
	@Test
	public void temporal() {
		
		List<People> list1 = queryBuilder
			.select(People.class)
			.where()
				.field(People_.created).year().eq(2017)
			.getResultList();
		
		assertThat(list1, notNullValue());
		assertThat(list1.size(), is(7));
		

		List<People> list2 = queryBuilder
			.select(People.class)
			.where()
				.field(People_.created).cast(LocalDate.class).eq(LocalDate.of(1986, SEPTEMBER, 17))
			.getResultList();
		
		assertThat(list2, notNullValue());
		assertThat(list2.size(), is(0));
		
	}
	
	@Test
	public void whereGroup() {
		
		List<People> list = queryBuilder
			.select(People.class)
			.where()
				.field(People_.name).like("%e%")
				// and (
				.orGroup()
					.field(People_.id).eq(1l)
					// or
					.field(People_.id).eq(2l)
					// or
					.field(People_.id).eq(3l)
				// )
				.end()
			.getResultList();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(2));
		
	}
	
	@Test
	public void join() {
		
		List<People> list1 = queryBuilder
				.select(People.class)
				.innerJoin(People_.address)
					.innerJoin(Address_.country)
						.on()
							.field(Country_.name).eq("Florida")
				.getResultList();
		
		assertThat(list1, notNullValue());
		assertThat(list1.size(), is(4));
		
		List<People> list2 = queryBuilder
				.select(People.class)
				.innerJoin(People_.address)
					.on()
						.orGroup()
							.field(Address_.street).eq("One")
							.field(Address_.street).eq("9999")
						.end()
					.end()
				.end()
				.leftJoin(People_.phones)
					.on()
						.field(Phone_.number).eq("123")
					.end()
				.where()
					.field(People_.name).ilike("%a%")
				.getResultList();
		
		assertThat(list2, notNullValue());
		assertThat(list2.size(), is(2));
		
	}
	
	@Test
	public void customFields() {
		
		Ref<Address> joinAdress = new Ref<>();
		
		List<Object[]> list = queryBuilder
				.select(People.class)
				.leftJoin(People_.address).ref(joinAdress) //.extractJoin(j -> this.joinAdress = j)
				.fields() // fields returns
					.add(People_.id)
					.add(People_.name)
					.add(joinAdress.field(Address_.street))
				.where()
					.field(People_.id).in(1l, 2l)
				.order()
					.asc(People_.id)
				.getResultObjects();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(2));
		
		Long maxId = queryBuilder
				.select(People.class)
				.fields()
					.field(People_.id).max().add()
				.getSingleResult(Long.class);
		
		assertThat(maxId, is(7l));
		
		List<Address> listAddress = queryBuilder
				.select(People.class)
				.fields()
					.add(People_.address)
				.where()
					.field(People_.id).eq(1l)
				.order()
					.asc(People_.address)
				.getResultList(Address.class);
		
		assertThat(listAddress, notNullValue());
		assertThat(listAddress.size(), is(1));
		
	}
	
	@Test
	public void customFieldsTransform() {
		
		Ref<Address> joinAdress = new Ref<>();
		
		DTO dto = queryBuilder
				.select(People.class)
				.leftJoin(People_.address).ref(joinAdress)//.extractJoin(j -> this.joinAdress = j)
				.fields()
					.add(People_.name)
					.add(joinAdress.field(Address_.street))
				.where()
					.field(People_.id).eq(1l)
				.getSingleResultByConstructor(DTO.class);
		
		assertThat(dto, notNullValue());
		assertThat(dto.getPeopleName(), is("Leandro"));
		
		DTO2 dto2 = queryBuilder
				.select(People.class)
				.leftJoin(People_.address).ref(joinAdress)//.extractJoin(j -> this.joinAdress = j)
				.fields()
					.field(People_.name).alias("peopleName")
					.field(joinAdress.field(Address_.street)).alias("peopleStreet")
				.where()
					.field(People_.id).eq(1l)
				.getSingleResultByReflect(DTO2.class);
		
		assertThat(dto2, notNullValue());
		assertThat(dto2.getPeopleName(), is("Leandro"));
		
	}
	
	@Test
	public void groupBy() {
		
		Ref<Address> joinAddress = new Ref<>();
		
		List<Object[]> list = queryBuilder
				.select(People.class)
				.leftJoin(People_.address).ref(joinAddress)
				.fields()
					.add(joinAddress.field(Address_.street))
					.field(People_.id).count().add()
				.group()
					.add(joinAddress.field(Address_.street))
				.order()
					.asc(joinAddress.field(Address_.street))
				.getResultObjects();

			list.stream().forEach(o -> {
				System.out.println(String.format("%10s count: %02d", o));
			});
		
	}
	
	@Test
	public void distinct() {
		
		List<Object[]> list = queryBuilder
				.select(Address.class)
				.distinct()
				.fields()
					.add(Address_.street)
				.order().asc(Address_.street)
				.getResultObjects();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(4));
		
	}
	
	@Test
	public void lambdaExpressions() {
		
		Ref<Country> refCoutry = new Ref<>();
		
		List<People> list = queryBuilder
				.select(People.class)
				.innerJoin(People_.address, jAddress -> {
					jAddress.innerJoin(Address_.country, jCountry -> {
						
						jCountry.ref(refCoutry).on(on -> {
							on
								.field(Country_.name).ilike("f%")
								.field("name").like("%a");
						});
						
					});
				})
				.where(w -> {
					w.orGroup()
						.field(People_.id).eq(1l)
						.field(People_.id).eq(2l)
						;
				})
				.order(o -> {
					o.asc(People_.id);
				})
				.getResultList();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(2));
		
	}
	
	@Test
	public void attributeName() {
		
		List<Object[]> list = queryBuilder
				.select(People.class)
				.fields()
					.add("address.street")
					.field("id").count().add()
				.group()
					.add("address.street")
				.order()
					.asc("address.street")
				.print()
				.getResultObjects();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(4));
		assertThat(list.get(0)[1], is(2l));
		assertThat(list.get(1)[1], is(1l));
		assertThat(list.get(2)[1], is(2l));
		assertThat(list.get(3)[1], is(2l));
		
	}
	
	@Test
	public void limit() {
		
		List<People> list = queryBuilder
			.select(People.class)
			.order().asc(People_.id)
			.maxResults(3)
			.getResultList();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(3));
		assertThat(list.get(0).getId(), is(1l));
		assertThat(list.get(1).getId(), is(2l));
		assertThat(list.get(2).getId(), is(3l));
		
		People people = queryBuilder
			.select(People.class)
			.order().asc(People_.id)
			.maxResults(1)
			.getSingleResult();
		
		assertThat(people, notNullValue());
		assertThat(people.getId(), is(1l));
		
	}
	
	@Test
	public void function() {
		
		List<Object[]> list = queryBuilder
			.select(People.class)
			.fields()
				.add(People_.id)
				.add(People_.created)
				.field(People_.created).function("TO_CHAR(:field, 'dd/MM/yyyy')").add()
			.where()
				.field(People_.id).in(1l, 2l)
			.order()
				.asc(People_.id)
			.print()
			.getResultObjects();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(2));
		assertThat(list.get(0)[0], is(1l));
		assertThat(list.get(1)[0], is(2l));
		
		//<a href="https://vladmihalcea.com/hibernate-sql-function-jpql-criteria-api-query/">hibernate-sql-function-jpql</a>
		
	}

	@Test
	public void sql() {
		
		Ref<People> refPeople = new Ref<>();
		List<Object[]> list = queryBuilder
			.select(People.class).ref(refPeople)
			.fields()
				.add(People_.id)
				.add(People_.name)
				.jpql(()->"CAST(id as java.lang.String)")
			.where()
				.jpql(()-> {
					String txt = "";
					txt += ":people.id = 1";
					txt += "OR :people.id = 2";
					return txt.replace(":people", refPeople.getAlias());
				})
			.order()
				.asc(People_.id)
			.print()
			.getResultObjects();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(2));
		assertThat(list.get(0)[0], is(1l));
		assertThat(list.get(1)[0], is(2l));
	
	}
	
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
					.field("id").sum().add()
					
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
				.group()
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
		
		assertTrue(true);
		//fail("Siccess!!!");
		
	}
		
}
