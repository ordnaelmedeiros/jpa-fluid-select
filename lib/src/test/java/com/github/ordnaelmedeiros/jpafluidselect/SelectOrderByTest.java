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
public class SelectOrderByTest extends SelectTestBase {
	
	@Test
	public void sucessFindOrderAsc() {
		
		List<People> lista1 = new Select(em)
			.from(People.class)
			.orderAsc(People_.id)
			.getResultList()
			;
		
		List<People> lista2 = new Select(em)
				.from(People.class)
				.order()
					.asc(People_.id)
				.getResultList()
				;
		
		assertEquals(7, lista1.size());
		for (int i=0; i<7; i++) {
			assertEquals(i+1, lista1.get(i).getId().intValue());
			assertEquals(i+1, lista2.get(i).getId().intValue());
		}
		
	}
	
	@Test
	public void sucessFindOrderDesc() {
		
		List<People> lista1 = new Select(em)
				.from(People.class)
				.orderDesc(People_.id)
				.getResultList()
				;
		
		List<People> lista2 = new Select(em)
			.from(People.class)
			.order()
				.desc(People_.id)
			.getResultList()
			;
		
		assertEquals(7, lista1.size());
		for (int i=0; i<7; i++) {
			assertEquals(7-i, lista1.get(i).getId().intValue());
			assertEquals(7-i, lista2.get(i).getId().intValue());
		}
		
	}
	
}

