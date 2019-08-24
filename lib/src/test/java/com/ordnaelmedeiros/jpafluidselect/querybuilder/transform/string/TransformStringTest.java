package com.ordnaelmedeiros.jpafluidselect.querybuilder.transform.string;

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

public class TransformStringTest {
	
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
	public void text_1_2_asc() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.where()
				.field("id").in(1, 2)
			.order()
				.asc(ObjString_.text)
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0).getId(), is(2));
		assertThat(result.get(1).getId(), is(1));
		
	}
	
	@Test
	public void textTrim_1_2_asc() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.field(ObjString_.text).trim().add()
			.where()
				.field("id").in(1, 2)
			.order()
				.field(ObjString_.text).trim().asc()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0).getId(), is(1));
		assertThat(result.get(1).getId(), is(2));
		
	}
	
	@Test
	public void textUpper_3_4_asc() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.field(ObjString_.text).upper().add()
			.where()
				.field("id").in(3, 4)
			.order()
				.field(ObjString_.text).upper().asc()
				.asc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0).getId(), is(3));
		assertThat(result.get(1).getId(), is(4));
		
	}
	
	@Test
	public void textLower_3_4_asc() {
		
		List<ObjString> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.field(ObjString_.text).lower().add()
			.where()
				.field("id").in(3, 4)
			.order()
				.field(ObjString_.text).lower().asc()
				.desc(ObjString_.id)
			.print()
			.getResultList();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0).getId(), is(4));
		assertThat(result.get(1).getId(), is(3));
		
	}

	@Test
	public void textLength_1_2_asc() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.field(ObjString_.text).length().add()
			.where()
				.field("id").in(1, 2)
			.order()
				.field(ObjString_.text).length().asc()
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0)[0], is(1));
		assertThat(result.get(0)[1], is(1));
		assertThat(result.get(1)[0], is(2));
		assertThat(result.get(1)[1], is(3));
		
	}
	
	@Test
	public void textLocate() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.add(ObjString_.text)
				.field(ObjString_.text).upper().locate("D").add()
			.where()
				.field("id").eq(5)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0)[0], is(5));
		assertThat(result.get(0)[2], is(5));
		
	}

	@Test
	public void textLocateIndex() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.add(ObjString_.text)
				.field(ObjString_.text).locate("e", 3).add()
			.where()
				.field("id").eq(5)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0)[0], is(5));
		assertThat(result.get(0)[2], is(10));
		
	}
	
	@Test
	public void textSubstringStart() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.add(ObjString_.text)
				.field(ObjString_.text).substring(9).upper().add()
			.where()
				.field("id").eq(5)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0)[0], is(5));
		assertThat(result.get(0)[2], is("MEDEIROS"));
		
	}
	
	@Test
	public void textSubstringStartLength() {
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class)
			.fields()
				.add(ObjString_.id)
				.add(ObjString_.text)
				.field(ObjString_.text).substring(9, 3).lower().add()
			.where()
				.field("id").eq(5)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0)[0], is(5));
		assertThat(result.get(0)[2], is("med"));
		
	}
	

	@Test
	public void textConcat() {
		
		Ref<ObjString> refObjString = new Ref<>();
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class).ref(refObjString)
			.fields()
				.add(ObjString_.id)
				.add(ObjString_.text)
				.field(ObjString_.text).concat(" - ", "a", " - b").add()
				.field(ObjString_.id).concat(" - ").concat(refObjString, "text").add()
				.field(ObjString_.id).concat(" - ").concat(refObjString, ObjString_.text).add()
			.where()
				.field("id").eq(5)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0)[0], is(5));
		assertThat(result.get(0)[2], is("Leandro Medeiros - a - b"));
		assertThat(result.get(0)[3], is("5 - Leandro Medeiros"));
		assertThat(result.get(0)[4], is("5 - Leandro Medeiros"));
		
	}
	
	@Test
	public void textConcatOhh() {
		
		Ref<ObjString> ref = new Ref<>();
		
		List<Object[]> result = queryBuilder
			.select(ObjString.class).ref(ref)
			.fields()
				.add(ObjString_.id)
				.add(ObjString_.text)
				.field(ObjString_.text).substring(1, 7).lower()
					.concat(" - ")
					.concat(ref
						.field(ObjString_.text).substring(9).upper()
					)
				.add()
			.where()
				.field("id").eq(5)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());

		assertThat(result.get(0)[0], is(5));
		assertThat(result.get(0)[2], is("leandro - MEDEIROS"));
		
	}

}
