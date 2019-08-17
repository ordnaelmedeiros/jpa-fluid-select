package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.transforms;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.FieldOrder;

public interface FieldOrderTransformTime<SelectTable> {

	void setSql(String sql);
	String getSql();
	
	FieldOrder<SelectTable> end();
	
	/**
	 * Extracting date parts HOUR.
	 * <ul>
	 * <li>JPQL: HOUR({t '23:59:00'})
	 * <li>in the example is evaluated for 23
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/date">www.objectdb.com</a>
	 * @return back FieldOrder
	 */
	default FieldOrder<SelectTable> hour() {
		this.setSql("HOUR(" + this.getSql() + ")");
		return end();
	}
	
	/**
	 * Extracting date parts MINUTE.
	 * <ul>
	 * <li>JPQL: MINUTE({t '23:59:00'})
	 * <li>in the example is evaluated for 59
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/date">www.objectdb.com</a>
	 * @return back FieldOrder
	 */
	default FieldOrder<SelectTable> minute() {
		this.setSql("MINUTE(" + this.getSql() + ")");
		return end();
	}
	
	/**
	 * Extracting date parts SECOND.
	 * <ul>
	 * <li>JPQL: SECOND({t '23:59:00'})
	 * <li>in the example is evaluated for 00
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/date">www.objectdb.com</a>
	 * @return back FieldOrder
	 */
	default FieldOrder<SelectTable> second() {
		this.setSql("SECOND(" + this.getSql() + ")");
		return end();
	}
	
}
