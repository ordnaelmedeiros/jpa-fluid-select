package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms;

public interface FieldTransformCast<Back> {

	void setSql(String sql);
	String getSql();
	
	Back end();
	
	default Back cast(Class<?> klass) {
		this.setSql("CAST("+this.getSql()+" AS "+klass.getName()+")");
		return end();
	}
	
}
