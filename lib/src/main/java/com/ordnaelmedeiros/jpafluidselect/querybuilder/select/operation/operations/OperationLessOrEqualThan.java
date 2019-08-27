package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

public interface OperationLessOrEqualThan<ObjBack, SelectTable, Type> extends OperationBase<ObjBack, SelectTable, Type> {
	
	/**
	 * Execute operation equal ( <= )
	 * <ul>
	 * <li>JPQL: e.salary <= 100000
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> lessThanOrEqual(Type value) {
		this.setSql(this.toSql() + " <= :" + this.createParam(value));
		return end();
	}
	
	/**
	 * Execute operation equal ( <= )
	 * <ul>
	 * <li>JPQL: e.salary <= 100000
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> le(Type value) {
		return this.lessThanOrEqual(value);
	}
	
	/**
	 * Execute operation equal ( <= )
	 * <ul>
	 * <li>JPQL: e.salary <= e.salary
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> lessThanOrEqual(FieldRef<?> field) {
		this.setSql(this.toSql() + " <= " + field.getSql());
		return this.end();
	}
	
	/**
	 * Execute operation equal ( <= )
	 * <ul>
	 * <li>JPQL: e.salary <= e.salary
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> le(FieldRef<?> field) {
		return this.lessThanOrEqual(field);
	}
	
}
