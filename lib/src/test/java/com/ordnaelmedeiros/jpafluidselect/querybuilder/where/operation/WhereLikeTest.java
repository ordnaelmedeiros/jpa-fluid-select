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

public class WhereLikeTest {
	
	private static EntityManagerFactory emf;
	public EntityManager em;
	public QueryBuilder queryBuilder;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.createQuery("delete from ObjString").executeUpdate();
		
		em.persist(new ObjString(1, "Leandro"));
		em.persist(new ObjString(2, "Ivana"));
		em.persist(new ObjString(3, "leandro"));
		em.persist(new ObjString(4, "Monica"));
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void like_Le() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).like("Le%")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		assertThat(result.get(0).getId(), is(1));
		
	}
	

	@Test
	public void ilike_Le() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).ilike("Le%")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(1).getId(), is(3));
		
	}
	
	@Test
	public void like_an() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).like("%an%")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(3));
		
	}
	

	@Test
	public void notlike_an() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).not().like("%an%")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		assertThat(result.get(0).getId(), is(4));
		
	}
	
	
}
