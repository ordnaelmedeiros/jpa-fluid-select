package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

public interface OperationNotEqual<ObjBack, SelectTable, Table, Type> extends OperationBase<ObjBack, SelectTable, Table, Type> {
	
	/**
	 * Execute operation equal ( != )
	 * <ul>
	 * <li>JPQL: e.firstName != 'Bob'
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> notEqual(Type value) {
		this.setSql(this.toSql() + " != :" + this.createParam(value));
		return end();
	}
	
	/**
	 * Execute operation equal ( != )
	 * <ul>
	 * <li>JPQL: e.firstName != 'Bob'
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> ne(Type value) {
		return this.notEqual(value);
	}
	
	/**
	 * Execute operation equal ( != )
	 * <ul>
	 * <li>JPQL: e.firstName != f.firstName
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> notEqual(FieldRef<?> field) {
		this.setSql(this.toSql() + " != " + field.getSql());
		return this.end();
	}
	
	/**
	 * Execute operation equal ( != )
	 * <ul>
	 * <li>JPQL: e.firstName != f.firstName
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> ne(FieldRef<?> field) {
		return this.notEqual(field);
	}
	
}
