package com.ordnaelmedeiros.jpafluidselect.querybuilder.join;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.Employee;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.EmployeePhone;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.EmployeePhone_;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.Employee_;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.Ref;

public class JoinTest {
	
	private static EntityManagerFactory emf;
	public EntityManager em;
	public QueryBuilder queryBuilder;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("h2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.createQuery("delete from Employee").executeUpdate();
		em.createQuery("delete from EmployeePhone").executeUpdate();
		
		em.persist(Employee.builder()
				.id(1)
				.name("Leandro")
				.birth(LocalDate.of(1986, 9, 17))
				.phones(Arrays.asList(
						EmployeePhone.builder().id(1).number("555").build()))
				.build());
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void joinString() {
		
		Ref<Employee> from = new Ref<>();
		Ref<EmployeePhone> refPhones = new Ref<>();
		
		
		List<Object[]> result = queryBuilder
			.select(Employee.class).ref(from)
			
			.leftJoin(Employee_.phones).ref(refPhones)
			.end()
			
			.fields()
				.field(Employee_.id).add()
				.field(Employee_.name).add()
				.field(refPhones.field(EmployeePhone_.number)).add()
			.where()
				.field(Employee_.id).eq(1)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		//assertThat(result.size(), is(1));
		//assertThat(result.get(0)[0], is(1));
		
		//CoreMatchers.
	}
	
}
