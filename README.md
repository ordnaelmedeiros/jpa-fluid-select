# Jpa Fluid Select

![](https://img.shields.io/github/stars/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/forks/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/tag/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/release/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/issues/ordnaelmedeiros/jpa-fluid-select.svg)


## Maven
https://mvnrepository.com/artifact/com.github.ordnaelmedeiros/jpa-fluid-select
```xml
<dependency>
	<groupId>com.github.ordnaelmedeiros</groupId>
	<artifactId>jpa-fluid-select</artifactId>
	<version>2.0.0</version>
</dependency>
```

### Optional dependence Jpa Meta Model
- Metamodel:
	- https://docs.jboss.org/hibernate/orm/4.0/hem/en-US/html/metamodel.html
- JPA Static Metamodel Generator (recommended):
	- https://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html


## First start QueryBuilder
```java
QueryBuilder queryBuilder = new QueryBuilder(em); // EntityManager
```


## Select All
```java
List<People> list = queryBuilder
	.select(People.class)
	.getResultList();
```

## Where
```java
List<People> list = queryBuilder
	.select(People.class)
	.where()
		.field(People_.id).eq(1l)
	.getResultList();
```

## OrderBy
```java
List<People> list1 = queryBuilder
	.select(People.class)
	.order().desc(People_.id)
	.getResultList();
	
List<People> list2 = queryBuilder
	.select(People.class)
	.order()
		.field(People_.name).asc()
		.field(People_.id).desc()
	.getResultList();
```


## Count
```java

Long count = queryBuilder
	.select(People.class)
	.fields().count()
	.where()
		.field(People_.name).ilike("%le%")
	.getSingleResult(Long.class);

```

## Not
```java
List<People> list = queryBuilder
	.select(People.class)
	.where()
		.field(People_.id).not().in(1, 2)
	.getResultList();
```

## Conditional
```java
boolean isUpdate = false; 
		
List<People> list = queryBuilder
	.select(People.class)
	.where(w -> {
		if (isUpdate) {
			w.field(People_.id).ne(1);
		}
		w.field(People_.name).eq("Leandro");
	})
	.getResultList();

```

## LocalDate and LocalDateTime
```java

List<People> list1 = queryBuilder
	.select(People.class)
	.where()
		.field(People_.created).year().eq(2017)
	.getResultList();

List<People> list2 = queryBuilder
	.select(People.class)
	.where()
		.field(People_.created).cast(LocalDate.class).eq(LocalDate.of(1986, SEPTEMBER, 17))
	.getResultList();

```

## WhereGroup
```java
List<People> list = queryBuilder
	.select(People.class)
	.where()
		.field(People_.name).like("%e%")
		// and (
		.orGroup()
			.field(People_.id).eq(1)
			// or
			.field(People_.id).eq(2)
			// or
			.field(People_.id).eq(3)
		// )
		.end()
	.getResultList();
```

## Join
```java
List<People> list1 = queryBuilder
	.select(People.class)
	.innerJoin(People_.address)
		.innerJoin(Address_.country)
			.on()
				.field(Country_.name).eq("Florida")
	.getResultList();

List<People> list2 = queryBuilder
	.select(People.class)
	.innerJoin(People_.address)
		.on()
			.orGroup()
				.field(Address_.street).eq("One")
				.field(Address_.street).eq("9999")
			.end()
		.end()
	.end()
	.leftJoin(People_.phones)
		.on()
			.field(Phone_.number).eq("123")
		.end()
	.where()
		.field(People_.name).ilike("%a%")
	.getResultList();
```

## CustomFields
```java
Ref<Address> joinAdress = new Ref<>();
		
List<Object[]> list = queryBuilder
	.select(People.class)
	.leftJoin(People_.address).ref(joinAdress)
	.fields() // fields returns
		.add(People_.id)
		.add(People_.name)
		.add(joinAdress.field(Address_.street))
	.where()
		.field(People_.id).in(1l, 2l)
	.order()
		.asc(People_.id)
	.getResultObjects();

Long maxId = queryBuilder
	.select(People.class)
	.fields()
		.field(People_.id).max().add()
	.getSingleResult(Long.class);

List<Address> listAddress = queryBuilder
	.select(People.class)
	.fields()
		.add(People_.address)
	.where()
		.field(People_.id).eq(1)
	.order()
		.asc(People_.address)
	.getResultList(Address.class);
```

## CustomFieldsTransform
```java

Ref<Address> joinAdress = new Ref<>();

DTO dto = queryBuilder
	.select(People.class)
	.leftJoin(People_.address).ref(joinAdress)
	.fields()
		.add(People_.name)
		.add(joinAdress.field(Address_.street))
	.where()
		.field(People_.id).eq(1)
	.getSingleResultByConstructor(DTO.class);

DTO2 dto2 = queryBuilder
	.select(People.class)
	.leftJoin(People_.address).ref(joinAdress)
	.fields()
		.field(People_.name).alias("peopleName")
		.field(joinAdress.field(Address_.street)).alias("peopleStreet")
	.where()
		.field(People_.id).eq(1)
	.getSingleResultByReflect(DTO2.class);

```

## Limit
```java
List<People> list = queryBuilder
	.select(People.class)
	.order().asc(People_.id)
	.maxResults(3)
	.getResultList();
```

## Pagination
```java
PaginationResult<People> page = queryBuilder
	.select(People.class)
	.order()
		.asc(People_.id)
	.pagination()
		.numRows(10)
		.page(3)
	.getResultList();

System.out.println(page.getPageNumber());
System.out.println(page.getLastPage());
System.out.println(page.getPageSize());
System.out.println(page.getTotalRows());
System.out.println(page.getData().size());
```

## GroupBy
```java
Ref<Address> joinAddress = new Ref<>();

List<Object[]> list = queryBuilder
	.select(People.class)
	.leftJoin(People_.address).ref(joinAddress)
	.fields()
		.add(joinAddress.field(Address_.street))
		.field(People_.id).count().add()
	.group()
		.add(joinAddress.field(Address_.street))
	.order()
		.asc(joinAddress.field(Address_.street))
	.getResultObjects();
```

## Distinct
```java
List<Object[]> list = queryBuilder
	.select(Address.class)
	.distinct()
	.fields()
		.add(Address_.street)
	.order().asc(Address_.street)
	.getResultObjects();

```

## Lambda expressions
```java
Ref<Country> refCoutry = new Ref<>();

List<People> list = queryBuilder
	.select(People.class)
	.innerJoin(People_.address, jAddress -> {
		jAddress.innerJoin(Address_.country, jCountry -> {
			jCountry.ref(refCoutry).on(on -> {
				on
					.field(Country_.name).ilike("f%")
					.field("name").like("%a");
			});
		});
	})
	.where(w -> {
		w.orGroup()
			.field(People_.id).eq(1)
			.field(People_.id).eq(2)
			;
	})
	.order(o -> {
		o.asc(People_.id);
	})
	.getResultList();

```

## JPQL
```java
Ref<People> refPeople = new Ref<>();
List<Object[]> list = queryBuilder
	.select(People.class).ref(refPeople)
	.fields()
		.add(People_.id)
		.add(People_.name)
		.jpql(()->"CAST(id as java.lang.String)")
	.where()
		.jpql(()-> {
			String txt = "";
			txt += ":people.id = 1";
			txt += "OR :people.id = 2";
			return txt.replace(":people", refPeople.getAlias());
		})
	.order()
		.asc(People_.id)
	.print()
	.getResultObjects();
```

## Use attribute name
```java
List<Object[]> list = queryBuilder
	.select(People.class)
	.fields()
		.add("address.street")
		.field("id").count().add()
	.group()
		.add("address.street")
	.order()
		.asc("address.street")
	.print()
	.getResultObjects();
```

## Finally

```java
if (this.savedYourLife() || this.savedJustALittle()) {
	System.out.println("Help me buy more coffee?");
}
```

[Doc version 1.1.0](https://github.com/ordnaelmedeiros/jpa-fluid-select/blob/master/VER1.md)


|USD|BRL|
|:---:|:---:|
|[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=QR5L9PULKKUCN&item_name=Coffe&currency_code=USD&source=url)|[![paypal](https://www.paypalobjects.com/pt_BR/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=QR5L9PULKKUCN&item_name=Caf%C3%A9&currency_code=BRL&source=url)|