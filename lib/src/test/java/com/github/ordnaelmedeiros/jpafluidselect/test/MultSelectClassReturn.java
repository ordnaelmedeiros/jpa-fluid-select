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
import com.github.ordnaelmedeiros.jpafluidselect.models.DTO;
import com.github.ordnaelmedeiros.jpafluidselect.models.DTO2;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MultSelectClassReturn extends SelectTestBase {
	
	
	private Join<People, Address> joinAdress;

	@Test
	public void testTransform() {
		
		DTO dto = new FSelect(em)
			.fromCustomFields(People.class, DTO.class)
			.join(People_.address).extractJoin(j -> this.joinAdress = j)
			.fields()
				.add(People_.name)
				.add(joinAdress, Address_.street)
			.where()
				.equal(People_.id, 1l)
			.getSingleResult();
		
		assertEquals("Leandro", dto.getPeopleName());
		assertEquals("One", dto.getPeopleStreet());
		
	}
	
	@Test
	public void testTransformByAlias() throws Exception {
		
		List<DTO2> list = new FSelect(em)
			.fromCustomFields(People.class)
			.join(People_.address).extractJoin(j -> this.joinAdress = j)
			.fields()
				.add(People_.name).alias("peopleName")
				.add(joinAdress, Address_.street).alias("peopleStreet")
			.where()
			//	.equal(People_.id, 1l)
			.end()
			.print()
			.getResultList(DTO2.class);
		
		assertEquals(7, list.size());
		assertEquals("Leandro", list.get(0).getPeopleName());
		assertEquals("One", list.get(0).getPeopleStreet());
		
		//assertEquals("Leandro", dto.getPeopleName());
		//assertEquals("One", dto.getPeopleStreet());
		
	}
	

	@Test
	public void testTransformSingleByAlias() throws Exception {
		
		DTO2 dto = new FSelect(em)
			.fromCustomFields(People.class)
			.join(People_.address).extractJoin(j -> this.joinAdress = j)
			.fields()
				.add(People_.name).alias("peopleName")
				.add(joinAdress, Address_.street).alias("peopleStreet")
			.where()
				.equal(People_.id, 1l)
			.end()
			.print()
			.getSingleResult(DTO2.class);
		
		assertEquals("Leandro", dto.getPeopleName());
		assertEquals("One", dto.getPeopleStreet());
		
		//assertEquals("Leandro", dto.getPeopleName());
		//assertEquals("One", dto.getPeopleStreet());
		
	}
	
}
