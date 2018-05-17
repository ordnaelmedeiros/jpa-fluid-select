package com.github.ordnaelmedeiros.jpafluidselect;

import static org.junit.Assert.assertEquals;

import javax.persistence.criteria.Join;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address_;
import com.github.ordnaelmedeiros.jpafluidselect.models.DTO;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MultSelectClassReturn extends SelectTestBase {
	
	
	private Join<People, Address> joinAdress;

	@Test
	public void testTransform() {
		
		DTO dto = new Select(em)
			.fromMultiSelect(People.class, DTO.class)
			.join(People_.address).extractJoin(j -> this.joinAdress = j).end()
			.fields()
				.add(People_.name)
				.add(joinAdress, Address_.street)
			.end()
			.where()
				.equal(People_.id, 1l)
			.end()
			.getSingleResult();
		
		assertEquals("Leandro", dto.getPeopleName());
		assertEquals("One", dto.getPeopleStreet());
		
	}
	
}
