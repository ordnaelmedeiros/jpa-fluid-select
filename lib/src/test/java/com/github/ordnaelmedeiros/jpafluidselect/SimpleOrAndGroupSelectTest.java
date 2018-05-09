package com.github.ordnaelmedeiros.jpafluidselect;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleOrAndGroupSelectTest extends SelectTestBase {

	@Test
	public void t010DeveBuscarComId1e2() {
		
		List<People> lista = new Select(em)
			.from(People.class)
			.where()
				.orGroup()
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
	public void t010DeveBuscarComId1Condition() {
		
		boolean notFindById2 = false;
		
		List<People> lista = new Select(em)
			.from(People.class)
			.where()
				.orGroup()
					.equal(People_.id, 1l)
					.ifCan(notFindById2).equal(People_.id, 2l)
				.end()
			.end()
			.getResultList()
			;
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getId().intValue());
		
	}
	
	@Test
	public void t010DeveBuscarComId1ComString() {
		
		List<People> lista = new Select(em)
			.from(People.class)
			.where()
				.like(People_.name, "%e%")
				.orGroup()
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
	
}

