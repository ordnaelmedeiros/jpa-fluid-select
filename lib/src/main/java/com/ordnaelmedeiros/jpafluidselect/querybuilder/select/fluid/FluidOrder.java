package com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid;

import java.util.function.Consumer;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.Select;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.Order;

public interface FluidOrder<SelectTable> {
	
	public Select<SelectTable> getSelect();
	
	/**
	 * ORDER BY clause
	 * <ul>
	 * <li>JPQL: ORDER BY c.name
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/order">www.objectdb.com</a> 
	 * @return ORDER BY
	 */
	default Order<SelectTable> order() {
		return this.getSelect().order();
	}
	
	/**
	 * ORDER BY clause
	 * <ul>
	 * <li>JPQL: ORDER BY c.name
	 * </ul>
	 * @param consumer 
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/order">www.objectdb.com</a> 
	 * @return ORDER BY
	 */
	default Select<SelectTable> order(Consumer<Order<SelectTable>> consumer) {
		return this.getSelect().order(consumer);
	}
	
}
