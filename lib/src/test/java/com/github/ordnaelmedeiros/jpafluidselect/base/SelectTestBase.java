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
import com.github.ordnaelmedeiros.jpafluidselect.models.Country;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;

public abstract class SelectTestBase {
	
	private static EntityManagerFactory emf;
	public EntityManager em;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.createQuery("delete from People").executeUpdate();
		em.createQuery("delete from Address").executeUpdate();
		em.createQuery("delete from Country").executeUpdate();
		
		People.countId = 0l;
		Address.countId = 0l;
		Country.countId = 0l;
		
		Country florida = save(em, new Country("Florida"));
		Country nevada = save(em, new Country("Nevada"));
		
		Address street1 = save(em, new Address(florida, "One"));
		Address street2 = save(em, new Address(florida, "Two"));
		Address street3 = save(em, new Address(florida, "Two"));
		Address street4 = save(em, new Address(nevada, "Six"));
		Address street5 = save(em, new Address(florida, "One"));
		Address street6 = save(em, new Address(nevada, "Six"));
		Address street7 = save(em, new Address(nevada, "Seven"));
		
		save(em, new People("Leandro", street1));
		save(em, new People("Ivana", street2));
		save(em, new People("Leandro", street3));
		save(em, new People("Eduardo", street4));
		save(em, new People("Rafael ", street5));
		save(em, new People("Matheus", street6));
		save(em, new People("Fabiano", street7));
		
		em.getTransaction().commit();
		
	}
	
	private static <A> A save(EntityManager em, A entity) {
		em.persist(entity);
		return entity;
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
