package com.github.ordnaelmedeiros.jpafluidselect.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.FSelect;
import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LessThanOrEqualToTest extends SelectTestBase {
	
	@Test
	public void deveTrazerDoisRegistros() {
		
		List<People> resultList = new FSelect(em)
			.from(People.class)
			.where()
				.le(People_.id, 2l)
			.getResultList();
		
		assertEquals(2, resultList.size());
	}
}

