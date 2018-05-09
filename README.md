# Jpa Fluid Select

![](https://img.shields.io/github/stars/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/forks/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/tag/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/release/ordnaelmedeiros/jpa-fluid-select.svg) ![](https://img.shields.io/github/issues/ordnaelmedeiros/jpa-fluid-select.svg)

## Maven
https://mvnrepository.com/artifact/com.github.ordnaelmedeiros/jpa-fluid-select
```html
<dependency>
	<groupId>com.github.ordnaelmedeiros</groupId>
	<artifactId>jpa-fluid-select</artifactId>
	<version>0.0.5</version>
</dependency>
```

### Dependence Jpa Meta Model
- Metamodel:
	- https://docs.jboss.org/hibernate/orm/4.0/hem/en-US/html/metamodel.html
- JPA Static Metamodel Generator (recommended):
	- https://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html

Exemple 1:
```javascript
	EntityManager em = ...;
	People o = new Select(em)
		.from(People.class)
		.where()
			.equal(People_.id, 1l)
		.end()
		.getSingleResult();
/*
SQL generated
select people0_.id as id1_1_, people0_.address_id as address_3_1_, people0_.name as name2_1_ 
from People people0_
where people0_.id=1
*/
```

Exemple 2:

```javascript
	List<People> lista = new Select(em)
	.from(People.class)
	.where()
		.like(People_.name, "%e%")
		.orGroup()
			.equal(People_.id, 1l)
			.equal(People_.id, 2l)
			.equal(People_.id, 5l)
		.end()
	.end()
	.getResultList();
/*
select people1x0_.id as id1_0_, people1x0_.name as name2_0_ 
from People1 people1x0_ 
where (people1x0_.name like ?) 
and (people1x0_.id=1 or people1x0_.id=2 or people1x0_.id=5)
*/
```
