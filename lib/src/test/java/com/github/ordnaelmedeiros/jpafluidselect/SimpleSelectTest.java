package com.github.ordnaelmedeiros.jpafluidselect;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleSelectTest extends SelectTestBase {
	
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
	public void t010DeveBuscarDiferenteId1() {
		
		List<People> lista = new Select(em)
			.from(People.class)
			.where()
				.not().equal(People_.id, 1l)
			.end()
			.getResultList()
			;
		assertEquals(6, lista.size());
		
		/*
		select people1x0_.id as id1_0_, people1x0_.name as name2_0_ 
		from People1 people1x0_ 
		where (people1x0_.name like ?) 
		and (people1x0_.id=1 or people1x0_.id=2 or people1x0_.id=5)
		*/
		
	}
	
	@Test
	public void t010DeveBuscarPeopleCount() {
		
		Long count = new Select(em)
			.fromCount(People.class)
			.getSingleResult();
		
		assertEquals(7, count.intValue());
		
	}
	
	@Test
	public void t010DeveBuscarPeopleCount5() {
		
		Long count = new Select(em)
			.fromCount(People.class)
			.where()
				.not().equal(People_.id, 1l)
				.not().equal(People_.id, 5l)
			.end()
			.getSingleResult();
		
		assertEquals(5, count.intValue());
		
	}

}

