package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms;

public interface FieldTransformTotals<Back> {

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
	default Back count() {
		this.setSql("COUNT("+this.getSql()+")");
		return end();
	}
	
	default Back countDistinct() {
		this.setSql("COUNT(DISTINCT "+this.getSql()+")");
		return end();
	}
	
}
