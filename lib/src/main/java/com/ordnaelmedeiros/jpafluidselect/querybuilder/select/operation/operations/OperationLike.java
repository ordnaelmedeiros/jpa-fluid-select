package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

public interface OperationLike<ObjBack, SelectTable, Type> extends OperationBase<ObjBack, SelectTable, Type> {
	
	/**
	 * Execute operation LIKE
	 * <ul>
	 * <li>JPQL: e.firstName LIKE 'A%'
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> like(String value) {
		this.setSql(this.toSql() + " LIKE :" + this.createParam(value));
		return end();
	}
	
	/**
	 * Execute operation LIKE
	 * <ul>
	 * <li>JPQL: e.firstName LIKE f.firstName
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> like(FieldRef<?> field) {
		this.setSql(this.toSql() + " LIKE " + field.getSql());
		return this.end();
	}
	
	/**
	 * Execute operation LIKE and ignore case-sensitive
	 * <ul>
	 * <li>JPQL: UPPER(e.firstName) LIKE UPPER('A%')
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> ilike(String value) {
		this.setSql("UPPER("+this.toSql()+") LIKE :" + this.createParam(value.toUpperCase()));
		return end();
	}

	/**
	 * Execute operation LIKE and ignore case-sensitive 
	 * <ul>
	 * <li>JPQL: UPPER(e.firstName) LIKE UPPER(f.firstName)
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> ilike(FieldRef<?> field) {
		this.setSql("UPPER("+this.toSql()+") LIKE UPPER(" + field.getSql()+")");
		return this.end();
	}
	
}
