package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;

import lombok.Getter;

public class Operation implements ToSql {

	@Getter
	private String field;
	
	@Getter
	private List<String> parameters = new ArrayList<>();
	
	@Getter
	private Comparator comparator;
	
	@Getter
	private List<Object> values = new ArrayList<>();

	private boolean not;
	
	public String getParameter() {
		return this.parameters.get(0);
	}
	public Object getValue() {
		return this.values.get(0);
	}
	
	private Operation field(String field) {
		this.field = field;
		return this;
	}
	private Operation parameters(List<String> parameters) {
		this.parameters = parameters;
		return this;
	}
//	private Operation parameter(String parameter) {
//		this.parameters.add(parameter);
//		return this;
//	}
	private Operation comparator(Comparator comparator) {
		this.comparator = comparator;
		return this;
	}
	private Operation values(List<Object> values) {
		this.values = values;
		return this;
	}
	private Operation value(Object value) {
		this.values.add(value);
		return this;
	}
	
	private Operation generateParameters(QueryBuilder qb) {
		for (int i=0; i<this.values.size(); i++) {
			this.parameters.add(qb.createParameter(this.field));
		}
		return this;
	}
	
	public String toComparator() {
		if (not) {
			return " NOT ("+this.comparator.toString(this)+")";
		} else {
			return this.comparator.toString(this);
		}
	}
	
	public void createParameter(Query query) {
		for (int i = 0; i < parameters.size(); i++) {
			query.setParameter(parameters.get(i), values.get(i));
		}
	}
	
	public Operation cast(String cast) {
		if (cast !=null) {
			this.field = "cast("+this.field+" as "+cast+")";
		}
		return this;
	}
	public Operation extract(String extract) {
		if (extract !=null) {
			this.field = "extract("+extract+" from "+this.field+")";
		}
		return this;
	}
	public Operation not(boolean not) {
		this.not = not;
		return this;
	}
	
	
	public static Operation equal(QueryBuilder qb, String field, Object value) {
		return new Operation()
				.field(field)
				.comparator(Comparator.EQUAL)
				.value(value)
				.generateParameters(qb);
	}
	
	public static Operation greaterThan(QueryBuilder qb, String field, Object value) {
		return new Operation()
				.field(field)
				.comparator(Comparator.GREATER_THAN)
				.value(value)
				.generateParameters(qb);
	}
	
	public static Operation lessThan(QueryBuilder qb, String field, Object value) {
		return new Operation()
				.field(field)
				.comparator(Comparator.LESS_THAN)
				.value(value)
				.generateParameters(qb);
	}
	@Override
	public String toSql() {
		return this.toComparator();
	}
	
	@Override
	public List<Operation> getOperations() {
		return Arrays.asList(this);
	}
	
}
