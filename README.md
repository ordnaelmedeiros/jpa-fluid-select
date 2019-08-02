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


## First
Start QueryBuilder
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
		.field(People_.id).eq(1)
	.getResultList();
```

## OrderBy
```java
List<People> list1 = queryBuilder
	.select(People.class)
	.order()
		.field(People_.id).desc()
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

Long count1 = queryBuilder
	.select(People.class)
	.where()
		.field(People_.id).lt(4)
	.count();

Select<People> select = queryBuilder
	.select(People.class)
	.where()
		.field(People_.id).lt(4)
	.order()
		.field(People_.id).desc()
	.getSelect();
	
Long count2 = select.count();
List<People> list = select.getResultList();

```

## Not
```java
List<People> list = queryBuilder
	.select(People.class)
	.where()
		.field(People_.id).lt(4l)
		.field(People_.id).not().eq(1l)
	.order()
		.field(People_.id).asc()
	.getResultList();
```

## Temporal
```java

```

## IfCan
```java

```

## WhereGroup
```java

```

## Join
```java

```

## CustomFields
```java

```

## CustomFieldsTransform
```java

```

## Pagination
```java

```

## GroupBy
```java

```

## Distinct
```java

```

## Lambda expressions
```java


```

## Use attribute name
```java

```

## Finally

```java
if (this.savedYourLife() || this.savedJustALittle()) {
	System.out.println("Help me buy more coffee?");
}
```

|USD|BRL|
|:---:|:---:|
|[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=QR5L9PULKKUCN&item_name=Coffe&currency_code=USD&source=url)|[![paypal](https://www.paypalobjects.com/pt_BR/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=QR5L9PULKKUCN&item_name=Caf%C3%A9&currency_code=BRL&source=url)|