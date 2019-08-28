package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.transforms;

import java.util.StringJoiner;

import javax.persistence.metamodel.Attribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.FieldOperation;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.FieldRef;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.Ref;

public interface OperationTransformString<ObjBack, SelectTable, Table> {
	
	Operations<ObjBack, SelectTable, Table> getOperations();
	String toSql();
	
	/**
	 * Execute string function TRIM.
	 * <ul>
	 * <li>JPQL: TRIM(' UK ')
	 * <li>in the example is evaluated to 'UK'
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/string#TRIM___Stripping_Leading_and_Trailing_Characters">www.objectdb.com</a> 
	 * @return back
	 */
	default FieldOperation<ObjBack, SelectTable, Table, String> trim() {
		return new FieldOperation<>(this.getOperations(), "TRIM("+this.toSql()+")");
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
	default FieldOperation<ObjBack, SelectTable, Table, String> lower() {
		return new FieldOperation<>(this.getOperations(), "LOWER("+this.toSql()+")");
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
	default FieldOperation<ObjBack, SelectTable, Table, String> upper() {
		return new FieldOperation<>(this.getOperations(), "UPPER("+this.toSql()+")");
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
	default FieldOperation<ObjBack, SelectTable, Table, Integer> length() {
		return new FieldOperation<>(this.getOperations(), "LENGTH("+this.toSql()+")");
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
	default FieldOperation<ObjBack, SelectTable, Table, Integer> locate(String value) {
		return new FieldOperation<>(this.getOperations(), "LOCATE('"+value+"', "+this.toSql()+")");
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
	default FieldOperation<ObjBack, SelectTable, Table, Integer> locate(String value, Integer start) {
		return new FieldOperation<>(this.getOperations(), "LOCATE('"+value+"', "+this.toSql()+", "+start+")");
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
	default FieldOperation<ObjBack, SelectTable, Table, String> substring(Integer start) {
		return new FieldOperation<>(this.getOperations(), "SUBSTRING("+this.toSql()+", "+start+")");
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
	default FieldOperation<ObjBack, SelectTable, Table, String> substring(Integer start, Integer length) {
		return new FieldOperation<>(this.getOperations(), "SUBSTRING("+this.toSql()+", "+start+", "+length+")");
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
	default FieldOperation<ObjBack, SelectTable, Table, String> concat(String ...values) {
		StringJoiner sj = new StringJoiner(", ", "CONCAT(", ")");
		sj.add(this.toSql());
		for (String s : values) {
			sj.add("'"+s+"'");
		}
		return new FieldOperation<>(this.getOperations(), sj.toString());
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
	default FieldOperation<ObjBack, SelectTable, Table, String>  concat(Ref<?> ref, String att) {
		return new FieldOperation<>(this.getOperations(), "CONCAT("+this.toSql()+", "+ref.getAlias()+"."+att+")");
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
	default <T> FieldOperation<ObjBack, SelectTable, Table, String> concat(Ref<T> ref, Attribute<T, ?> att) {
		return new FieldOperation<>(this.getOperations(), "CONCAT("+this.toSql()+", "+ref.getAlias()+"."+att.getName()+")");
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
	default FieldOperation<ObjBack, SelectTable, Table, String> concat(FieldRef<?> field) {
		return new FieldOperation<>(this.getOperations(), "CONCAT("+this.toSql()+", "+field.getSql()+")");
	}
	
}
