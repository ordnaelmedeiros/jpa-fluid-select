package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms;

import java.util.StringJoiner;

import javax.persistence.metamodel.Attribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.Ref;

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
	
	/**
	 * Execute string function LOCATE.
	 * <ul>
	 * <li>JPQL: LOCATE('a', 'India') 
	 * <li>in the example is evaluated to 5
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#LOCATE___Locating_Substrings">www.objectdb.com</a> 
	 * @return back
	 */
	default Back locate(String value) {
		this.setSql("LOCATE('"+value+"', "+this.getSql()+")");
		return end();
	}
	
	/**
	 * Execute string function LOCATE.
	 * <ul>
	 * <li>JPQL: LOCATE('a', 'Japan', 3) 
	 * <li>in the example is evaluated to 4
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#LOCATE___Locating_Substrings">www.objectdb.com</a> 
	 * @return back
	 */
	default Back locate(String value, Integer start) {
		this.setSql("LOCATE('"+value+"', "+this.getSql()+", "+start+")");
		return end();
	}

	/**
	 * Execute string function SUBSTRING.
	 * <ul>
	 * <li>JPQL: SUBSTRING('Italy', 3)
	 * <li>in the example is evaluated to 'aly'
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#SUBSTRING___Getting_a_Portion_of_a_String">www.objectdb.com</a> 
	 * @return back
	 */
	default Back substring(Integer start) {
		this.setSql("SUBSTRING("+this.getSql()+", "+start+")");
		return end();
	}
	
	/**
	 * Execute string function SUBSTRING.
	 * <ul>
	 * <li>JPQL: SUBSTRING('Italy', 3, 2)
	 * <li>in the example is evaluated to 'al'
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#SUBSTRING___Getting_a_Portion_of_a_String">www.objectdb.com</a> 
	 * @return back
	 */
	default Back substring(Integer start, Integer length) {
		this.setSql("SUBSTRING("+this.getSql()+", "+start+", "+length+")");
		return end();
	}
	
	/**
	 * Execute string function CONCAT.
	 * <ul>
	 * <li>JPQL: CONCAT('Serbia', ' and ', 'Montenegro')
	 * <li>in the example is evaluated to 'Serbia and Montenegro'
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#CONCAT___String_Concatenation">www.objectdb.com</a> 
	 * @return back
	 */
	default Back concat(String ...values) {
		StringJoiner sj = new StringJoiner(", ", "CONCAT(", ")");
		sj.add(this.getSql());
		for (String s : values) {
			sj.add("'"+s+"'");
		}
		this.setSql(sj.toString());
		return end();
	}
	
	/**
	 * Execute string function CONCAT.
	 * <ul>
	 * <li>JPQL: CONCAT('Serbia', ' and ', 'Montenegro')
	 * <li>in the example is evaluated to 'Serbia and Montenegro'
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#CONCAT___String_Concatenation">www.objectdb.com</a> 
	 * @return back
	 */
	default Back concat(Ref<?> ref, String att) {
		this.setSql("CONCAT("+this.getSql()+", "+ref.getAlias()+"."+att+")");
		return end();
	}
	
	/**
	 * Execute string function CONCAT.
	 * <ul>
	 * <li>JPQL: CONCAT('Serbia', ' and ', 'Montenegro')
	 * <li>in the example is evaluated to 'Serbia and Montenegro'
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#CONCAT___String_Concatenation">www.objectdb.com</a> 
	 * @return back
	 */
	default <T> Back concat(Ref<T> ref, Attribute<T, ?> att) {
		this.setSql("CONCAT("+this.getSql()+", "+ref.getAlias()+"."+att.getName()+")");
		return end();
	}

	/**
	 * Execute string function CONCAT.
	 * <ul>
	 * <li>JPQL: CONCAT('Serbia', ' and ', 'Montenegro')
	 * <li>in the example is evaluated to 'Serbia and Montenegro'
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#CONCAT___String_Concatenation">www.objectdb.com</a> 
	 * @return back
	 */
	default <T> Back concat(FieldRef<?> field) {
		this.setSql("CONCAT("+this.getSql()+", "+field.getSql()+")");
		return end();
	}
	
}
