package com.github.ordnaelmedeiros.jpafluidselect.base;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;

public abstract class SelectTestBase {

	public static int groupTest = 0;
	
	private static EntityManagerFactory emf;
	public EntityManager em;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.createQuery("delete from People").executeUpdate();
		em.createQuery("delete from Address").executeUpdate();
		
		People.countId = 0l;
		Address.countId = 0l;
		
		save(em, "Leandro", "One");
		save(em, "Ivana", "Two");
		save(em, "Leandro", "Two");
		save(em, "Eduardo", "Six");
		save(em, "Rafael ", "One");
		save(em, "Matheus", "Six");
		save(em, "Fabiano", "Seven");
		
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
	
	
}
