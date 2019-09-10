package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.FieldControl;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Content;

public class FieldOrder<SelectTable> extends FieldControl<FieldOrder<SelectTable>> {

	protected Order<SelectTable> order;
	
	public FieldOrder(Order<SelectTable> order, String alias, String field) {
		this.order = order;
		this.setBack(this);
		this.setSql(alias+"."+field);
	}
	
	public FieldOrder(Order<SelectTable> order, String sql) {
		this.order = order;
		this.setBack(this);
		this.setSql(sql);
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
		order.add(new Content(this.getSql() + " ASC"));
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
		order.add(new Content(this.getSql() + " DESC"));
		return order;
	}
	
}
