package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

public interface OperationNotEqualOrIsNull<ObjBack, SelectTable, Table, Type> extends OperationBase<ObjBack, SelectTable, Table, Type> {

	/**
	 * Execute operation (NOT EQUAL <em>val</em> OR IS NULL)
	 * <ul>
	 * <li>JPQL: (e.firstName != 'Bob' or e.firstName is null)
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> notEqualOrIsNull(Type value) {

		this.setSql("(" + this.toSql() + " != :" + this.createParam(value) + " OR " + this.toSql() + " IS NULL)");
		return end();
	}

	/**
	 * Execute operation (NOT EQUAL <em>val</em> OR IS NULL)
	 * <ul>
	 * <li>JPQL: (e.firstName != 'Bob' or e.firstName is null)
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> neOrNull(Type value) {
		return this.notEqualOrIsNull(value);
	}

	/**
	 * Execute operation (NOT EQUAL <em>val</em> OR IS NULL)
	 * <ul>
	 * <li>JPQL: (e.firstName != f.firstName or e.firstName is null)
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> notEqualOrIsNull(FieldRef<?> field) {
		this.setSql("(" + this.toSql() + " != " + field.getSql()  + " OR " + this.toSql() + " IS NULL)");
		return this.end();
	}

	/**
	 * Execute operation (NOT EQUAL <em>val</em> OR IS NULL)
	 * <ul>
	 * <li>JPQL: (e.firstName != f.firstName or e.firstName is null)
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> neOrNull(FieldRef<?> field) {
		return this.notEqualOrIsNull(field);
	}

}
