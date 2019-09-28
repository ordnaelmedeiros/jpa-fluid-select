package com.ordnaelmedeiros.jpafluidselect.querybuilder.select;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.NonUniqueResultException;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.FieldSelect;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fields.Fields;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.fluid.ToSql;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.groupby.GroupBy;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join.Join;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.join.joins.JoinImpl;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.operation.Operations;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.order.Order;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.pagination.Pagination;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.parameters.Parameters;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.ref.Ref;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.where.Where;

import lombok.Getter;

public class Select<Table> implements JoinImpl<Table> {

	private Class<Table> klass;
	
	@Getter
	private QueryBuilder builder;
	
	@Getter
	private List<Join<?,?,?>> joins;
	private Where<Table> where;
	private Order<Table> order;
	private GroupBy<Table> groupBy;
	private Fields<Table> fields;
	
	private boolean distinct = false;
	
	@Getter
	private Parameters<Table> param;
	
	@Getter
	private String aliasFrom;
	
	private ResultType resultType = ResultType.CONSTRUCTOR;

	@Getter
	private Integer maxResults = null;
	
	@Getter
	private Integer firstResult = null;
	
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
	
	public Select<Table> distinct() {
		this.distinct = true;
		return this;
	}
	
	/**
	 * Set the maximum number of results to retrieve
	 * @param maxResults
	 * @return SELECT
	 */
	public Select<Table> maxResults(int maxResults) {
		this.maxResults = maxResults;
		return this;
	}
	
	/**
	 * Set the position of the first result to retrieve
	 * @param firstResult
	 * @return SELECT
	 */
	public Select<Table> firstResult(int firstResult) {
		this.firstResult = firstResult;
		return this;
	}
	
	public Operations<Select<Table>, Table, Table> where() {
		return this.where;
	}
	
