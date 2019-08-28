package com.ordnaelmedeiros.jpafluidselect.querybuilder;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.Country;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.Status;

public class QueryBuilderTestBase {

	public static EntityManagerFactory emf;
	public EntityManager em;
	public QueryBuilder queryBuilder;
	
	@BeforeClass
	public static void beforeClass() {
		if (emf==null) {
			
			emf = Persistence.createEntityManagerFactory("h2");
			
			EntityManager em = emf.createEntityManager();
			
			Country one = em.find(Country.class, 1l);
			
			if (one==null) {
				
				em.getTransaction().begin();
				
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
				
				LocalDateTime now = LocalDateTime.of(2017, 6, 20, 5, 46);
				LocalDateTime time0 = now;
				LocalDateTime time1 = now.plusMinutes(1);
				LocalDateTime time2 = now.plusHours(1);
				LocalDateTime time3 = now.plusDays(1);
				LocalDateTime time4 = now.plusMonths(1);
				
				save(em, new People("Leandro", street1, time0));
				save(em, new People("Ivana", street2, time0));
				save(em, new People("Leandro", street3, time0).status(Status.INACTIVE));
				save(em, new People("Eduardo", street4, time1));
				save(em, new People("Rafael ", street5, time2));
				save(em, new People("Matheus", street6, time3));
				save(em, new People("Fabiano", street7, time4));
				
				em.getTransaction().commit();
				
			}
			
		}
	}
	
	private static <A> A save(EntityManager em, A entity) {
		em.persist(entity);
		return entity;
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		em.getTransaction().begin();
		
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@After
	public void after() {
		em.getTransaction().commit();
	}
	
}
