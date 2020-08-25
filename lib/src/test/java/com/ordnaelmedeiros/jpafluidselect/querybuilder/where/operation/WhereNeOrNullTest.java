package com.ordnaelmedeiros.jpafluidselect.querybuilder.where.operation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.*;
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

public class WhereNeOrNullTest {

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
		em.persist(new ObjString(2, null));
		em.persist(new ObjString(3, "C"));
		em.persist(new ObjString(4, "D"));

		em.getTransaction().commit();

	}

	@Before
	public void before() {

		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);

	}

	@Test
	public void noOrNull() {

		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).neOrNull("C")
				.field(ObjString_.id).notEqualOrIsNull(4)
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();

		assertThat(result, notNullValue());
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(0).getText(), is("A"));
		assertThat(result.get(1).getId(), is(2));
		assertThat(result.get(1).getText(), nullValue());
	}

	@Test
	public void noOrNullOrGroup() {

		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.orGroup()
					.field(ObjString_.text).neOrNull("C")
					.field(ObjString_.id).notEqualOrIsNull(4)
				.end()
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();

		assertThat(result, notNullValue());
		assertThat(result.size(), is(4));
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(0).getText(), is("A"));
		assertThat(result.get(1).getId(), is(2));
		assertThat(result.get(1).getText(), nullValue());

		assertThat(result.get(2).getId(), is(3));
		assertThat(result.get(2).getText(), is("C"));
		assertThat(result.get(3).getId(), is(4));
		assertThat(result.get(3).getText(), is("D"));
	}

	@Test
	public void eqByRef() {

		Ref<ObjString> ref = new Ref<>();
		List<ObjString> result = queryBuilder
			.select(ObjString.class).ref(ref)
			.where()
				.field(ObjString_.text).neOrNull(ref.field(ObjString_.text))
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();

		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		assertThat(result.get(0).getId(), is(2));
		assertThat(result.get(0).getText(), nullValue());

	}

}
