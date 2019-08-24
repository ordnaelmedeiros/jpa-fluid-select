package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.FieldOperation;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

public interface OperationTransformTime<ObjBack, SelectTable> {
	
	Operations<ObjBack, SelectTable> getOperations();
	String toSql();
	
	/**
	 * Extracting date parts HOUR.
	 * <ul>
	 * <li>JPQL: HOUR({t '23:59:00'})
	 * <li>in the example is evaluated for 23
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/date">www.objectdb.com</a>
	 * @return back
	 */
	default FieldOperation<ObjBack, SelectTable, Integer> hour() {
		return new FieldOperation<>(this.getOperations(), "HOUR("+this.toSql()+")");
	}

	/**
	 * Extracting date parts MINUTE.
	 * <ul>
	 * <li>JPQL: MINUTE({t '23:59:00'})
	 * <li>in the example is evaluated for 59
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/date">www.objectdb.com</a>
	 * @return back
	 */
	default FieldOperation<ObjBack, SelectTable, Integer> minute() {
		return new FieldOperation<>(this.getOperations(), "MINUTE("+this.toSql()+")");
	}

	/**
	 * Extracting date parts SECOND.
	 * <ul>
	 * <li>JPQL: SECOND({t '23:59:00'})
	 * <li>in the example is evaluated for 00
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/date">www.objectdb.com</a>
	 * @return back
	 */
	default FieldOperation<ObjBack, SelectTable, Integer> second() {
		return new FieldOperation<>(this.getOperations(), "SECOND("+this.toSql()+")");
	}
	
	
}
