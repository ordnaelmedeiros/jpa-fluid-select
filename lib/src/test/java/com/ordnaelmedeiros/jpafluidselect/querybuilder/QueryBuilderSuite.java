package com.ordnaelmedeiros.jpafluidselect.querybuilder;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.example.ExampleTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.order.OrderByAttribute;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.order.OrderStringFieldTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.order.date.OrderByDateTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.order.time.OrderByTimeTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.results.PrintTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.results.ReturnTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.transform.string.TransformStringTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.where.WhereCastTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.where.WhereDateTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.where.WhereEqTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.where.WhereTest;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.where.WhereTimeTest;

@RunWith(Suite.class)
@SuiteClasses({
	PrintTest.class,
	ReturnTest.class,
	WhereTest.class,
	ExampleTest.class,
	TransformStringTest.class,
	OrderStringFieldTest.class,
	OrderByAttribute.class,
	OrderByDateTest.class,
	OrderByTimeTest.class,
	WhereDateTest.class,
	WhereTimeTest.class,
	WhereCastTest.class,
	WhereEqTest.class
})
public class QueryBuilderSuite {
	
}
