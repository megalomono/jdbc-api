jdbc-api
========

Librería para encapsular el uso de JBDC. Actualmente emplea sintaxis propia de Informix, ya que esta librería se implementó para un proyecto _legacy_ en el que las consultas se construían concatenando texto.

* Para compilar la librería:

```
mvn package
```

* Para lanzar los tests

```
mvn test
```

Ejemplo de uso:

``` java
Select select = new Select("test_table");
select.projection("field1", "field2", "field3");
select.addCondition(Condition.equalThan("field1", null));
select.addCondition(Condition.isNull("field2"));
select.orderBy("field1");
ResultReader<e> resultado = select.uniqueResult();
```