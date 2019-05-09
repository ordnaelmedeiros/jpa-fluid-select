package com.github.ordnaelmedeiros.jpafluidselect.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.FSelect;
import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StringFieldsTest extends SelectTestBase {
	
	@Test
	public void join1() {
		
		List<Address> lista = new FSelect(em)
			.from(Address.class)
			.join("country")
				.on()
					.equal("id", 1)
				.end()
			.end()
			.print()
			.getResultList()
			;
		
		List<Long> floridaStreets = Arrays.asList(1l, 2l, 3l, 5l);
		
		boolean allMatch = lista.stream().mapToLong(Address::getId).allMatch(id->{
			return floridaStreets.contains(id);
		});
		assertTrue(allMatch);
		
	}
	
	@Test
	public void join2() {
		
		List<Address> lista = new FSelect(em)
			.from(Address.class)
			.join("country")
				.join("address")
					.on()
						.equal("id", 1)
					.end()
				.end()
			.end()
			.print()
			.getResultList()
			;
		
		List<Long> floridaStreets = Arrays.asList(1l, 2l, 3l, 5l);
		
		boolean allMatch = lista.stream().mapToLong(Address::getId).allMatch(id->{
			return floridaStreets.contains(id);
		});
		assertTrue(allMatch);
		
	}
	
	/*
	@Test
	public void join3() {
		
		List<Address> lista = new FSelect(em)
			.from(Address.class)
			.join("country.address")
				.on()
					.equal("id", 1)
				.end()
			.end()
			.print()
			.getResultList()
			;
		
		List<Long> floridaStreets = Arrays.asList(1l, 2l, 3l, 5l);
		
		boolean allMatch = lista.stream().mapToLong(Address::getId).allMatch(id->{
			return floridaStreets.contains(id);
		});
		assertTrue(allMatch);
		
	}
	*/
	
	@Test
	public void wherePeople() {
		
		People people = new FSelect(em)
			.from(People.class)
			.where()
				.equal("id", 1)
			.end()
			.print()
			.getSingleResult()
			;
		
		assertEquals(Long.valueOf(1l), people.getId());
		assertEquals("Leandro", people.getName());
		
	}
	
	@Test
	public void whereAdress() {
		
		List<Address> lista = new FSelect(em)
			.from(Address.class)
			.join("country", j-> {
				j.on()
					.equal("id", 1l);
			})
			.where()
				.equal("country.address.id", 1)
			.end()
			.print()
			.getResultList()
			;
		
		List<Long> floridaStreets = Arrays.asList(1l, 2l, 3l, 5l);
		
		boolean allMatch = lista.stream().mapToLong(Address::getId).allMatch(id->{
			return floridaStreets.contains(id);
		});
		assertTrue(allMatch);
		
	}
	
	@Test
	public void whereGreat1People() {
		
		List<People> peoples = new FSelect(em)
			.from(People.class)
			.where()
				.greaterThan("id", 1)
			.end()
			.order()
				.asc("id")
			.end()
			.print()
			.getResultList()
			;
		
		for (int i = 0; i < peoples.size(); i++) {
			assertEquals(Long.valueOf(i+2), peoples.get(i).getId());
		}
		
		//assertEquals(Long.valueOf(1l), people.getId());
		//assertEquals("Leandro", people.getName());
		
	}
	
	@Test
	public void whereGroup() {
		
		List<Object[]> list = new FSelect(em)
			.fromCustomFields(People.class)
			.fields()
				.add("address.street")
				.count("id")
			.group()
				.add("address.street")
			.end()
			.print()
			.getResultList()
			;
		
		for (Object[] o : list) {
			if (o[0].equals("Seven")) {
				assertEquals(Long.valueOf(1), o[1]);
			} else {
				assertEquals(Long.valueOf(2), o[1]);
			}
			
		}
		
		//assertEquals(Long.valueOf(1l), people.getId());
		//assertEquals("Leandro", people.getName());
		
	}
	
}
