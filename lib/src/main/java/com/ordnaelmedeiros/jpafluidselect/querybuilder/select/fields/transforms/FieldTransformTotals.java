package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms;

public interface FieldTransformTotals<Back> {

	void setSql(String sql);
	String getSql();
	
	Back end();
	
	/**
	 * Execute function COUNT.
	 * @return back
	 */
	default Back count() {
		this.setSql("COUNT("+this.getSql()+")");
		return end();
	}
	
	/**
	 * Execute function COUNT DISTINCT.
	 * @return back
	 */
	default Back countDistinct() {
		this.setSql("COUNT(DISTINCT "+this.getSql()+")");
		return end();
	}
	
	/**
	 * Execute function SUM
	 * @return back
	 */
	default Back sum() {
		this.setSql("SUM("+this.getSql()+")");
		return end();
	}
	
	/**
	 * Execute function MIN
	 * @return back
	 */
	default Back min() {
		this.setSql("MIN("+this.getSql()+")");
		return end();
	}
	
	/**
	 * Execute function MAX
	 * @return back
	 */
	default Back max() {
		this.setSql("MAX("+this.getSql()+")");
		return end();
	}
	
	/**
	 * Execute function AVG
	 * @return back
	 */
	default Back avg() {
		this.setSql("AVG("+this.getSql()+")");
		return end();
	}
	
}
