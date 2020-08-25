package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.operations;

import java.util.Arrays;
import java.util.Collection;
import java.util.StringJoiner;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;

@SuppressWarnings("unchecked")
public interface OperationNotInOrIsNull<ObjBack, SelectTable, Table, Type> extends OperationBase<ObjBack, SelectTable, Table, Type> {

	/**
	 * Execute operation (NOT IN (...) OR IS NULL)
	 * <ul>
	 * <li>JPQL: (e.firstName NOT IN ('Bob', 'Fred', 'Joe') or e.firstName is null)
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> notInOrIsNull(Collection<Type> value) {
		this.setSql("(" + this.toSql() + " NOT IN :" + this.createParam(value) + " OR " + this.toSql() + " IS NULL)");
		return end();
	}

	/**
	 * Execute operation (NOT IN (...) OR IS NULL)
	 * <ul>
	 * <li>JPQL: (e.firstName NOT IN ('Bob', 'Fred', 'Joe') or e.firstName is null)
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> notInOrIsNull(Type ...value) {
		this.notInOrIsNull(Arrays.asList(value));
		return end();
	}

	/**
	 * Execute operation (NOT IN (...) OR IS NULL)
	 * <ul>
	 * <li>JPQL: (e.firstName NOT IN ('Bob', 'Fred', 'Joe') or e.firstName is null)
	 * </ul>
	 * @return back
	 */
	default Operations<ObjBack,SelectTable, Table> notInOrIsNull(FieldRef<?> ...field) {
		StringJoiner sj = new StringJoiner(", ", "(", ")");
		for (FieldRef<?> f : field) {
			sj.add(f.getSql());
		}
		this.setSql("(" + this.toSql() + " NOT IN " + sj.toString() + " OR " + this.toSql() + " IS NULL)");
		return this.end();
	}

}
