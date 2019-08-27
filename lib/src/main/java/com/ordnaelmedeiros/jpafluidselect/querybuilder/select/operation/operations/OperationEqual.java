package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

public interface OperationEqual<ObjBack, SelectTable, Type> extends OperationBase<ObjBack, SelectTable, Type> {
	
	/**
	 * Execute operation equal ( = )
	 * <ul>
	 * <li>JPQL: e.firstName = 'Bob'
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> equal(Type value) {
		this.createParam(value);
		this.setSql(this.toSql() + " = :" + this.getParam());
		return end();
	}
	
	/**
	 * Execute operation equal ( = )
	 * <ul>
	 * <li>JPQL: e.firstName = 'Bob'
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> eq(Type value) {
		return this.equal(value);
	}
	
	/**
	 * Execute operation equal ( = )
	 * <ul>
	 * <li>JPQL: e.firstName = f.firstName
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> equal(FieldRef<?> field) {
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
	default Operations<ObjBack,SelectTable> eq(FieldRef<?> field) {
		return this.equal(field);
	}
	
}
