package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

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

public class SelectDistinctTest {
	
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
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void distinct() {
		
		List<String> result = queryBuilder
			.select(ObjString.class).distinct()
			.fields()
				.field(ObjString_.text).upper().add()
			.order()
				.field(ObjString_.text).upper().asc()
			//.print()
			.getResultList(String.class);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0), is("A"));
		assertThat(result.get(1), is("C"));
		
	}
	
}
