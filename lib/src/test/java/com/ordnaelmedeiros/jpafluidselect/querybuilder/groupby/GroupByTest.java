package com.ordnaelmedeiros.jpafluidselect.querybuilder.groupby;

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
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjString;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjString_;

public class GroupByTest {
	
	private static EntityManagerFactory emf;
	public EntityManager em;
	public QueryBuilder queryBuilder;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.createQuery("delete from ObjString").executeUpdate();
		
		em.persist(new ObjString(1, "A"));
		em.persist(new ObjString(2, "a"));
		em.persist(new ObjString(3, "C"));
		em.persist(new ObjString(4, "C"));
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void count() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.field(ObjString_.text).upper().add()
				.count()
			.group()
				.field(ObjString_.text).upper().add()
			.order()
				.field(ObjString_.text).upper().asc()
			//.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0)[0], is("A"));
		assertThat(result.get(0)[1], is(2l));
		assertThat(result.get(1)[0], is("C"));
		assertThat(result.get(1)[1], is(2l));
		
	}
	
	@Test
	public void countField() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.field(ObjString_.text).upper().add()
				.field(ObjString_.id).count().add()
			.group()
				.field(ObjString_.text).upper().add()
			.order()
				.field(ObjString_.text).upper().asc()
			//.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0)[0], is("A"));
		assertThat(result.get(0)[1], is(2l));
		assertThat(result.get(1)[0], is("C"));
		assertThat(result.get(1)[1], is(2l));
		
	}
	
	@Test
	public void countDistinctField() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.field(ObjString_.text).upper().add()
				.field(ObjString_.text).countDistinct().add()
			.group()
				.field(ObjString_.text).upper().add()
			.order()
				.field(ObjString_.text).upper().asc()
			//.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0)[0], is("A"));
		assertThat(result.get(0)[1], is(2l));
		assertThat(result.get(1)[0], is("C"));
		assertThat(result.get(1)[1], is(1l));
		
	}
	
	@Test
	public void sumField() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.field(ObjString_.text).upper().add()
				.field(ObjString_.id).sum().add()
			.group()
				.field(ObjString_.text).upper().add()
			.order()
				.field(ObjString_.text).upper().asc()
			//.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0)[0], is("A"));
		assertThat(result.get(0)[1], is(3l));
		assertThat(result.get(1)[0], is("C"));
		assertThat(result.get(1)[1], is(7l));
		
	}
	
	@Test
	public void minMaxField() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.field(ObjString_.id).min().add()
				.field(ObjString_.id).max().add()
			//.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		assertThat(result.get(0)[0], is(1));
		assertThat(result.get(0)[1], is(4));
		
	}
	
	@Test
	public void avgField() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.field(ObjString_.id).sum().add()
				.field(ObjString_.id).avg().add()
			//.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		assertThat(result.get(0)[0], is(10l));
		assertThat(result.get(0)[1], is(2.5));
		
	}
	
}
