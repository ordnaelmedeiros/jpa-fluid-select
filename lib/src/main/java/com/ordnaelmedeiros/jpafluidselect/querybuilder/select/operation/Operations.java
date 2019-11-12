package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation;

import java.util.function.Consumer;

import javax.persistence.metamodel.Attribute;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidEnd;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidFields;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidGroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidOrder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidPagination;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.FluidSelect;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.Ref;

import lombok.Getter;
import lombok.Setter;

public class Operations<ObjBack, SelectTable, Table>
		extends Container 
		implements 
			FluidEnd<ObjBack>,
			FluidSelect<SelectTable>,
			FluidOrder<SelectTable>,
			FluidFields<SelectTable>,
			FluidGroupBy<SelectTable>,
			FluidPagination<SelectTable> {

	private ObjBack objBack;
	
	@Getter
	private Select<SelectTable> select;
	
	@Getter
	@Setter
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
	
	public Operations<Operations<ObjBack, SelectTable, Table>, SelectTable, Table> orGroup() {
		Operations<Operations<ObjBack, SelectTable, Table>, SelectTable, Table> or = new Operations<>(this, select, this.originAlias);
		or.setDelimiter(" OR ");
		this.add(or);
		return or;
	}

	public ObjBack orGroup(Consumer<Operations<Operations<ObjBack, SelectTable, Table>, SelectTable, Table>> orGroup) {
		orGroup.accept(orGroup());
		return end();
	}
	
	public Operations<Operations<ObjBack,SelectTable, Table>,SelectTable, Table> andGroup() {
		Operations<Operations<ObjBack, SelectTable, Table>, SelectTable, Table> and = new Operations<>(this, select, this.originAlias);
		and.setDelimiter(" AND ");
		this.add(and);
		return and;
	}
	
	public ObjBack andGroup(Consumer<Operations<Operations<ObjBack, SelectTable, Table>, SelectTable, Table>> andGroup) {
		andGroup.accept(andGroup());
		return end();
	}
	
	public void addField(FieldOperation<ObjBack, SelectTable, Table, ?> field) {
		this.add(field);
	}
	
	@Override
	public ObjBack end() {
		return objBack;
	}

	public FieldOperation<ObjBack, SelectTable, Table, Object> field(String field) {
		return new FieldOperation<>(this, originAlias, field);
	}
	
	public <T> FieldOperation<ObjBack, SelectTable, Table, T> field(Attribute<Table, T> field) {
		return new FieldOperation<>(this, originAlias, field.getName());
	}

	public <T> FieldOperation<ObjBack, SelectTable, Table, Object> field(Ref<T> ref, String att) {
		return new FieldOperation<>(this, ref.getAlias(), att);
	}
	
	public <T, F> FieldOperation<ObjBack, SelectTable, Table, F> field(Ref<T> ref, Attribute<T, F> att) {
		return new FieldOperation<>(this, ref.getAlias(), att.getName());
	}
	
	public Operations<ObjBack, SelectTable, Table> jpql(ToSql toSql) {
		
		Container c = new Container();
		c.setPrefix("(");
		c.setSuffix(")");
		c.add(toSql);
		this.add(c);
		
		return this;
		
	}
	
}
