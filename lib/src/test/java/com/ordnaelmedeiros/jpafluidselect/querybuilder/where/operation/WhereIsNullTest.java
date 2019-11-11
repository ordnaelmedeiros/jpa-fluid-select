package com.ordnaelmedeiros.jpafluidselect.querybuilder.where.operation;

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

public class WhereIsNullTest {
	
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
		em.persist(new ObjString(2, null));
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void textIsNull() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).isNull()
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		assertThat(result.get(0).getId(), is(2));
		
	}
	
	@Test
	public void textIsNotNull() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).isNotNull()
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		assertThat(result.get(0).getId(), is(1));
		
	}
	
}
