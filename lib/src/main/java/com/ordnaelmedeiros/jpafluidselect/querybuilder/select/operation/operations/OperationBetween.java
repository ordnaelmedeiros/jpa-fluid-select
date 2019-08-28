package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

public interface OperationBetween<ObjBack, SelectTable, Table, Type> extends OperationBase<ObjBack, SelectTable, Table, Type> {
	
	/**
	 * Execute operation BETWEEN
	 * <ul>
	 * <li>JPQL: e.firstName BETWEEN 'A' AND 'C'
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable,Table> between(Type value1, Type value2) {
		this.setSql(this.toSql() + " BETWEEN :" + this.createParam(value1) + " AND :" + this.createParam(value2));
		return end();
	}
	
	/**
	 * Execute operation BETWEEN
	 * <ul>
	 * <li>JPQL: e.salary BETWEEN f.salary AND g.salary
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable,Table> between(FieldRef<?> field1, FieldRef<?> field2) {
		this.setSql(this.toSql() + " BETWEEN " + field1.getSql() + " AND " + field2.getSql());
		return this.end();
	}

	/**
	 * Execute operation BETWEEN
	 * <ul>
	 * <li>JPQL: e.salary BETWEEN 'A' AND g.salary
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable,Table> between(Type value1, FieldRef<?> field2) {
		this.setSql(this.toSql() + " BETWEEN :" + this.createParam(value1) + " AND " + field2.getSql());
		return this.end();
	}
	

	/**
	 * Execute operation BETWEEN
	 * <ul>
	 * <li>JPQL: e.salary BETWEEN f.salary AND g.salary
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable,Table> between(FieldRef<?> field1, Type value2) {
		this.setSql(this.toSql() + " BETWEEN " + field1.getSql() + " AND :" + this.createParam(value2));
		return this.end();
	}
	
	
}
