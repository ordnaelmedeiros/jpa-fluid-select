package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

public interface OperationGreaterOrEqualThan<ObjBack, SelectTable, Table, Type> extends OperationBase<ObjBack, SelectTable, Table, Type> {
	
	/**
	 * Execute operation equal ( >= )
	 * <ul>
	 * <li>JPQL: e.salary >= 100000
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> greaterThanOrEqual(Type value) {
		this.setSql(this.toSql() + " >= :" + this.createParam(value));
		return end();
	}
	
	/**
	 * Execute operation equal ( >= )
	 * <ul>
	 * <li>JPQL: e.salary >= 100000
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> ge(Type value) {
		return this.greaterThanOrEqual(value);
	}
	
	/**
	 * Execute operation equal ( >= )
	 * <ul>
	 * <li>JPQL: e.salary >= e.salary
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> greaterThanOrEqual(FieldRef<?> field) {
		this.setSql(this.toSql() + " >= " + field.getSql());
		return this.end();
	}
	
	/**
	 * Execute operation equal ( >= )
	 * <ul>
	 * <li>JPQL: e.salary >= e.salary
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> ge(FieldRef<?> field) {
		return this.greaterThanOrEqual(field);
	}
	
}
