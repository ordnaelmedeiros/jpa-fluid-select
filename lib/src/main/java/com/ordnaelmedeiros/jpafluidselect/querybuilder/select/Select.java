package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.Fields;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.groupby.GroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join.Join;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.Order;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.parameters.Parameters;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.where.Where;

import lombok.Getter;

public class Select<Table> {

	private Class<Table> klass;
	
	@Getter
	private QueryBuilder builder;
	
	private List<Join<?,?>> joins;
	private Where<Table> where;
	private Order<Table> order;
	private GroupBy<Table> groupBy;
	private Fields<Table> fields;
	
	@Getter
	private Parameters<Table> param;
	
	private String aliasFrom;
	
	private ResultType resultType = ResultType.CONSTRUCTOR;
	
	public Select(Class<Table> klass, QueryBuilder builder) {
		
		this.aliasFrom = builder.createTableAlias(klass.getSimpleName());
		
		this.klass = klass;
		this.builder = builder;
		
		this.where = new Where<>(this, this.aliasFrom);
		this.where.setPrefix("WHERE (");
		
		this.order = new Order<>(this, this.aliasFrom);
		this.fields = new Fields<>(this, this.aliasFrom);
		this.groupBy = new GroupBy<>(this, this.aliasFrom);
		
		this.joins = new ArrayList<>();
		this.param = new Parameters<>(this);
		
		
	}
	
	public Join<Select<Table>,Table> leftJoin(String field) {
		Join<Select<Table>, Table> join = new Join<>(this, this, this.aliasFrom, field);
		this.joins.add(join);
		return join;
	}
	
	public Operations<Select<Table>, Table> where() {
		return this.where;
	}

	/**
	 * ORDER BY clause
	 * <ul>
	 * <li>JPQL: ORDER BY c.name
	 * </ul>
	 * @see <a href="https://www.objectdb.com/java/jpa/query/jpql/order">www.objectdb.com</a> 
	 * @return ORDER BY
	 */
	public Order<Table> order() {
		return this.order;
	}
	
	public GroupBy<Table> groupBy() {
		return this.groupBy;
	}
	
	public Fields<Table> fields() {
		return this.fields;
	}
	
	private String toSql() {
		
		String sql = "SELECT ";
		
		sql += this.fields.toSql(resultType, klass);
		sql += " FROM " + this.klass.getName()+" " + this.aliasFrom + " \n";
		for (Join<?, ?> join : this.joins) {
			sql += join.toSql() + "\n";
		}
		sql += this.where.toSql() + "\n";
		sql += this.groupBy.toSql() + "\n";
		sql += this.order.toSql() + "\n";
		
		return sql;
	}
	
	public List<Table> getResultList() {
		
		this.resultType = ResultType.CONSTRUCTOR;
		
		String sql = this.toSql();
		System.out.println("SQL:");
		System.out.println(sql);
		
		TypedQuery<Table> query = this.builder.getEm().createQuery(sql, klass);
		
		this.param.setParameters(query);
		
		List<Table> result = query.getResultList();
		
		return result;
	}
	
