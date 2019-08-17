package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.transforms;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.FieldOrder;

public interface FieldOrderTransformCast<SelectTable> {

	void setSql(String sql);
	String getSql();
	
	FieldOrder<SelectTable> end();
	
	default FieldOrder<SelectTable> cast(Class<?> klass) {
		this.setSql("CAST("+this.getSql()+" AS "+klass.getName()+")");
		return end();
	}
	
	
}
