package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.SingularAttribute;

import com.github.ordnaelmedeiros.jpafluidselect.redirect.RedirectFrom;

public class GroupBy<T, D, R> extends RedirectFrom<T, R> {

	private com.github.ordnaelmedeiros.jpafluidselect.From<T, R> back;
	private From<T, D> from;

	public GroupBy(From<T, D> from, com.github.ordnaelmedeiros.jpafluidselect.From<T, R> jfsForm) {
		super(jfsForm);
		this.from = from;
		this.back = jfsForm;
	}

	public com.github.ordnaelmedeiros.jpafluidselect.From<T, R> end() {
		return this.back;
	}

	private List<Expression<?>> groups = new ArrayList<>();

	public GroupBy<T, D, R> add(SingularAttribute<D, ?> attribute) {
		groups.add(from.get(attribute));
		return this;
	}

	public <J, Y, A> GroupBy<T, D, R> add(Join<J, Y> join, SingularAttribute<Y, A> attribute) {
		groups.add(join.get(attribute));
		return this;
	}
	
	public GroupBy<T, D, R> add(Path<String> path) {
		groups.add(path);
		return this;
	}

	public boolean isEmpty() {
		return this.groups.isEmpty();
	}

	public List<Expression<?>> getList() {
		return groups;
	}

}
