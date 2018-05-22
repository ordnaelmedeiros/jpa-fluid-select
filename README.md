# Jpa Fluid Select

![](https://img.shields.io/github/stars/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/forks/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/tag/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/release/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/issues/ordnaelmedeiros/jpa-fluid-select.svg)

## Maven
https://mvnrepository.com/artifact/com.github.ordnaelmedeiros/jpa-fluid-select
```html
<dependency>
	<groupId>com.github.ordnaelmedeiros</groupId>
	<artifactId>jpa-fluid-select</artifactId>
	<version>0.0.7</version>
</dependency>
```

### Dependence Jpa Meta Model
- Metamodel:
	- https://docs.jboss.org/hibernate/orm/4.0/hem/en-US/html/metamodel.html
- JPA Static Metamodel Generator (recommended):
	- https://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html


## All
```javascript
List<People> lista = new Select(em)
	.from(People.class)
	.getResultList();
```

## Where
```javascript
People p = new Select(em)
	.from(People.class)
	.where()
		.equal(People_.id, 1)
	.end()
	.getSingleResult()
```


## OrderBy
```javascript
List<People> lista1 = new Select(em)
	.from(People.class)
	.orderDesc(People_.id)
	.getResultList();
	
List<People> lista1 = new Select(em)
	.from(People.class)
	.order()
		.asc(People_.name)
		.desc(People_.id)
	.end()
	.getResultList();
```


## Count
```javascript
Long count = new Select(em)
	.fromCount(People.class)
	.where()
		.like(People_.name, "%le%")
	.end()
	.getSingleResult();
```

## Not
```javascript
List<People> lista = new Select(em)
	.from(People.class)
	.where()
		.not().equal(People_.id, 1)
	.end()
	.getResultList()
```

## IfCan
```javascript
boolean notFindById2 = false;
		
List<People> lista = new Select(em)
	.from(People.class)
	.where()
		.orGroup()
			.equal(People_.id, 1)
			.ifCan(notFindById2).equal(People_.id, 2)
		.end()
	.end()
	.getResultList()
	;
```

## WhereGroup
```javascript
List<People> lista = new Select(em)
	.from(People.class)
	.where()
		.like(People_.name, "%e%")
		// and (
		.orGroup()
			.equal(People_.id, 1)
			// or
			.equal(People_.id, 2)
			// or
			.equal(People_.id, 5)
		.end()
		// )
	.end()
	.getResultList()
```

## Join
```javascript
List<People> lista = new Select(em)
	.from(People.class)
	.join(People_.address)
		.on()
			.orGroup()
				.equal(Address_.street, "One")
				.equal(Address_.street, "9999")
			.end()
		.end()
	.end()
	.join(JoinType.LEFT, People_.phones)
		.on()
			.equal(Phone_.number, "123")
		.end()
	.end()
	.where()
		.like(People_.name, "%a%")
	.end()
	.getResultList()
```

## CustomFields
```javascript
List<Object[]> lista = new Select(em)
	.fromMultSelect(People.class)
	.join(People_.address).extractJoin(j -> this.joinAdress = j).end()
	.fields() // fields returns
		.add(People_.id)
		.add(People_.name)
		.add(joinAdress, Address_.street)
	.end()
	.where()
		.in(People_.id, new Long[] {1, 2})
	.end()
	.orderAsc(People_.id)
	.getResultList()
```

## CustomFieldsTransform
```javascript
public class DTO {
	
	private String peopleName;
	private String peopleStreet;
	
	public DTO(String peopleName, String peopleStreet) {
		this.peopleName = peopleName;
		this.peopleStreet = peopleStreet;
	}
	
}

DTO dto = new Select(em)
	.fromMultiSelect(People.class, DTO.class)
	.join(People_.address).extractJoin(j -> this.joinAdress = j).end()
	.fields()
		.add(People_.name)
		.add(joinAdress, Address_.street)
	.end()
	.where()
		.equal(People_.id, 1)
	.end()
	.getSingleResult();
```

## Pagination
```javascript
List<Object[]> list = new Select(em)
	.fromMultiSelect(People.class)
	.fields()
		.add(People_.id)
	.end()
	.orderAsc(People_.id)
	.getResultList(2, 3);
	//(page, limit)
```

## GroupBy
```javascript
List<Object[]> list = new Select(em)
	.fromMultiSelect(People.class)
	.join(People_.address).extractJoin(j -> this.joinAddress = j).end()
	.fields()
		.add(this.joinAddress, Address_.street)
		.count(People_.id)
	.end()
	.group()
		.add(this.joinAddress, Address_.street)
	.end()
	.order()
		.asc(this.joinAddress, Address_.street)
	.end()
	.getResultList();

list.stream().forEach(o -> {
	System.out.println(String.format("%10s count: %02d", o));
});
```
