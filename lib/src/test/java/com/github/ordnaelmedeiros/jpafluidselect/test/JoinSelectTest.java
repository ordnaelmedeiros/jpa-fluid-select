package com.github.ordnaelmedeiros.jpafluidselect.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.FSelect;
import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address_;
import com.github.ordnaelmedeiros.jpafluidselect.models.Country_;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;
import com.github.ordnaelmedeiros.jpafluidselect.models.Phone_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JoinSelectTest extends SelectTestBase {
	
	public CriteriaBuilder builder;
	
	@Test
	public void t010DeveBuscarComJoinAdress() {
		
		List<People> lista = new FSelect(em).extractBuilder(b -> this.builder = b)
			.from(People.class)
			.join(People_.address)
				.on()
					.orGroup()
						.equal(Address_.street, "One")
						.equal(Address_.street, "9999")
					.end()
				.end()
			.end()
			.join(JoinType.LEFT, People_.phones)
				.on()
					.equal(Phone_.number, "123")
				.end()
			.where()
				.like(People_.name, "%a%")
				.orGroup()
					.equal(People_.id, 1l)
					.equal(People_.id, 2l)
					.add(r -> builder.equal(r.get(People_.id), 5l))
			.getResultList()
			;
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId().intValue());
		assertEquals(5, lista.get(1).getId().intValue());
		
		/*
		
		SELECT people0_.id AS id1_1_,
		       people0_.address_id AS address_3_1_,
		       people0_.name AS name2_1_
		FROM People people0_
		INNER JOIN Address address1_ ON people0_.address_id=address1_.id
		AND (address1_.street=?
		     OR address1_.street=?)
		LEFT OUTER JOIN Phone phones2_ ON people0_.id=phones2_.people_id
		AND (phones2_.number=?)
		WHERE (people0_.name LIKE ?)
		  AND (people0_.id=1
		       OR people0_.id=2
		       OR people0_.id=5)

		*/
		
	}
	
	@Test
	public void t020DeveBuscarComJoinAdress() {
		
		List<People> lista = new FSelect(em)
			.from(People.class)
			.join(People_.address)
				.join(Address_.country)
					.on()
						.equal(Country_.name, "Florida")
			.getResultList()
			;
		
		assertEquals(4, lista.size());
		//assertEquals(1, lista.get(0).getId().intValue());
		//assertEquals(5, lista.get(1).getId().intValue());

	}
	
}

