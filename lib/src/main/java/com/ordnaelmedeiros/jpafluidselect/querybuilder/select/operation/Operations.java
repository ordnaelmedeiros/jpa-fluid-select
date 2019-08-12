package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidEnd;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidGroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidOrder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidSelect;

import lombok.Getter;

public class Operations<ObjBack, SelectTable>
		extends Container 
		implements 
			FluidEnd<ObjBack>,
			FluidSelect<SelectTable>,
			FluidOrder<SelectTable>,
			FluidGroupBy<SelectTable> {

	private ObjBack objBack;
	
	@Getter
	private Select<SelectTable> select;
	
	@Getter
	private String originAlias;
	
	public Operations(ObjBack objBack, Select<SelectTable> select, String originAlias) {
		super();
		
		this.originAlias = originAlias;
		
		this.setPrefix("(");
		this.setDelimiter(" AND ");
		this.setSuffix(")");
		
		this.objBack = objBack;
		this.select = select;
		
	}
	
	public Operations<Operations<ObjBack,SelectTable>,SelectTable> orGroup() {
		Operations<Operations<ObjBack, SelectTable>, SelectTable> or = new Operations<>(this, select, this.originAlias);
		or.setDelimiter(" OR ");
		this.add(or);
		return or;
	}
	
	public Operations<Operations<ObjBack,SelectTable>,SelectTable> andGroup() {
		Operations<Operations<ObjBack, SelectTable>, SelectTable> and = new Operations<>(this, select, this.originAlias);
		and.setDelimiter(" AND ");
		this.add(and);
		return and;
	}
	
	public void addField(FieldOperation<ObjBack, SelectTable> field) {
		String param = this.select.getParam().create(field.getValue());
		field.setParam(param);
		this.add(field);
	}
	
	@Override
	public ObjBack end() {
		return objBack;
	}

	public FieldOperation<ObjBack, SelectTable> field(String field) {
		return new FieldOperation<>(this, field);
	}

}
