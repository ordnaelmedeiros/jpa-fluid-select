package com.ordnaelmedeiros.jpafluidselect.querybuilder.join;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.LazyInitializationException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.Employee;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.EmployeePhone;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.EmployeePhone_;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.models.Employee_;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
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
		
		/*
		em.persist(Employee.builder()
				.id(1)
				.name("Leandro")
				.birth(LocalDate.of(1986, 9, 17))
				.phones(Arrays.asList(
						EmployeePhone.builder().id(1).number("5546999145929").build()))
				.build());
		*/
		
		Employee employee = Employee.builder()
			.id(1)
			.name("Leandro")
			.birth(LocalDate.of(1986, 9, 17))
			.build();
		
		employee.setPhones(Arrays.asList(
				EmployeePhone.builder()
					.id(1)
					.employee(employee)
					.number("5546999145929")
				.build(),
				EmployeePhone.builder()
					.id(2)
					.employee(employee)
					.number("5546999145930")
				.build()
			));
		
		em.persist(employee);
		
		em.getTransaction().commit();
		
	}
	
	@Before
	public void before() {
		
		em = emf.createEntityManager();
		queryBuilder = new QueryBuilder(em);
		
	}
	
	@Test
	public void selectEmpJoinPhone() {
		
		Ref<EmployeePhone> refPhones = new Ref<>();
		
		List<Object[]> result = queryBuilder
			.select(Employee.class)
			.innerJoin(Employee_.phones).ref(refPhones)
				.on()
					.field(EmployeePhone_.id).eq(1)
				.end()
				.leftJoin(EmployeePhone_.employee)
			.fields()
				.field(Employee_.id).add()
				.field(Employee_.name).add()
				.field(refPhones.field(EmployeePhone_.number)).add()
			.where()
				.field(Employee_.id).eq(1)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		assertThat(result.get(0)[0], is(1));
		assertThat(result.get(0)[1], is("Leandro"));
		assertThat(result.get(0)[2], is("5546999145929"));
		
		//CoreMatchers.
	}
	
	@Test
	public void selectEmpJoinPhoneRef() {
		
		Ref<EmployeePhone> refPhones = new Ref<>();
		
		List<Object[]> result = queryBuilder
			.select(Employee.class)
			.innerJoin(Employee_.phones).ref(refPhones)
			.fields()
				.field(Employee_.id).add()
				.field(Employee_.name).add()
				.field(refPhones.field(EmployeePhone_.number)).add()
			.where()
				.field(Employee_.id).eq(1)
				.field(refPhones, EmployeePhone_.id).eq(1)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		assertThat(result.get(0)[0], is(1));
		assertThat(result.get(0)[1], is("Leandro"));
		assertThat(result.get(0)[2], is("5546999145929"));
		
		//CoreMatchers.
	}
	

	@Test
	public void selectEmpJoinPhoneRef2() {
		
		Ref<EmployeePhone> refPhones = new Ref<>();
		
		Select<Employee> select = queryBuilder
			.select(Employee.class);
		List<Employee> result = select
			.innerJoin(Employee_.phones).ref(refPhones)
			.fields()
				.field(Employee_.id).add()
				.field(Employee_.name).add()
				.field(refPhones.field(EmployeePhone_.number)).add()
			.where()
				.field(Employee_.id).eq(1)
				.field(refPhones, EmployeePhone_.id).eq(1)
			.print()
			.getResultListByReflect(Employee.class);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		//assertThat(result.get(0)[0], is(1));
		//assertThat(result.get(0)[1], is("Leandro"));
		//assertThat(result.get(0)[2], is("5546999145929"));
		
		//CoreMatchers.
	}
	
	@Test
	public void selectPhoneJoinEmp() {
		
		Ref<Employee> refEmp = new Ref<>();
		
		List<Object[]> result = queryBuilder
			.select(EmployeePhone.class)
			.leftJoin(EmployeePhone_.employee).ref(refEmp).end()
			.fields()
				.field(refEmp.field(Employee_.id)).add()
				.field(refEmp.field(Employee_.name)).upper().add()
				.field(EmployeePhone_.number).add()
			.where()
				.field(EmployeePhone_.id).eq(1)
			.print()
			.getResultObjects();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), is(1));
		assertThat(result.get(0)[0], is(1));
		assertThat(result.get(0)[1], is("LEANDRO"));
		assertThat(result.get(0)[2], is("5546999145929"));
		
	}

	@Test
	public void expectLazyInitializationExceptionFromDetachList() {

		Employee employee = new QueryBuilder(em)
			.select(Employee.class)
			.innerJoin(Employee_.phones).end()
			.maxResults(1)
			.getSingleResult();

		em.detach(employee);
		try {
			employee.getPhones().get(0);
			fail("should have thrown a LazyInitializationException");
		} catch(LazyInitializationException e) {
			// Expected result
		}
	}

	@Test
	public void fetchJoin() {

		Employee employee = new QueryBuilder(em)
			.select(Employee.class)
			.innerJoin(Employee_.phones).fetch().end()
			.maxResults(1)
			.getSingleResult();

		em.detach(employee);
		EmployeePhone phone = employee.getPhones().get(0);
		assertThat(phone, notNullValue());
	}

}
