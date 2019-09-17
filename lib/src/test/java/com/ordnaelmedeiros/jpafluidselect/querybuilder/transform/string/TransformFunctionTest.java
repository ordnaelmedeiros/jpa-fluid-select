package com.ordnaelmedeiros.jpafluidselect.querybuilder.transform.string;

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

public class TransformFunctionTest {
	
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
	public void selectFunctionToChar() {
		
		List<Object[]> list = queryBuilder
			.select(ObjDate.class)
			.fields()
				.add(ObjDate_.id)
				.add(ObjDate_.date)
				.field(ObjDate_.date).function("TO_CHAR(:field, 'dd/MM/yyyy')").add()
			.where()
				.field(ObjDate_.id).in(1, 2)
			.order()
				.asc(ObjDate_.id)
			.print()
			.getResultObjects();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(2));
		assertThat(list.get(0)[0], is(1));
		assertThat(list.get(1)[0], is(2));
		
	}
	
	@Test
	public void whereFunctionToChar() {
		
		List<Object[]> list = queryBuilder
			.select(ObjDate.class)
			.fields()
				.add(ObjDate_.id)
				.add(ObjDate_.date)
				.field(ObjDate_.date).function("TO_CHAR(:field, 'dd/MM/yyyy')").add()
			.where()
				.field(ObjDate_.id).in(1, 2)
				.field(ObjDate_.date).function("TO_CHAR(:field, 'dd/MM/yyyy')", String.class).eq("13/06/2016")
			.order()
				.asc(ObjDate_.id)
			.print()
			.getResultObjects();
		
		assertThat(list, notNullValue());
		assertThat(list.size(), is(1));
		assertThat(list.get(0)[0], is(1));
		
	}
	
}