	public Table getSingleResult() {
		
		this.resultType = ResultType.CONSTRUCTOR;
		
		String sql = this.toSql();
		System.out.println("SQL:");
		System.out.println(sql);
		
		TypedQuery<Table> query = this.builder.getEm().createQuery(sql, klass);
		
		this.param.setParameters(query);
		
		Table result = query.getSingleResult();
		
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getResultObjects() {
		
		resultType = ResultType.ARRAY;
		
		String sql = this.toSql();
		System.out.println("SQL:");
		System.out.println(sql);
		
		Query query = this.builder.getEm().createQuery(sql);
		
		this.param.setParameters(query);
		
		List<Object[]> result = query.getResultList();
		
		return result;
		
	}
	
	public <T> T getSingleResult(Class<T> klass) {
		
		this.resultType = ResultType.ARRAY;
		
		String sql = this.toSql();
		System.out.println("SQL:");
		System.out.println(sql);
		
		TypedQuery<T> query = this.builder.getEm().createQuery(sql, klass);
		
		this.param.setParameters(query);
		
		T result = query.getSingleResult();
		
		return result;
	}
	
	public <T> T getSingleResultByConstructor(Class<T> klass) {
		
		this.resultType = ResultType.CONSTRUCTOR;
		
		String sql = this.toSql();
		System.out.println("SQL:");
		System.out.println(sql);
		
		TypedQuery<T> query = this.builder.getEm().createQuery(sql, klass);
		
		this.param.setParameters(query);
		
		T result = query.getSingleResult();
		
		return result;
	}
	public <T> T getSingleResultByTransform(Class<T> klass) {
		
		T result = null;
		
		return result;
		
	}
	
	public <T> List<T> getResultList(Class<T> klass) {
		
		this.resultType = ResultType.ARRAY;
		
		String sql = this.toSql();
		System.out.println("SQL:");
		System.out.println(sql);
		
		TypedQuery<T> query = this.builder.getEm().createQuery(sql, klass);
		
		this.param.setParameters(query);
		
		List<T> result = query.getResultList();
		
		return result;
	}
	public <T> List<T> getResultListByConstructor(Class<T> klass) {
		
		this.resultType = ResultType.CONSTRUCTOR;
		
		String sql = this.toSql();
		System.out.println("SQL:");
		System.out.println(sql);
		
		TypedQuery<T> query = this.builder.getEm().createQuery(sql, klass);
		
		this.param.setParameters(query);
		
		List<T> result = query.getResultList();
		
		return result;
	}
	public <T> List<T> getResultListByTransform(Class<T> klass) {

		List<T> result = null;
		
		return result;
	}
	
	private String format(Object object, String replacer, Integer length) {
		String value;
		if (object==null) {
			value = "";
		} else {
			value = object.toString();
		}
		for (int i = value.length(); i < length; i++) {
			value += replacer;
		}
		return value;
	}
	
	public String resultToString() {
		
		String block = "\n=============================\n";
		StringJoiner sBuilder = new StringJoiner("\n", block, block);
		
		if (this.fields.isEmpty()) {
			
			List<Table> result = this.getResultList();
			
			result.forEach(i -> {
				
				Stream<Field> fields = Stream.of(this.klass.getDeclaredFields());
				StringJoiner sRow = new StringJoiner(" - ");
				
				fields.forEach(f -> {
					f.setAccessible(true);
					try {
						Object v = f.get(i);
						if (v!=null) {
							sRow.add(v.toString());
						} else {
							sRow.add("<NULL>");
						}
					} catch (Exception e) {
					}
				});
				sBuilder.add(sRow.toString());
			});
			
		} else {
			
			try {
				
				List<Object[]> objects = this.getResultObjects();
				
				List<Integer> lenths = new ArrayList<Integer>();
				
				this.fields.getList().forEach( f-> {
					lenths.add(f.toSql().length());
				});
				objects.forEach(list-> {
					for (int i = 0; i < list.length; i++) {
						Object o = list[i];
						if (o!=null) {
							Integer len = o.toString().length();
							if (len>lenths.get(i)) {
								lenths.add(i, len);
							}
						}
					}
				});
				
				StringJoiner lines = new StringJoiner("===","==","==");
				StringJoiner fields = new StringJoiner(" | ","| "," |");
				
				for (int j = 0; j < this.fields.getList().size(); j++) {
					ToSql toSql = this.fields.getList().get(j);
					lines.add(this.format("=", "=", lenths.get(j)));
					fields.add(this.format(toSql.toSql(), " ", lenths.get(j)));
				}
				
				sBuilder.add(lines.toString());
				sBuilder.add(fields.toString());
				sBuilder.add(lines.toString());
				
				objects.forEach(list -> {
					StringJoiner values = new StringJoiner(" | ","| "," |");	
					for (int i = 0; i < list.length; i++) {
						values.add(this.format(list[i], " ", lenths.get(i)));
					}
					sBuilder.add(values.toString());
				});
				
				sBuilder.add(lines.toString());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sBuilder.toString();
		
	}
	
	public Select<Table> print() {
		System.out.println(this.resultToString());
		return this;
	}
	
}
