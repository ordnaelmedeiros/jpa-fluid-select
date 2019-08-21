package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms.FieldTransformCast;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms.FieldTransformDate;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms.FieldTransformString;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.transforms.FieldTransformTime;

import lombok.Getter;
import lombok.Setter;

public class FieldControl<Back> implements 
		FieldTransformDate<Back>,
		FieldTransformTime<Back>,
		FieldTransformString<Back>,
		FieldTransformCast<Back> {
	
	@Getter @Setter
	private String sql;
	
	private Back back;
	
	public FieldControl() {
	}
	
	public FieldControl(Back back) {
		this.back = back;
	}
	
	protected void setBack(Back back) {
		this.back = back;
	}
	
	public Back end() {
		return this.back;
	}
	
}
