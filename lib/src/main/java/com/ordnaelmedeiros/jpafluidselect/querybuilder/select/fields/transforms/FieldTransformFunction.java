package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms;

public interface FieldTransformFunction<Back> {

	void setSql(String sql);
	String getSql();
	
	Back end();
	
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
	default Back function(String function) {
		
		this.setSql(function.replace(":field", this.getSql()));
		return end();
	}
	
	
}
