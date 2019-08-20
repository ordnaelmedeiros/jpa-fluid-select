package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms;

public interface FieldTransformTime<Back> {

	void setSql(String sql);
	String getSql();
	
	Back end();
	
	/**
	 * Extracting date parts HOUR.
	 * <ul>
	 * <li>JPQL: HOUR({t '23:59:00'})
	 * <li>in the example is evaluated for 23
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/date">www.objectdb.com</a>
	 * @return back FieldOrder
	 */
	default Back hour() {
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
	default Back minute() {
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
	default Back second() {
		this.setSql("SECOND(" + this.getSql() + ")");
		return end();
	}
	
}
