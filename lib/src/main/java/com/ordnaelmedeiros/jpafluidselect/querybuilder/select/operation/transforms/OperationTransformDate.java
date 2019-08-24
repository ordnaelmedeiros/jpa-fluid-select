package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.FieldOperation;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;

public interface OperationTransformDate<ObjBack, SelectTable> {
	
	Operations<ObjBack, SelectTable> getOperations();
	String toSql();
	
	/**
	 * Extracting date parts YEAR.
	 * <ul>
	 * <li>JPQL: YEAR({d '2011-12-31'})
	 * <li>in the example is evaluated for 2011
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/date">www.objectdb.com</a> 
	 * @return back
	 */
	default FieldOperation<ObjBack, SelectTable, Integer> year() {
		return new FieldOperation<>(this.getOperations(), "YEAR("+this.toSql()+")");
	}

	/**
	 * Extracting date parts MONTH.
	 * <ul>
	 * <li>JPQL: MONTH({d '2011-12-31'})
	 * <li>in the example is evaluated for 12
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/date">www.objectdb.com</a>
	 * @return back
	 */
	default FieldOperation<ObjBack, SelectTable, Integer> month() {
		return new FieldOperation<>(this.getOperations(), "MONTH("+this.toSql()+")");
	}
	
	/**
	 * Extracting date parts DAY.
	 * <ul>
	 * <li>JPQL: DAY({d '2011-12-31'})
	 * <li>in the example is evaluated for 31
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/date">www.objectdb.com</a>
	 * @return back
	 */
	default FieldOperation<ObjBack, SelectTable, Integer> day() {
		return new FieldOperation<>(this.getOperations(), "DAY("+this.toSql()+")");
	}
	
}
