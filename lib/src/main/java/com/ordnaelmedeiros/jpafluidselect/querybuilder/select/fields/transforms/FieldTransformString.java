package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms;

public interface FieldTransformString<Back> {

	void setSql(String sql);
	String getSql();
	
	Back end();
	
	/**
	 * Execute string function TRIM.
	 * <ul>
	 * <li>JPQL: TRIM(' UK ')
	 * <li>in the example is evaluated to 'UK'
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#TRIM___Stripping_Leading_and_Trailing_Characters">www.objectdb.com</a> 
	 * @return back
	 */
	default Back trim() {
		this.setSql("TRIM(" + this.getSql() + ")");
		return end();
	}
	
	/**
	 * Execute string function LOWER.
	 * <ul>
	 * <li>JPQL: LOWER('Germany') 
	 * <li>in the example is evaluated to 'germany'
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#LOWER_and_UPPER___Changing_String_Case">www.objectdb.com</a> 
	 * @return back
	 */
	default Back lower() {
		this.setSql("LOWER(" + this.getSql() + ")");
		return end();
	}
	
	/**
	 * Execute string function UPPER.
	 * <ul>
	 * <li>JPQL: UPPER('Germany') 
	 * <li>in the example is evaluated to 'GERMANY'
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#LOWER_and_UPPER___Changing_String_Case">www.objectdb.com</a> 
	 * @return back
	 */
	default Back upper() {
		this.setSql("UPPER(" + this.getSql() + ")");
		return end();
	}

	/**
	 * Execute string function LENGTH.
	 * <ul>
	 * <li>JPQL: LENGTH('China') 
	 * <li>in the example is evaluated to 5
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#LENGTH___Counting_Characters_in_a_String">www.objectdb.com</a> 
	 * @return back
	 */
	default Back length() {
		this.setSql("LENGTH(" + this.getSql() + ")");
		return end();
	}
	
}
