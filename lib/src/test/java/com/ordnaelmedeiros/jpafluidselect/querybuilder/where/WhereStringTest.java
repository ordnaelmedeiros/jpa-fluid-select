package com.ordnaelmedeiros.jpafluidselect.querybuilder.where;

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

public class WhereStringTest {
	
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
		em.persist(new ObjString(2, "  A"));
		em.persist(new ObjString(3, "a"));
		em.persist(new ObjString(4, "A"));
		em.persist(new ObjString(5, "Leandro Medeiros"));
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void eqTrim() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).trim().eq("A")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(3));
		
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(1).getId(), is(2));
		assertThat(result.get(2).getId(), is(4));
		
	}
	
	@Test
	public void eqLower() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).lower().eq("a")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(3));
		
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(1).getId(), is(3));
		assertThat(result.get(2).getId(), is(4));
		
	}
	
	@Test
	public void eqUpper() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).upper().eq("A")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(3));
		
		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(1).getId(), is(3));
		assertThat(result.get(2).getId(), is(4));
		
	}
	
	@Test
	public void eqLength() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).length().eq(3)
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		
		assertThat(result.get(0).getId(), is(2));
		
	}

	@Test
	public void eqLocate() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).locate("a").eq(3)
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		
		assertThat(result.get(0).getId(), is(5));
		
	}

	@Test
	public void eqLocateStart() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).locate("e", 4).eq(10)
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		
		assertThat(result.get(0).getId(), is(5));
		
	}
	
	@Test
	public void eqSubstringStart() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).substring(9).eq("Medeiros")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		
		assertThat(result.get(0).getId(), is(5));
		
	}

	@Test
	public void eqSubstringStartlength() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.text).substring(9, 3).eq("Med")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		
		assertThat(result.get(0).getId(), is(5));
		
	}

	@Test
	public void eqConcat() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field(ObjString_.id).concat("-").eq("1-")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		
		assertThat(result.get(0).getId(), is(1));
		
	}
	
	@Test
	public void eqConcatRefAtt() {
		
		Ref<ObjString> ref = new Ref<>();
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class).ref(ref)
			.where()
				.field(ObjString_.id).concat("-").concat(ref, ObjString_.text).eq("5-Leandro Medeiros")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		
		assertThat(result.get(0).getId(), is(5));
		
	}
	
	@Test
	public void eqConcatFieldRef() {
		
		Ref<ObjString> ref = new Ref<>();
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class).ref(ref)
			.where()
				.field(ObjString_.id).concat("-").concat(ref.field(ObjString_.text).upper()).eq("5-LEANDRO MEDEIROS")
			.order()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		
		assertThat(result.get(0).getId(), is(5));
		
	}
	
}
