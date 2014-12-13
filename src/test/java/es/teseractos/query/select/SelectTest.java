package es.teseractos.query.select;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.teseractos.query.condition.CompositeCondition;
import es.teseractos.query.condition.Condition;

@RunWith(JUnit4.class)
public class SelectTest {

  @SuppressWarnings("rawtypes")
  @Test
  public void test_setSQL() {
    Select select = new Select("test_table");
    String sql = "SELECT field1 FROM cualquier_tabla WHERE field1 IS NOT NULL";
    select.setQuery(sql);
    Assert.assertEquals(sql, select.sql());
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test_addCondition() {
    Select select = new Select("test_table");
    select.addCondition(Condition.equalThan("field1", null));
    select.addCondition(Condition.isNull("field2"));
    Assert.assertEquals(
        "SELECT * FROM test_table WHERE field1=? AND field2 IS NULL",
        select.sql());
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test_projection() {
    Select select = new Select("test_table");
    select.projection("field1", "field2", "field3");
    Assert.assertEquals("SELECT field1, field2, field3 FROM test_table",
        select.sql());
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test_orderBy() {
    Select select = new Select("test_table");
    select.orderBy("field1");
    Assert.assertEquals("SELECT * FROM test_table ORDER BY field1",
        select.sql());
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test_orderBy_multiple_fields() {
    Select select = new Select("test_table");
    select.orderBy("field1");
    select.orderBy("field2");
    Assert.assertEquals("SELECT * FROM test_table ORDER BY field1, field2",
        select.sql());
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test_orderBy_asc() {
    Select select = new Select("test_table");
    select.orderBy("field1", Order.ASCENDANT);
    Assert.assertEquals("SELECT * FROM test_table ORDER BY field1 ASC",
        select.sql());
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test_orderBy_desc() {
    Select select = new Select("test_table");
    select.orderBy("field1", Order.DESCENDANT);
    Assert.assertEquals("SELECT * FROM test_table ORDER BY field1 DESC",
        select.sql());
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void test_complex_sql() {

    Select select = new Select("test_table");
    Condition condition1 = Condition.contains("field1", "");

    CompositeCondition orConditon = (CompositeCondition) Condition.or(Condition
        .equalThan("field2", 1));

    for (int i = 3; i < 7; i++) {
      orConditon.addCondition(Condition.equalThan("field" + i, i));
    }

    CompositeCondition condition2 = (CompositeCondition) Condition.and(
        orConditon,
        Condition.or(Condition.equalThan("field7", 1),
            Condition.equalThan("field8", 0)));

    select.addCondition(Condition.or(condition1, condition2));

    Assert.assertEquals(
        "SELECT * FROM test_table "
            + "WHERE (field1 MATCHES ? "
            + "OR ((field2=? OR field3=? OR field4=? OR field5=? OR field6=?) "
            + "AND (field7=? OR field8=?)))", select.sql());
  }
}
