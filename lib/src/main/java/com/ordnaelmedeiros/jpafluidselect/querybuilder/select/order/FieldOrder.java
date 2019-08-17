package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Content;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.transforms.FieldOrderTransformCast;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.transforms.FieldOrderTransformDate;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.transforms.FieldOrderTransformTime;

public class FieldOrder<SelectTable> implements 
		FieldOrderTransformDate<SelectTable>,
		FieldOrderTransformTime<SelectTable>,
		FieldOrderTransformCast<SelectTable>
		{

	protected Order<SelectTable> order;
	
	private String sql;
	
	public FieldOrder(Order<SelectTable> order, String alias, String field) {
		this.order = order;
		this.sql = alias+"."+field;
	}

	@Override
	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String getSql() {
		return this.sql;
	}

	@Override
	public FieldOrder<SelectTable> end() {
		return this;
	}
	
	/**
	 * Order direction ASC.
	 * <ul>
	 * <li>JPQL: ORDER BY c.name ASC
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/order#Order_Direction_ASC_DESC">www.objectdb.com</a> 
	 * @return back Order
	 */
	public Order<SelectTable> asc() {
		order.add(new Content(this.sql + " ASC"));
		return order;
	}
	
	/**
	 * Order direction DESC.
	 * <ul>
	 * <li>JPQL: ORDER BY c.name DESC
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/order#Order_Direction_ASC_DESC">www.objectdb.com</a> 
	 * @return back Order
	 */
	public Order<SelectTable> desc() {
		order.add(new Content(this.sql + " DESC"));
		return order;
	}
	
}
