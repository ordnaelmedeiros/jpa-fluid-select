package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

public interface OperationEqual<ObjBack, SelectTable, Table, Type> extends OperationBase<ObjBack, SelectTable, Table, Type> {
	
	/**
	 * Execute operation equal ( = )
	 * <ul>
	 * <li>JPQL: e.firstName = 'Bob'
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> equal(Type value) {
		this.setSql(this.toSql() + " = :" + this.createParam(value));
		return end();
	}
	
	/**
	 * Execute operation equal ( = )
	 * <ul>
	 * <li>JPQL: e.firstName = 'Bob'
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> eq(Type value) {
		return this.equal(value);
	}
	
	/**
	 * Execute operation equal ( = )
	 * <ul>
	 * <li>JPQL: e.firstName = f.firstName
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> equal(FieldRef<?> field) {
		this.setSql(this.toSql() + " = " + field.getSql());
		return this.end();
	}
	
	/**
	 * Execute operation equal ( = )
	 * <ul>
	 * <li>JPQL: e.firstName = f.firstName
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> eq(FieldRef<?> field) {
		return this.equal(field);
	}
	
}
