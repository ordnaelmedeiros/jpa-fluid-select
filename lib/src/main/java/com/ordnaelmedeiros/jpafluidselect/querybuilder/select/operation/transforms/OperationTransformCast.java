package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.FieldOperation;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

public interface OperationTransformCast<ObjBack, SelectTable> {
	
	Operations<ObjBack, SelectTable> getOperations();
	String toSql();
	
	/**
	 * Execute function CAST.
	 * <ul>
	 * <li>JPQL: CAST('2019-01-01 16:30' AS LocalDate)
	 * <li>in the example is evaluated to '2019-01-01'
	 * </ul>
	 * @return back
	 */
	default <T> FieldOperation<ObjBack, SelectTable, T> cast(Class<T> klass) {
		return new FieldOperation<>(this.getOperations(), "CAST("+this.toSql()+" AS "+klass.getName()+")");
	}

}
