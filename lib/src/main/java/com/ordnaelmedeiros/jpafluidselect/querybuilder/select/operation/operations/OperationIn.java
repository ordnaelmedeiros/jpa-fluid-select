package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import java.util.Arrays;
import java.util.Collection;
import java.util.StringJoiner;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

@SuppressWarnings("unchecked")
public interface OperationIn<ObjBack, SelectTable, Table, Type> extends OperationBase<ObjBack, SelectTable, Table, Type> {
	
	/**
	 * Execute operation IN
	 * <ul>
	 * <li>JPQL: e.firstName IN ('Bob', 'Fred', 'Joe')
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> in(Type ...value) {
		this.in(Arrays.asList(value));
		return end();
	}
	
	/**
	 * Execute operation IN
	 * <ul>
	 * <li>JPQL: e.firstName IN ('Bob', 'Fred', 'Joe')
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> in(Collection<Type> value) {
		this.setSql(this.toSql() + " IN :" + this.createParam(value));
		return end();
	}
	
	/**
	 * Execute operation IN
	 * <ul>
	 * <li>JPQL: e.firstName IN ('Bob', 'Fred', 'Joe')
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> in(FieldRef<?> ...field) {
		StringJoiner sj = new StringJoiner(", ", "(", ")");
		for (FieldRef<?> f : field) {
			sj.add(f.getSql());
		}
		this.setSql(this.toSql() + " IN " + sj.toString());
		return this.end();
	}
	
}
