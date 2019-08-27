package com.ordnaelmedeiros.jpafluidselect.querybuilder.where.transform;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjDateTime;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjDateTime_;

public class WhereCastTest {
	
	private static EntityManagerFactory emf;
	public EntityManager em;
	public QueryBuilder queryBuilder;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.createQuery("delete from ObjDateTime").executeUpdate();
		
		em.persist(new ObjDateTime(1, LocalDateTime.of(2019, 9, 24, 10, 52)));
		em.persist(new ObjDateTime(2, LocalDateTime.of(2019, 9, 24, 18, 52)));
		em.persist(new ObjDateTime(3, LocalDateTime.of(2019, 9, 23, 18, 52)));
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void eqDate() {
		
		LocalDate filter = LocalDate.of(2019, Month.SEPTEMBER, 24);
		
		List<ObjDateTime> result = queryBuilder
			.select(ObjDateTime.class)
			.where()
				.field(ObjDateTime_.now).cast(LocalDate.class).eq(filter)
			.order()
				.asc(ObjDateTime_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(0).getNow(), is(LocalDateTime.of(2019, 9, 24, 10, 52)));
		
		assertThat(result.get(1).getId(), is(2));
		assertThat(result.get(1).getNow(), is(LocalDateTime.of(2019, 9, 24, 18, 52)));
		
	}
	
}
