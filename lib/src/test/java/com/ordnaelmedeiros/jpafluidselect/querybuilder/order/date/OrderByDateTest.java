package com.ordnaelmedeiros.jpafluidselect.querybuilder.order.date;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.Month;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjDate;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjDate_;

public class OrderByDateTest {
	
	private static EntityManagerFactory emf;
	public EntityManager em;
	public QueryBuilder queryBuilder;
	
	private final int maxSize = 4;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.createQuery("delete from ObjDate").executeUpdate();
		
		em.persist(new ObjDate(1, 2016, Month.JUNE, 13));
		em.persist(new ObjDate(2, 2017, Month.JULY, 14));
		em.persist(new ObjDate(3, 2018, Month.JULY, 15));
		em.persist(new ObjDate(4, 2019, Month.AUGUST, 16));
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void dateAsc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.asc(ObjDate_.date)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(i+1));
		}
		
	}
	
	@Test
	public void dateDesc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.desc(ObjDate_.date)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(maxSize-i));
		}
		
	}
	
	@Test
	public void dateYearAsc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.field(ObjDate_.date).year().asc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(i+1));
		}
		
	}
	
	@Test
	public void dateYearDesc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.field(ObjDate_.date).year().desc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(maxSize-i));
		}
		
	}
	
	@Test
	public void dateDayAsc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.field(ObjDate_.date).day().asc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(i+1));
		}
		
	}
	
	@Test
	public void dateDayDesc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.field(ObjDate_.date).day().desc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(maxSize-i));
		}
		
	}
	
	@Test
	public void dateMonthAsc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.field(ObjDate_.date).month().asc()
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
	public void dateMonthDesc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.field(ObjDate_.date).month().desc()
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
	public void dateMonthDayAsc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.field(ObjDate_.date).month().asc()
				.field(ObjDate_.date).day().asc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(i+1));
		}
		
	}
	
	@Test
	public void dateMonthDayDesc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.field(ObjDate_.date).month().desc()
				.field(ObjDate_.date).day().desc()
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(maxSize));

		for (int i = 0; i < maxSize; i++) {
			assertThat(result.get(i).getId(), is(maxSize-i));
		}
		
	}
	
	@Test
	public void dateMonthAscDayDesc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.field(ObjDate_.date).month().asc()
				.field(ObjDate_.date).day().desc()
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
	public void dateMonthDescDayAsc() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.order()
				.field(ObjDate_.date).month().desc()
				.field(ObjDate_.date).day().asc()
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
