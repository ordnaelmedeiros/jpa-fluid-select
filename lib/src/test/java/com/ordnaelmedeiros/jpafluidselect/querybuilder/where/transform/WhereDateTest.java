package com.ordnaelmedeiros.jpafluidselect.querybuilder.where.transform;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.Month;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjDate;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjDate_;

public class WhereDateTest {
	
	private static EntityManagerFactory emf;
	public EntityManager em;
	public QueryBuilder queryBuilder;
	
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
	public void eqDate() {
		
		LocalDate filter = LocalDate.of(2017, Month.JULY, 14);
		
		ObjDate obj = queryBuilder
			.select(ObjDate.class)
			.where()
				.field(ObjDate_.date).eq(filter)
			.print()
			.getSingleResult();
		
		assertThat(obj, notNullValue());
		assertThat(obj.getId(), is(2));
		assertThat(obj.getDate(), is(filter));
		
	}

	@Test
	public void eqYear() {
		
		ObjDate obj = queryBuilder
			.select(ObjDate.class)
			.where()
				.field(ObjDate_.date).year().eq(2017)
			.print()
			.getSingleResult();
		
		assertThat(obj, notNullValue());
		assertThat(obj.getId(), is(2));
		assertThat(obj.getDate(), is(LocalDate.of(2017, Month.JULY, 14)));
		
	}
	
	@Test
	public void eqMonth() {
		
		ObjDate obj = queryBuilder
			.select(ObjDate.class)
			.where()
				.field(ObjDate_.date).month().eq(8)
			.print()
			.getSingleResult();
		
		assertThat(obj, notNullValue());
		assertThat(obj.getId(), is(4));
		assertThat(obj.getDate(), is(LocalDate.of(2019, Month.AUGUST, 16)));
		
	}

	@Test
	public void eqDay() {
		
		ObjDate obj = queryBuilder
			.select(ObjDate.class)
			.where()
				.field(ObjDate_.date).day().eq(16)
			.print()
			.getSingleResult();
		
		assertThat(obj, notNullValue());
		assertThat(obj.getId(), is(4));
		assertThat(obj.getDate(), is(LocalDate.of(2019, Month.AUGUST, 16)));
		
	}
	
}
