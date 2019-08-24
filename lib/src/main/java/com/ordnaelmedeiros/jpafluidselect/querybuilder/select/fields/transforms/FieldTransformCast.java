package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms;

public interface FieldTransformCast<Back> {

	void setSql(String sql);
	String getSql();
	
	Back end();
	
	/**
	 * Execute function CAST.
	 * <ul>
	 * <li>JPQL: CAST('2019-01-01 16:30' AS LocalDate)
	 * <li>in the example is evaluated to '2019-01-01'
	 * </ul>
	 * @return back
	 */
	default Back cast(Class<?> klass) {
		this.setSql("CAST("+this.getSql()+" AS "+klass.getName()+")");
		return end();
	}
	
}
