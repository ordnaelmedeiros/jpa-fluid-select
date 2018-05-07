package com.github.ordnaelmedeiros.jpafluidselect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.JoinType;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address_;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;
import com.github.ordnaelmedeiros.jpafluidselect.models.Phone_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SelectTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		save(em, "Leandro", "Rua 1");
		save(em, "Ivana", "Leandro");
		save(em, "Leandro", "Leandro");
		save(em, "Eduardo", "Leandro");
		save(em, "Rafael", "Rua 1");
		save(em, "Matheus", "Leandro");
		save(em, "Fabiano", "Leandro");
		
		em.getTransaction().commit();
		
	}
	
	private static void save(EntityManager em, String people, String street) {
		
		Address a = new Address(street);
		em.persist(a);
		
		People p = new People(people, a);
		em.persist(p);
		
	}
	
	@Before
	public void before() {
		em = emf.createEntityManager();
		em.getTransaction().begin();
	}
	
	@After
	public void after() {
		em.getTransaction().commit();
	}
	
	@Test
	public void t000Nothing() {
		assertTrue(true);
	}
	
	@Test(expected=NoResultException.class)
	public void t001NaoDeveTrazerResultado() {
		
		new Select(em)
			.from(People.class)
			.where()
				.equal(People_.id, 9999999l)
			.end()
			.getSingleResult()
			;
		
	}
	
	
	@Test
	public void t010DeveBuscarComId1() {
		
		People o = new Select(em)
			.from(People.class)
			.where()
				.equal(People_.id, 1l)
			.end()
			.getSingleResult()
			;
		
		assertEquals("Leandro", o.getName());
		
	}
	
	@Test(expected=NonUniqueResultException.class)
	public void t010NaoDeveBuscarUniqueLeandro() {
		
		new Select(em)
			.from(People.class)
			.where()
				.equal(People_.name, "Leandro")
			.end()
			.getSingleResult()
			;
		
	}
	
	@Test
	public void t010DeveBuscarTodos() {
		
		List<People> lista = new Select(em)
			.from(People.class)
			.getResultList()
			;
		assertEquals(7, lista.size());
		
	}
	
	@Test
	public void t010DeveBuscar2Leandro() {
		
		List<People> lista = new Select(em)
			.from(People.class)
			.where()
				.equal(People_.name, "Leandro")
			.end()
			.getResultList()
			;
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId().intValue());
		assertEquals("Leandro", lista.get(0).getName());
		assertEquals(3, lista.get(1).getId().intValue());
		assertEquals("Leandro", lista.get(1).getName());
		
	}
	
	@Test
	public void t010DeveBuscarComId1e2() {
		
		List<People> lista = new Select(em)
			.from(People.class)
			.where()
				.or()
					.equal(People_.id, 1l)
					.equal(People_.id, 2l)
				.end()
			.end()
			.getResultList()
			;
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId().intValue());
		assertEquals(2, lista.get(1).getId().intValue());
		
	}
	
	@Test
	public void t010DeveBuscarComId1ComString() {
		
		List<People> lista = new Select(em)
			.from(People.class)
			.where()
				.like(People_.name, "%e%")
				.or()
					.equal(People_.id, 1l)
					.equal(People_.id, 2l)
					.equal(People_.id, 5l)
				.end()
			.end()
			.getResultList()
			;
		assertEquals(2, lista.size());
		
		/*
		select people1x0_.id as id1_0_, people1x0_.name as name2_0_ 
		from People1 people1x0_ 
		where (people1x0_.name like ?) 
		and (people1x0_.id=1 or people1x0_.id=2 or people1x0_.id=5)
		*/
		
	}
	
	@Test
	public void t010DeveBuscarComJoinAdress() {
		
		ContainerSelect c = new ContainerSelect();
		
		List<People> lista = new Select(em).extractToBuilder(c)
			.from(People.class)
			.join(People_.address)
				.on()
					.or()
						.equal(Address_.street, "Rua 1")
						.equal(Address_.street, "Rua 9999")
					.end()
				.end()
			.end()
			.join(JoinType.LEFT, People_.phones)
				.on()
					.equal(Phone_.number, "123")
				.end()
			.end()
			.where()
				.like(People_.name, "%a%")
				.or()
					.equal(People_.id, 1l)
					.equal(People_.id, 2l)
					.add(r -> c.builder().equal(r.get(People_.id), 5l))
				.end()
			.end()
			.getResultList()
			;
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId().intValue());
		assertEquals(5, lista.get(1).getId().intValue());
		
		/*
		
		SELECT people0_.id AS id1_1_,
		       people0_.address_id AS address_3_1_,
		       people0_.name AS name2_1_
		FROM People people0_
		INNER JOIN Address address1_ ON people0_.address_id=address1_.id
		AND (address1_.street=?
		     OR address1_.street=?)
		LEFT OUTER JOIN Phone phones2_ ON people0_.id=phones2_.people_id
		AND (phones2_.number=?)
		WHERE (people0_.name LIKE ?)
		  AND (people0_.id=1
		       OR people0_.id=2
		       OR people0_.id=5)

		*/
		
	}
	
}

