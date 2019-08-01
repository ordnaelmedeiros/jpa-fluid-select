package com.ordnaelmedeiros.jpafluidselect.querybuilder;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.example.ExampleTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.order.OrderTest;

@RunWith(Suite.class)
@SuiteClasses({
	ExampleTest.class,
	OrderTest.class
})
public class QueryBuilderSuite {
	
}
