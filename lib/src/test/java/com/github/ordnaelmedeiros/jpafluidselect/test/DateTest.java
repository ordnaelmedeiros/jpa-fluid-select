package com.github.ordnaelmedeiros.jpafluidselect.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.Select;
import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;
import com.github.ordnaelmedeiros.jpafluidselect.predicate.TemporalFunction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DateTest extends SelectTestBase {
	
	@Test
	public void whereEqual() {
		
		LocalDateTime now = LocalDateTime.of(2017, 6, 20, 5, 46);
		
		List<People> list = new Select(em)
			.from(People.class)
			.where()
				.equal(People_.created, now)
			.getResultList();
			
		assertEquals(3, list.size());
		
	}
	
	@Test
	public void whereGreaterThan() {
		
		LocalDateTime now = LocalDateTime.of(2017, 6, 20, 5, 46);
		
		List<People> list = new Select(em)
			.from(People.class)
			.where()
				.greaterThan(People_.created, now)
			.getResultList();
			
		assertEquals(4, list.size());
		
	}
	
	@Test
	public void whereGreaterThanOrEqualTo() {
		
		LocalDateTime now = LocalDateTime.of(2017, 6, 20, 5, 46);
		
		List<People> list = new Select(em)
			.from(People.class)
			.where()
				.greaterThanOrEqualTo(People_.created, now)
			.getResultList();
			
		assertEquals(7, list.size());
		
	}
	
	@Test
	public void whereBetween() {
		
		LocalDateTime now = LocalDateTime.of(2017, 6, 20, 5, 46);
		
		List<People> list = new Select(em)
			.from(People.class)
			.where()
				.between(People_.created, now, now)
			.getResultList();
			
		assertEquals(3, list.size());
		
	}
	

	@Test
	public void whereEqualMonth() {
		
		List<People> list = new Select(em)
			.from(People.class)
			.where()
				.equal(People_.created, TemporalFunction.MONTH, 6)
			.getResultList();
			
		assertEquals(6, list.size());
		
	}
	
	@Test
	public void whereEqualDay() {
		
		List<People> list = new Select(em)
			.from(People.class)
			.where()
				.equal(People_.created, TemporalFunction.DAY, 20)
			.getResultList();
			
		assertEquals(6, list.size());
		
	}
	
	@Test
	public void whereEqualYear() {
		
		List<People> list = new Select(em)
			.from(People.class)
			.where()
				.equal(People_.created, TemporalFunction.YEAR, 2017)
			.getResultList();
			
		assertEquals(7, list.size());
		
	}

}
