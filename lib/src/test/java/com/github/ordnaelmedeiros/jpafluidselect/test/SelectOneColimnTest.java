package com.github.ordnaelmedeiros.jpafluidselect.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.FSelect;
import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SelectOneColimnTest extends SelectTestBase {
	
	@Test
	public void deveSomar3() {
		
		Long sum = new FSelect(em)
			.fromCustomFields(People.class, Long.class)
			.fields()
				.sum(People_.id)
			.where()
				.le(People_.id, 2l)
			.getSingleResult();
		
		assertEquals(3, sum.intValue());
		
	}
	
	@Test
	public void deveRetornarNull() {
		
		Long sum = new FSelect(em)
			.fromCustomFields(People.class, Long.class)
			.fields()
				.sum(People_.id)
			.where()
				.lt(People_.id, 0l)
			.getSingleResult();
		
		assertNull(sum);
		
		
	}
	
	@Test
	public void deveListarNomes() {
		
		List<String> nomes = new FSelect(em)
			.fromCustomFields(People.class, String.class)
			.fields()
				.add(People_.name)
			.where()
				.le(People_.id, 2l)
			.order()
				.asc(People_.id)
			.getResultList();
		
		assertEquals("Leandro", nomes.get(0));
		assertEquals("Ivana", nomes.get(1));
		
	}
	
	@Test
	public void deveListarRuas() {
		
		List<Address> listAddress = new FSelect(em)
			.fromCustomFields(People.class, Address.class)
			.fields()
				.add(People_.address)
			.where()
				.le(People_.id, 2l)
			.order()
				.asc(People_.id)
			.getResultList();
		
		assertEquals("One", listAddress.get(0).getStreet());
		assertEquals("Two", listAddress.get(1).getStreet());
		
	}
	
}

