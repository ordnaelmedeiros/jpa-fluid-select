package com.github.ordnaelmedeiros.jpafluidselect.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.FSelect;
import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IgnoreCaseTrimSelectTest extends SelectTestBase {
	
	public void notSucessFindEqualLowerCase() {
		
		People people = new FSelect(em)
			.from(People.class)
			.where()
				.equal(People_.name, "leandro")
			.getSingleResult()
			;
		
		assertNull(people);
		
	}
	
	public void notSucessFindEqualNoTrim() {
		
		People people = new FSelect(em)
			.from(People.class)
			.where()
				.equal(People_.name, "Rafael")
			.getSingleResult()
			;
		
		assertNull(people);
		
	}
	
	@Test
	public void sucessFindEqualTrim() {
		
		new FSelect(em)
			.from(People.class)
			.where()
				.iEqual(People_.name, "Rafael")
			.getSingleResult()
			;
		
	}
	
	@Test
	public void sucessFindEqualIgnoreCase() {
		
		List<People> peoples = new FSelect(em)
			.from(People.class)
			.where()
				.iEqual(People_.name, "leandro")
			.getResultList()
			;
		
		assertEquals(2, peoples.size());
		assertEquals(1, peoples.get(0).getId().intValue());
		assertEquals(3, peoples.get(1).getId().intValue());
		
	}
	
	public void notSucessFindLowerCaseLike() {
		
		People people = new FSelect(em)
			.from(People.class)
			.where()
				.like(People_.name, "%le%")
			.getSingleResult()
			;
		
		assertNull(people);
		
	}
	
	@Test
	public void sucessFindIgnoreCaseLike() {
		
		List<People> peoples = new FSelect(em)
			.from(People.class)
			.where()
				.iLike(People_.name, "%le%")
			.getResultList()
			;
		
		assertEquals(2, peoples.size());
		assertEquals(1, peoples.get(0).getId().intValue());
		assertEquals(3, peoples.get(1).getId().intValue());
		
	}
	
	public void notSucessFindNoTrimLike() {
		
		People people = new FSelect(em)
			.from(People.class)
			.where()
				.like(People_.name, "rafael")
			.getSingleResult()
			;
		
		assertNull(people);
		
	}
	
	@Test
	public void sucessFindTrimLike() {
		
		List<People> peoples = new FSelect(em)
			.from(People.class)
			.where()
				.iLike(People_.name, "rafael")
			.getResultList()
			;
		
		assertEquals(1, peoples.size());
		assertEquals(5, peoples.get(0).getId().intValue());
		
	}
	
}

