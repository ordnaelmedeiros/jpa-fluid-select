package com.ordnaelmedeiros.jpafluidselect.querybuilder.order.string;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;

public class OrderByStringTest {
	
	private static EntityManagerFactory emf;
	public EntityManager em;
	public QueryBuilder queryBuilder;
	
	private final int maxSize = 4;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(new ObjString(1, "A"));
		em.persist(new ObjString(2, "  A"));
		em.persist(new ObjString(3, "a"));
		em.persist(new ObjString(4, "A"));
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void text_1_2_asc() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field("id").in(1, 2)
			.order()
				.asc(ObjString_.text)
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0).getId(), is(2));
		assertThat(result.get(1).getId(), is(1));
		
	}
	
	@Test
	public void textTrim_1_2_asc() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.field(ObjString_.text).trim().add()
			.where()
				.field("id").in(1, 2)
			.order()
				.field(ObjString_.text).trim().asc()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(1).getId(), is(2));
		
	}
	
	@Test
	public void textUpper_3_4_asc() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.field(ObjString_.text).upper().add()
			.where()
				.field("id").in(3, 4)
			.order()
				.field(ObjString_.text).upper().asc()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0).getId(), is(3));
		assertThat(result.get(1).getId(), is(4));
		
	}
	
	@Test
	public void textLower_3_4_asc() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.field(ObjString_.text).lower().add()
			.where()
				.field("id").in(3, 4)
			.order()
				.field(ObjString_.text).lower().asc()
				.desc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0).getId(), is(4));
		assertThat(result.get(1).getId(), is(3));
		
	}

	@Test
	public void textLength_1_2_asc() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.field(ObjString_.text).length().add()
			.where()
				.field("id").in(1, 2)
			.order()
				.field(ObjString_.text).length().asc()
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0)[0], is(1));
		assertThat(result.get(0)[1], is(1));
		assertThat(result.get(1)[0], is(2));
		assertThat(result.get(1)[1], is(3));
		
	}
	
}