	public Select<Table> where(Consumer<Operations<Select<Table>, Table, Table>> where) {
		where.accept(this.where());
		return this;
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
	public Select<Table> order(Consumer<Order<Table>> consumer) {
		consumer.accept(this.order);
		return this;
	}
	
	public GroupBy<Table> group() {
		return this.groupBy;
	}
	public Select<Table> group(Consumer<GroupBy<Table>> consumer) {
		consumer.accept(this.groupBy);
		return this;
	}
	
	public Fields<Table> fields() {
		return this.fields;
	}
	
	public Select<Table> fields(Consumer<Fields<Table>> consumer) {
		consumer.accept(this.fields);
		return this;
	}
	
	public Pagination<Table> pagination() {
		return new Pagination<Table>(this);
	}
	
	private String toSql(Class<?> klass) {
		
		String sql = "SELECT ";
		if (this.distinct) {
			sql += "DISTINCT ";
		}
		
		sql += this.fields.toSql(resultType, klass);
		sql += " FROM " + this.klass.getName()+" " + this.aliasFrom + " \n";
		for (Join<?, ?, ?> join : this.joins) {
			sql += join.toSql() + "\n";
		}
		sql += this.where.toSql() + "\n";
		sql += this.groupBy.toSql() + "\n";
		if (!ResultType.COUNT.equals(this.resultType)) {
			sql += this.order.toSql() + "\n";
		}
		
		return sql;
	}
	
	private void configQuery(Query query) {
		if (maxResults!=null) {
			query.setMaxResults(maxResults);
		}
		if (firstResult!=null) {
			query.setFirstResult(firstResult);
		}
	}
	
	public List<Table> getResultList() {

		try {

			this.resultType = ResultType.CONSTRUCTOR;
			
			String sql = this.toSql(this.klass);
			
			TypedQuery<Table> query = this.builder.getEm().createQuery(sql, klass);
			this.configQuery(query);
			
			this.param.setParameters(query);
			
			List<Table> result = query.getResultList();
			
			return result;
			
		} catch (NoResultException e) {
			return new ArrayList<>();
		}
		
	}
	
	public Table getSingleResult() {
		
		try {
				
			this.resultType = ResultType.CONSTRUCTOR;
			
			String sql = this.toSql(this.klass);
			
			TypedQuery<Table> query = this.builder.getEm().createQuery(sql, klass);
			this.configQuery(query);
			
			this.param.setParameters(query);
			
			Table result = query.getSingleResult();
			
			return result;
			
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getResultObjects() {
		
		try {
			
			resultType = ResultType.ARRAY;
			
			String sql = this.toSql(this.klass);
			
			Query query = this.builder.getEm().createQuery(sql);
			this.configQuery(query);
			
			this.param.setParameters(query);
			
			List<Object[]> result = query.getResultList();
			
			return result;
		
		} catch (NoResultException e) {
			return new ArrayList<>();
		}
		
	}
	
	public Long count() {
		
		try {

			Class<Long> klass = Long.class;
			
			this.resultType = ResultType.COUNT;
			
			String sql = this.toSql(klass);
			
			TypedQuery<Long> query = this.builder.getEm().createQuery(sql, klass);
			this.configQuery(query);
			
			this.param.setParameters(query);
			
			Long result = query.getSingleResult();
			
			return result;
			
		} catch (NoResultException e) {
			return 0l;
		}
		
	}
	
	public <T> T getSingleResult(Class<T> klass) {
		
		try {
			
			this.resultType = ResultType.ARRAY;
			
			String sql = this.toSql(klass);
			
			TypedQuery<T> query = this.builder.getEm().createQuery(sql, klass);
			this.configQuery(query);
			
			this.param.setParameters(query);
			
			T result = query.getSingleResult();
			
			return result;
			
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	public <T> T getSingleResultByConstructor(Class<T> klass) {
		
		try {
			
			this.resultType = ResultType.CONSTRUCTOR;
			
			String sql = this.toSql(klass);
			
			TypedQuery<T> query = this.builder.getEm().createQuery(sql, klass);
			this.configQuery(query);
			
			this.param.setParameters(query);
			
			T result = query.getSingleResult();
			
			return result;
			
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	public <T> T getSingleResultByReflect(Class<T> klass) {
		
		List<T> results = this.getResultListByReflect(klass);
		
		if (results==null || results.isEmpty()) {
			return null;
		} else if (results.size()>1) {
			throw new NonUniqueResultException(results.size());
		}
		
		return results.get(0);
		
	}
	
	public <T> List<T> getResultList(Class<T> klass) {
		
		try {
			
			this.resultType = ResultType.ARRAY;
			
			String sql = this.toSql(klass);
			
			TypedQuery<T> query = this.builder.getEm().createQuery(sql, klass);
			this.configQuery(query);
			
			this.param.setParameters(query);
			
			List<T> result = query.getResultList();
			
			return result;
			
		} catch (NoResultException e) {
			return new ArrayList<>();
		}
		
	}
	public <T> List<T> getResultListByConstructor(Class<T> klass) {
		
		try {
			
			this.resultType = ResultType.CONSTRUCTOR;
			
			String sql = this.toSql(klass);
			
			TypedQuery<T> query = this.builder.getEm().createQuery(sql, klass);
			this.configQuery(query);
			
			this.param.setParameters(query);
			
			List<T> result = query.getResultList();
			
			return result;
			
		} catch (NoResultException e) {
			return new ArrayList<>();
		}
		
	}
	public <T> List<T> getResultListByReflect(Class<T> klass) {

		try {
			
			List<Object[]> results = this.getResultObjects();
			
			List<T> result = new ArrayList<>();
			
			// create field list
			
			List<Field> fieldList = new ArrayList<Field>();
			
			for (FieldSelect<?> f : this.fields().getList()) {
				
				if (f.getAlias()!=null) {
					
					Field field = klass.getDeclaredField(f.getAlias());
					field.setAccessible(true);
					fieldList.add(field);
					
				} else {
					fieldList.add(null);
				}
				
			}
			
			
			for (Object[] o : results) {
				
				T item = klass.newInstance();
				
				for (int i = 0; i < fieldList.size(); i++) {
					Field f = fieldList.get(i);
					if (f!=null && o[i]!=null) {
						f.set(item, o[i]);
					}
				}
				
				result.add(item);
				
			}
			
			return result;
		
		} catch (NoResultException e) {
			return new ArrayList<>();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
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
		
		if (this.fields.isEmpty() || this.fields().getList().size()==1) {
			
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

	public Select<Table> ref(Ref<Table> ref) {
		ref.setKlass(this.klass);
		ref.setAlias(this.aliasFrom);
		return this;
	}

	@Override
	public Select<Table> getSelect() {
		return this;
	}
	
}
