package com.github.ordnaelmedeiros.jpafluidselect.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.criteria.Join;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.FSelect;
import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address_;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MultiSelectTest extends SelectTestBase {
	
	private Join<People, Address> joinAdress;

	@Test
	public void t010DeveBuscarComJoinAdress() {
		
		List<Object[]> lista = new FSelect(em)
			.fromCustomFields(People.class)
			.join(People_.address).extractJoin(j -> this.joinAdress = j)
			.fields()
				.add(People_.id)
				.add(People_.name)
				.add(joinAdress, Address_.street)
			.where()
				.in(People_.id, new Long[] {1l, 2l})
			.order()
				.asc(People_.id)
			.getResultList()
			;
		
		assertEquals(2, lista.size());
		
		assertEquals("1", ""+lista.get(0)[0]);
		assertEquals("Leandro", lista.get(0)[1]);
		assertEquals("One", lista.get(0)[2]);
		
		assertEquals("2", ""+lista.get(1)[0]);
		assertEquals("Ivana", lista.get(1)[1]);
		assertEquals("Two", lista.get(1)[2]);
		
		for (Object[] lObj : lista) {
			System.out.println();
			for (Object object : lObj) {
				System.out.print(String.format("| %10s |", object));
			}
		}
		System.out.println();
		
		/*
		
		|          1 ||    Leandro ||        One |
		|          2 ||      Ivana ||        Two |
		
		*/
		
	}
	
	@Test
	public void t011DeveBuscarCampoLiteral() throws Exception {
		
		List<People> lista = new FSelect(em)
			.fromCustomFields(People.class)
			.fields()
				.literal(Long.valueOf("1")).alias("id")
				.literal("name literal").alias("name")
			.where()
				.in(People_.id, new Long[] {1l, 2l})
			.order()
				.asc(People_.id)
			.end()
			.print()
			.getResultList(People.class)
			;
		
		assertEquals(2, lista.size());
		
		assertEquals(Long.valueOf("1"), lista.get(0).getId());
		assertEquals("name literal", lista.get(0).getName());
		
		assertEquals(Long.valueOf("1"), lista.get(1).getId());
		assertEquals("name literal", lista.get(1).getName());
		
	}
	
}

