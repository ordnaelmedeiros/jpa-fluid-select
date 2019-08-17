package com.ordnaelmedeiros.jpafluidselect.querybuilder.order.time;

import static org.hamcrest.CoreMatchers.anyOf;
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

public class OrderByTimeTest {
	
	private static EntityManagerFactory emf;
	public EntityManager em;
	public QueryBuilder queryBuilder;
	
	private final int maxSize = 4;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
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
	public void timeAsc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.asc(ObjTime_.now)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(i+1));
		}
		
	}
	
	@Test
	public void timeDesc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.desc(ObjTime_.now)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(maxSize-i));
		}
		
	}
	
	@Test
	public void timeHourAsc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.field(ObjTime_.now).hour().asc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(i+1));
		}
		
	}
	
	@Test
	public void timeHourDesc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.field(ObjTime_.now).hour().desc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(maxSize-i));
		}
		
	}
	
	@Test
	public void timeSecondAsc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.field(ObjTime_.now).second().asc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(i+1));
		}
		
	}
	
	@Test
	public void timeSecondDesc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.field(ObjTime_.now).second().desc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(maxSize-i));
		}
		
	}
	
	@Test
	public void timeMinuteAsc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.field(ObjTime_.now).minute().asc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(1).getId(), anyOf(is(2), is(3)));
		assertThat(result.get(2).getId(), anyOf(is(2), is(3)));
		assertThat(result.get(3).getId(), is(4));
		
	}
	
	@Test
	public void timeMinuteDesc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.field(ObjTime_.now).minute().desc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		assertThat(result.get(0).getId(), is(4));
		assertThat(result.get(1).getId(), anyOf(is(2), is(3)));
		assertThat(result.get(2).getId(), anyOf(is(2), is(3)));
		assertThat(result.get(3).getId(), is(1));
		
	}
	

	@Test
	public void timeMinuteSecondAsc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.field(ObjTime_.now).minute().asc()
				.field(ObjTime_.now).second().asc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(i+1));
		}
		
	}
	
	@Test
	public void timeMinuteSecondDesc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.field(ObjTime_.now).minute().desc()
				.field(ObjTime_.now).second().desc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(maxSize-i));
		}
		
	}
	
	@Test
	public void timeMinuteAscSecondDesc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.field(ObjTime_.now).minute().asc()
				.field(ObjTime_.now).second().desc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));
		
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(1).getId(), is(3));
		assertThat(result.get(2).getId(), is(2));
		assertThat(result.get(3).getId(), is(4));
		
	}
	
	@Test
	public void timeMinuteDescSecondAsc() {
		
		List<ObjTime> result = queryBuilder
			.select(ObjTime.class)
			.order()
				.field(ObjTime_.now).minute().desc()
				.field(ObjTime_.now).second().asc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));
		
		assertThat(result.get(0).getId(), is(4));
		assertThat(result.get(1).getId(), is(2));
		assertThat(result.get(2).getId(), is(3));
		assertThat(result.get(3).getId(), is(1));
		
	}
	
}
