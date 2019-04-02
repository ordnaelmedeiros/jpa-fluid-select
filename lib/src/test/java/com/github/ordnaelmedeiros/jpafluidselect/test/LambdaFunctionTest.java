package com.github.ordnaelmedeiros.jpafluidselect.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.FSelect;
import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address_;
import com.github.ordnaelmedeiros.jpafluidselect.models.Country_;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LambdaFunctionTest extends SelectTestBase {
	
	@Test
	public void where() {
		
		List<People> list = new FSelect(em)
			.from(People.class)
			.where(w -> {
				w.orGroup()
					.equal(People_.id, 1l)
					.equal(People_.id, 2l);
			})
			.print()
			.getResultList()
			;
		
		assertEquals("Leandro", list.get(0).getName());
		assertEquals("Ivana", list.get(1).getName());
		
	}
	
	@Test
	public void order() {
		
		List<People> list = new FSelect(em)
			.from(People.class)
			.where(w -> {
				w.orGroup()
					.equal(People_.id, 1l)
					.equal(People_.id, 2l);
			})
			.order(o -> {
				o.asc(People_.id);
			})
			.print()
			.getResultList()
			;
		
		assertEquals("Leandro", list.get(0).getName());
		assertEquals("Ivana", list.get(1).getName());
		
	}
	
	@Test
	public void join() {
		
		List<People> list = new FSelect(em)
			.from(People.class)
			.join(People_.address, jAddress -> {
				jAddress.join(Address_.country, jCountry -> {
					jCountry.on()
						.eq(Country_.name, "Florida");
				});
			})
			.where(w -> {
				w.orGroup()
					.equal(People_.id, 1l)
					.equal(People_.id, 2l);
			})
			.order(o -> {
				o.asc(People_.id);
			})
			.print()
			.getResultList()
			;
		
		assertEquals("Leandro", list.get(0).getName());
		assertEquals("Florida", list.get(0).getAddress().getCountry().getName());
		assertEquals("Ivana", list.get(1).getName());
		
	}

}
