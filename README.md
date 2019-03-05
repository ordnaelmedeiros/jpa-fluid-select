# Jpa Fluid Select

![](https://img.shields.io/github/stars/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/forks/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/tag/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/release/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/issues/ordnaelmedeiros/jpa-fluid-select.svg)

## Maven
https://mvnrepository.com/artifact/com.github.ordnaelmedeiros/jpa-fluid-select
```html
<dependency>
	<groupId>com.github.ordnaelmedeiros</groupId>
	<artifactId>jpa-fluid-select</artifactId>
	<version>1.0.7</version>
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
	.getSingleResult()
```

## OrderBy
```javascript
List<People> lista1 = new Select(em)
	.from(People.class)
	.order().desc(People_.id)
	.getResultList();
	
List<People> lista1 = new Select(em)
	.from(People.class)
	.order()
		.asc(People_.name)
		.desc(People_.id)
	.getResultList();
```


## Count
```javascript
Long count = new Select(em)
	.fromCount(People.class)
	.where()
		.like(People_.name, "%le%")
	.getSingleResult();
```

## Not
```javascript
List<People> lista = new Select(em)
	.from(People.class)
	.where()
		.not().equal(People_.id, 1)
	.getResultList()
```

## Temporal
```javascript
List<People> list = new Select(em)
	.from(People.class)
	.where()
		.equal(People_.created, TemporalFunction.MONTH, 6)
	.getResultList();
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
	.where()
		.like(People_.name, "%a%")
	.getResultList()
```
```javascript
List<People> lista = new FSelect(em)
	.from(People.class)
	.join(People_.address)
		.join(Address_.country)
			.on()
				.equal(Country_.name, "Florida")
	.getResultList();
```

## CustomFields
```javascript
List<Object[]> lista = new Select(em)
	.fromCustomFields(People.class)
	.join(People_.address).extractJoin(j -> this.joinAdress = j)
	.fields() // fields returns
		.add(People_.id)
		.add(People_.name)
		.add(joinAdress, Address_.street)
	.where()
		.in(People_.id, new Long[] {1, 2})
	.order().asc(People_.id)
	.getResultList()
```

```javascript
Long maxId = new Select(em)
	.fromCustomFields(People.class, Long.class)
	.fields()
		.max(People_.id)
	.getSingleResult();
```

```javascript
List<Address> listAddress = new Select(em)
	.fromCustomFields(People.class, Address.class)
	.fields()
		.add(People_.address)
	.where()
		.le(People_.id, 2)
	.order()
		.asc(People_.id)
	.getResultList();
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

// Need Constructor in DTO
DTO dto = new Select(em)
	.fromCustomFields(People.class, DTO.class)
	.join(People_.address).extractJoin(j -> this.joinAdress = j)
	.fields()
		.add(People_.name)
		.add(joinAdress, Address_.street)
	.where()
		.equal(People_.id, 1)
	.getSingleResult();
```

```javascript
public class DTO2 {
	
	private String peopleName;
	private String peopleStreet;
	
}

// No need Constructor in DTO
List<DTO2> list = new FSelect(em)
	.fromCustomFields(People.class)
	.join(People_.address).extractJoin(j -> this.joinAdress = j)
	.fields()
		.add(People_.name).alias("peopleName")
		.add(joinAdress, Address_.street).alias("peopleStreet")
	.end()
	.getResultList(DTO2.class);
```

## Pagination
```javascript
List<Object[]> list = new Select(em)
	.fromCustomFields(People.class)
	.fields()
		.add(People_.id)
	.order().asc(People_.id)
	.getResultList(2, 3);
	//(page, limit)
```

## GroupBy
```javascript
List<Object[]> list = new Select(em)
	.fromCustomFields(People.class)
	.join(People_.address).extractJoin(j -> this.joinAddress = j)
	.fields()
		.add(this.joinAddress, Address_.street)
		.count(People_.id)
	.group()
		.add(this.joinAddress, Address_.street)
	.order()
		.asc(this.joinAddress, Address_.street)
	.getResultList();

list.stream().forEach(o -> {
	System.out.println(String.format("%10s count: %02d", o));
});
```

## Distinct
```javascript
List<Object[]> list = new Select(em)
	.fromCustomFields(Address.class)
	.distinct()
	.fields()
		.add(Address_.street)
	.order().asc(Address_.street)
	.getResultList();
```

## Finally

```javascript
if (this.savedYourLife() || this.savedJustALittle()) {
	System.out.println("Help me buy more coffee?");
}
```

|USD|BRL|
|:---:|:---:|
|[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=QR5L9PULKKUCN&item_name=Coffe&currency_code=USD&source=url)|[![paypal](https://www.paypalobjects.com/pt_BR/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=QR5L9PULKKUCN&item_name=Caf%C3%A9&currency_code=BRL&source=url)|