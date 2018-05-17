package com.github.ordnaelmedeiros.jpafluidselect;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IgnoreCaseTrimSelectTest extends SelectTestBase {
	
	@Test(expected=NoResultException.class)
	public void notSucessFindEqualLowerCase() {
		
		new Select(em)
			.from(People.class)
			.where()
				.equal(People_.name, "leandro")
			.end()
			.getSingleResult()
			;
		
	}
	
	@Test(expected=NoResultException.class)
	public void notSucessFindEqualNoTrim() {
		
		new Select(em)
			.from(People.class)
			.where()
				.equal(People_.name, "Rafael")
			.end()
			.getSingleResult()
			;
		
		
	}
	
	@Test
	public void sucessFindEqualTrim() {
		
		new Select(em)
			.from(People.class)
			.where()
				.iEqual(People_.name, "Rafael")
			.end()
			.getSingleResult()
			;
		
	}
	
	@Test
	public void sucessFindEqualIgnoreCase() {
		
		List<People> peoples = new Select(em)
			.from(People.class)
			.where()
				.iEqual(People_.name, "leandro")
			.end()
			.getResultList()
			;
		
		assertEquals(2, peoples.size());
		assertEquals(1, peoples.get(0).getId().intValue());
		assertEquals(3, peoples.get(1).getId().intValue());
		
	}
	
	@Test(expected=NoResultException.class)
	public void notSucessFindLowerCaseLike() {
		
		new Select(em)
			.from(People.class)
			.where()
				.like(People_.name, "%le%")
			.end()
			.getSingleResult()
			;
		
	}
	
	@Test
	public void sucessFindIgnoreCaseLike() {
		
		List<People> peoples = new Select(em)
			.from(People.class)
			.where()
				.iLike(People_.name, "%le%")
			.end()
			.getResultList()
			;
		
		assertEquals(2, peoples.size());
		assertEquals(1, peoples.get(0).getId().intValue());
		assertEquals(3, peoples.get(1).getId().intValue());
		
	}
	
	@Test(expected=NoResultException.class)
	public void notSucessFindNoTrimLike() {
		
		new Select(em)
			.from(People.class)
			.where()
				.like(People_.name, "rafael")
			.end()
			.getSingleResult()
			;
		
	}
	
	@Test
	public void sucessFindTrimLike() {
		
		List<People> peoples = new Select(em)
			.from(People.class)
			.where()
				.iLike(People_.name, "rafael")
			.end()
			.getResultList()
			;
		
		assertEquals(1, peoples.size());
		assertEquals(5, peoples.get(0).getId().intValue());
		
	}
	
}

