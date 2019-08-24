package com.ordnaelmedeiros.jpafluidselect.querybuilder.where;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjTime;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjTime_;

public class WhereTimeTest {
	
	private static EntityManagerFactory emf;
	public EntityManager em;
	public QueryBuilder queryBuilder;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.createQuery("delete from ObjTime").executeUpdate();
		
		em.persist(new ObjTime(1, 16, 6, 13));
		em.persist(new ObjTime(2, 17, 7, 14));
		em.persist(new ObjTime(3, 18, 7, 15));
		em.persist(new ObjTime(4, 19, 8, 16));
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void eqTime() {
		
		LocalTime filter = LocalTime.of(17, 7, 14);
		
		ObjTime obj = queryBuilder
			.select(ObjTime.class)
			.where()
				.field(ObjTime_.now).eq(filter)
			.print()
			.getSingleResult();
		
		assertThat(obj, notNullValue());
		assertThat(obj.getId(), is(2));
		assertThat(obj.getNow(), is(filter));
		
	}

	@Test
	public void eqHour() {
		
		ObjTime obj = queryBuilder
			.select(ObjTime.class)
			.where()
				.field(ObjTime_.now).hour().eq(19)
			.print()
			.getSingleResult();
		
		assertThat(obj, notNullValue());
		assertThat(obj.getId(), is(4));
		assertThat(obj.getNow(), is(LocalTime.of(19, 8, 16)));
		
	}
	
	@Test
	public void eqMinute() {
		
		ObjTime obj = queryBuilder
			.select(ObjTime.class)
			.where()
				.field(ObjTime_.now).minute().eq(8)
			.print()
			.getSingleResult();
			
		assertThat(obj, notNullValue());
		assertThat(obj.getId(), is(4));
		assertThat(obj.getNow(), is(LocalTime.of(19, 8, 16)));
		
	}
	
	@Test
	public void eqSecond() {
		
		ObjTime obj = queryBuilder
			.select(ObjTime.class)
			.where()
				.field(ObjTime_.now).second().eq(16)
			.print()
			.getSingleResult();
			
		assertThat(obj, notNullValue());
		assertThat(obj.getId(), is(4));
		assertThat(obj.getNow(), is(LocalTime.of(19, 8, 16)));
		
	}
	
}
