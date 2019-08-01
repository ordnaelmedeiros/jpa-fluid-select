package com.ordnaelmedeiros.jpafluidselect.querybuilder;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address_;
import com.github.ordnaelmedeiros.jpafluidselect.models.Country;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;
import com.github.ordnaelmedeiros.jpafluidselect.models.Status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateTest extends SelectTestBase {
	
	@Test
	public void t001create() {
		
		QueryBuilder qb = new QueryBuilder(em);
		List<People> list = qb
			.select(People.class)
			.where()
				.field("address.country").eq(new Country(1l))
				.field("address").to("country").eq(new Country(1l))
				.field(People_.address).to(Address_.country).eq(new Country(1l))
				.field(People_.status).eq(Status.ACTIVE)
				.field(People_.status).cast(String.class).eq("ACTIVE")
				.field("created").cast(LocalDate.class).eq(LocalDate.of(2017, Month.JUNE, 20))
				.field("created").extract("year", Integer.class).eq(2017)
			.order()
				.field(People_.name).asc()
				.field("id").desc()
			.getResultList();
		
		if (list!=null) {
			list.forEach(System.out::println);
		}
		
//		.eq(qb.path(People_.address).to(Address_.country).to(Country_.id).toString(), 1l)
//		.eq("address.country.id", 1l)
//		.gt(People_.id.getName(), 1l)
//		.lt("id", 6l)

		//System.out.println(Address_.country.getJavaMember().getName());
		//System.out.println(Address_.country.getJavaMember().getDeclaringClass());
		
		fail();
		
	}
	
}
