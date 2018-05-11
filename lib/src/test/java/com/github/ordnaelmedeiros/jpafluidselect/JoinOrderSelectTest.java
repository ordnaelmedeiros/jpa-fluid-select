package com.github.ordnaelmedeiros.jpafluidselect;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address_;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JoinOrderSelectTest extends SelectTestBase {
	
	public CriteriaBuilder builder;
	
	private Join<People, Address> joinAdress;
	
	@Test
	public void t010DeveBuscarComJoinAdress() {
		
		List<People> lista = new Select(em).extractBuilder(b -> this.builder = b)
			.from(People.class)
			.join(People_.address).extractJoin(j -> this.joinAdress = j)
			.end()
			.order()
				.asc(joinAdress, Address_.street)
				.desc(People_.id)
			.end()
			.getResultList()
			;
		
		lista.forEach(System.out::println);
		
		assertEquals(7, lista.size());
		
		assertEquals(5, lista.get(0).getId().intValue());
		assertEquals(1, lista.get(1).getId().intValue());
		assertEquals(7, lista.get(2).getId().intValue());
		assertEquals(6, lista.get(3).getId().intValue());
		assertEquals(4, lista.get(4).getId().intValue());
		assertEquals(3, lista.get(5).getId().intValue());
		assertEquals(2, lista.get(6).getId().intValue());
		
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
	
}

