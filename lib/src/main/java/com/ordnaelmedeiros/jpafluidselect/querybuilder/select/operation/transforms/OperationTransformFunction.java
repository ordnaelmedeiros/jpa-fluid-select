package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.FieldOperation;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

public interface OperationTransformFunction<ObjBack, SelectTable, Table> {
	
	Operations<ObjBack, SelectTable, Table> getOperations();
	String toSql();
	
	/**
	 * Execute literal function.
	 * <ul>
	 * <li>Ex.:
	 * <li>.field(ObjDate_.date).function("TO_CHAR(:field, 'dd/MM/yyyy')").add()
	 * <li>JPQL: TO_CHAR(obj0_.date, 'dd/MM/yyyy')
	 * <li>in the example is evaluated to '01/01/2019'
	 * </ul>
	 * @return back
	 * @see <a href="https://vladmihalcea.com/hibernate-sql-function-jpql-criteria-api-query/">hibernate-sql-function-jpql</a>
	 */
	default <T> FieldOperation<ObjBack, SelectTable, Table, T> function(String function, Class<T> klass) {
		return new FieldOperation<>(this.getOperations(), function.replace(":field", this.toSql()));
	}
	
}
