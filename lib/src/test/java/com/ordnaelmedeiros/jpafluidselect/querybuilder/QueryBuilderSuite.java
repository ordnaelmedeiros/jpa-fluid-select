package com.ordnaelmedeiros.jpafluidselect.querybuilder;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.example.ExampleTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.order.OrderStringFieldTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.results.PrintTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.results.ReturnTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.where.WhereTest;

@RunWith(Suite.class)
@SuiteClasses({
	PrintTest.class,
	ReturnTest.class,
	WhereTest.class,
	ExampleTest.class,
	OrderStringFieldTest.class
})
public class QueryBuilderSuite {
	
}
