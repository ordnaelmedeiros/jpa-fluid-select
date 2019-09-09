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
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.Ref;

public class WhereEqTest {
	
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
		em.persist(new ObjString(2, "B"));
		em.persist(new ObjString(3, "C"));
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void eqOrGroup() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.orGroup()
					.field(ObjString_.id).eq(1)
					.field(ObjString_.text).equal("C")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(0).getText(), is("A"));
		assertThat(result.get(1).getId(), is(3));
		assertThat(result.get(1).getText(), is("C"));
		
	}
	
	@Test
	public void eqByRef() {
		
		Ref<ObjString> ref = new Ref<>();
		List<ObjString> result = queryBuilder
			.select(ObjString.class).ref(ref)
			.where()
				.field(ObjString_.id).eq(ref.field(ObjString_.id))
				.orGroup()
					.field(ObjString_.id).eq(1)
					.field(ObjString_.text).equal("C")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(0).getText(), is("A"));
		assertThat(result.get(1).getId(), is(3));
		assertThat(result.get(1).getText(), is("C"));
		
	}
	
	@Test
	public void eqByIgnore() {
		
		boolean condicao = false;
		
		Ref<ObjString> ref = new Ref<>();
		List<ObjString> result = queryBuilder
			.select(ObjString.class).ref(ref)
			.where()
				.orGroup(or -> {
					or.field(ObjString_.id).eq(1);
					if (condicao) {
						or.field(ObjString_.text).equal("C");
					}
				})
			.order()
				.asc(ObjString_.id)
			//.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(0).getText(), is("A"));
		
	}
	
}
