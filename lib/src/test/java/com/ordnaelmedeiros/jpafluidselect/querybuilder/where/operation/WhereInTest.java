package com.ordnaelmedeiros.jpafluidselect.querybuilder.where.operation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.Month;
import java.util.Arrays;
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
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.Ref;

public class WhereInTest {
	
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
	public void dateIn2017_2019() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.where()
				.field(ObjDate_.date).year().in(2017, 2019)
			.order()
				.asc(ObjDate_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getId(), is(2));
		assertThat(result.get(1).getId(), is(4));	

	}
	

	@Test
	public void dateNotIn2017_2019() {
		
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class)
			.where()
				.field(ObjDate_.date).year().not().in(2017, 2019)
			.order()
				.asc(ObjDate_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(1).getId(), is(3));	

		List<Integer> years = Arrays.asList(2017, 2019);
		
		result = queryBuilder
			.select(ObjDate.class)
			.where()
				.field(ObjDate_.date).year().not().in(years)
			.order()
				.asc(ObjDate_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(1).getId(), is(3));	
	}
	
	
	@Test
	public void dateIn2017_2019_ref() {
		
		Ref<ObjDate> ref = new Ref<>();
		List<ObjDate> result = queryBuilder
			.select(ObjDate.class).ref(ref)
			.where()
				.field(ObjDate_.date).year().in(
					ref.field(ObjDate_.date).year(),
					ref.field(ObjDate_.date).year()
				)
			.order()
				.asc(ObjDate_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(4));

	}
	
}
