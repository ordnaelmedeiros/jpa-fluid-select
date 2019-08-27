package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

public interface OperationGreaterThan<ObjBack, SelectTable, Type> extends OperationBase<ObjBack, SelectTable, Type> {
	
	/**
	 * Execute operation equal ( > )
	 * <ul>
	 * <li>JPQL: e.salary > 100000
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> greaterThan(Type value) {
		this.createParam(value);
		this.setSql(this.toSql() + " > :" + this.getParam());
		return end();
	}
	
	/**
	 * Execute operation equal ( > )
	 * <ul>
	 * <li>JPQL: e.salary > 100000
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> gt(Type value) {
		return this.greaterThan(value);
	}
	
	/**
	 * Execute operation equal ( > )
	 * <ul>
	 * <li>JPQL: e.salary > e.salary
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> greaterThan(FieldRef<?> field) {
		this.setSql(this.toSql() + " < " + field.getSql());
		return this.end();
	}
	
	/**
	 * Execute operation equal ( > )
	 * <ul>
	 * <li>JPQL: e.salary > e.salary
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> gt(FieldRef<?> field) {
		return this.greaterThan(field);
	}
	
}
