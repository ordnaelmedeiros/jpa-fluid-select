package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms;

public interface FieldTransformDate<Back> {

	void setSql(String sql);
	String getSql();
	
	Back end();
	
	/**
	 * Extracting date parts YEAR.
	 * <ul>
	 * <li>JPQL: YEAR({d '2011-12-31'})
	 * <li>in the example is evaluated for 2011
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/date">www.objectdb.com</a> 
	 * @return back
	 */
	default Back year() {
		this.setSql("YEAR(" + this.getSql() + ")");
		return end();
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
	default Back month() {
		this.setSql("MONTH(" + this.getSql() + ")");
		return end();
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
	default Back day() {
		this.setSql("DAY(" + this.getSql() + ")");
		return end();
	}
	
}
