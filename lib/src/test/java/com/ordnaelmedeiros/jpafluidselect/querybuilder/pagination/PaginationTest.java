package com.ordnaelmedeiros.jpafluidselect.querybuilder.pagination;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjString;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.ObjString_;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.pagination.Pagination;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.pagination.PaginationResult;

public class PaginationTest {

	private static EntityManagerFactory emf;
	public EntityManager em;
	public QueryBuilder queryBuilder;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.createQuery("delete from ObjString").executeUpdate();
		
		for (int i = 1; i <= 110; i++) {
			em.persist(new ObjString(i, "TESTE "+i));
		}
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void testCount() {
		
		Long count = queryBuilder.select(ObjString.class)
			.fields()
				.count()
			.getSingleResult(Long.class);
		
		assertThat(count, CoreMatchers.is(110l));
		
	}
	
	@Test
	public void testLe15page1() {
		
		PaginationResult<ObjString> page = queryBuilder.select(ObjString.class)
			.where()
				.field(ObjString_.id).le(15)
			.order()
				.asc(ObjString_.id)
			.pagination()
				.numRows(10)
				.page(1)
			.getResultList();
			;
		
		assertThat(page.getPageNumber(), is(1));
		assertThat(page.getPageSize(), is(10));
		assertThat(page.getTotalRows(), is(15l));
		assertThat(page.getData().size(), is(10));
		for (int i = 0; i < page.getData().size(); i++) {
			ObjString o = page.getData().get(i);
			assertThat(o.getId(), is(i+1));
		}
		
	}
	
	@Test
	public void testLe15page2() {
		
		PaginationResult<ObjString> page = queryBuilder.select(ObjString.class)
			.where()
				.field(ObjString_.id).le(15)
			.order()
				.asc(ObjString_.id)
			.pagination()
				.numRows(10)
				.page(2)
			.getResultList();
			;
		
		assertThat(page.getPageNumber(), is(2));
		assertThat(page.getPageSize(), is(10));
		assertThat(page.getTotalRows(), is(15l));
		assertThat(page.getData().size(), is(5));
		for (int i = 0; i < page.getData().size(); i++) {
			ObjString o = page.getData().get(i);
			assertThat(o.getId(), is(i+11));
		}
		
	}

	@Test
	public void testLe15page3() {
		
		PaginationResult<ObjString> page = queryBuilder.select(ObjString.class)
			.where()
				.field(ObjString_.id).le(15)
			.order()
				.asc(ObjString_.id)
			.pagination()
				.numRows(10)
				.page(3)
			.getResultList();
			;
		
		assertThat(page.getPageNumber(), is(3));
		assertThat(page.getPageSize(), is(10));
		assertThat(page.getTotalRows(), is(15l));
		assertThat(page.getData().size(), is(0));
		
	}
	
	@Test
	public void testAll() {
		
		Pagination<ObjString> pagination = queryBuilder
				.select(ObjString.class)
				.order()
					.asc(ObjString_.id)
				.pagination();
		
		int index = 0;
		while (pagination.next()) {
			pagination.getResultList();
			index++;
		}
		
		assertThat(index, is(3));
		
	}
	
}
