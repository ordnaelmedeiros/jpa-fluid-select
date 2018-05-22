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
public class PaginationSelectTest extends SelectTestBase {
	
	@Test
	public void testPage0limit20() {
		
		List<Object[]> list = new Select(em)
			.fromMultiSelect(People.class)
			.fields()
				.add(People_.id)
			.end()
			.orderAsc(People_.id)
			.getResultList(0, 0);
		
		assertEquals(7, list.size());
		assertEquals(1l, list.get(0));
		assertEquals(2l, list.get(1));
		//...
		
	}

	@Test
	public void testPaget1limit3() {
		
		List<Object[]> list = new Select(em)
			.fromMultiSelect(People.class)
			.fields()
				.add(People_.id)
			.end()
			.orderAsc(People_.id)
			.getResultList(1, 3);
		
		assertEquals(3, list.size());
		assertEquals(1l, list.get(0));
		assertEquals(2l, list.get(1));
		assertEquals(3l, list.get(2));
		
	}

	@Test
	public void testPaget2limit3() {
		
		List<Object[]> list = new Select(em)
			.fromMultiSelect(People.class)
			.fields()
				.add(People_.id)
			.end()
			.orderAsc(People_.id)
			.getResultList(2, 3);
		
		assertEquals(3, list.size());
		assertEquals(4l, list.get(0));
		assertEquals(5l, list.get(1));
		assertEquals(6l, list.get(2));
		
	}
	
	@Test
	public void testPaget3limit3() {
		
		List<Object[]> list = new Select(em)
			.fromMultiSelect(People.class)
			.fields()
				.add(People_.id)
			.end()
			.orderAsc(People_.id)
			.getResultList(3, 3);
		
		assertEquals(1, list.size());
		assertEquals(7l, list.get(0));
		
	}
	
}
