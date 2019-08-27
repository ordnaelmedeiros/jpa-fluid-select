package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

public interface OperationIsNull<ObjBack, SelectTable, Type> extends OperationBase<ObjBack, SelectTable, Type> {
	
	/**
	 * Execute operation IS NULL
	 * <ul>
	 * <li>JPQL: e.endDate IS NULL
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable> isNull() {
		this.setSql(this.toSql() + " IS NULL");
		return end();
	}
	
}
