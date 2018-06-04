package com.github.ordnaelmedeiros.jpafluidselect.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.FSelect;
import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DistinctSelectTest extends SelectTestBase {
	
	@Test
	public void testOrderDistinctStreet() {
		
		List<Object[]> list = new FSelect(em)
			.fromCustomFields(Address.class)
			.distinct()
			.fields()
				.add(Address_.street)
			.order().asc(Address_.street)
			.getResultList();
		
		//System.out.println(list);
		
		assertEquals(4, list.size());
		assertEquals("One", list.get(0));
		assertEquals("Seven", list.get(1));
		assertEquals("Six", list.get(2));
		assertEquals("Two", list.get(3));
		
	}
	
}
