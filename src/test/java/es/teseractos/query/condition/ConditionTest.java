package es.teseractos.query.condition;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ConditionTest {

  @Test
  public void test_isNull() {
    Assert.assertEquals("field1 IS NULL", Condition.isNull("field1").sql());
  }

  @Test
  public void test_equalThan() {
    Assert.assertEquals("field1=?", Condition.equalThan("field1", null)
        .sql());
  }

  @Test
  public void test_greaterThan() {
    Assert.assertEquals("field1>?", Condition.greaterThan("field1", null)
        .sql());
  }

  @Test
  public void test_greaterEqualThan() {
    Assert.assertEquals("field1>=?", Condition
        .greaterEqualThan("field1", null).sql());
  }

  @Test
  public void test_lesserThan() {
    Assert.assertEquals("field1<?", Condition.lesserThan("field1", null)
        .sql());
  }

  @Test
  public void test_lesserEqualThan() {
    Assert.assertEquals("field1<=?", Condition
        .lesserEqualThan("field1", null).sql());
  }

  @Test
  public void test_between() {
    Assert.assertEquals("(field1>=? AND field1<=?)",
        Condition.between("field1", 1, 2).sql());
  }

  @Test
  public void test_between_without_parameters() {
    Assert.assertNull(Condition.between("field1", null, null));
  }

  @Test
  public void test_between_without_lesser_parameter() {
    Assert.assertEquals("(field1>=?)", Condition.between("field1", 1, null)
        .sql());
  }

  @Test
  public void test_between_without_greater_parameter() {
    Assert.assertEquals("(field1<=?)", Condition.between("field1", null, 2)
        .sql());
  }

  @Test
  public void test_exclusiveBetween() {
    Assert.assertEquals("(field1>? AND field1<?)", Condition
        .exclusiveBetween("field1", 1, 2).sql());
  }

  @Test
  public void test_contains_string() {
    Assert.assertEquals("field1 MATCHES ?",
        Condition.contains("field1", "cadena").sql());
  }

  @Test
  public void test_contains_integer() {
    Assert.assertEquals("field1 MATCHES ?",
        Condition.contains("field1", new Integer(1)).sql());
  }

  @Test
  public void test_contains_boolean() {
    Assert.assertEquals("field1 MATCHES ?",
        Condition.contains("field1", new Boolean(true)).sql());
  }

  @Test
  public void test_contains_date() {
    Assert.assertEquals("field1 MATCHES ?",
        Condition.contains("field1", new Date()).sql());
  }

  @Test
  public void test_contains_calendar() {
    Assert.assertEquals("field1 MATCHES ?",
        Condition.contains("field1", Calendar.getInstance()).sql());
  }

  @Test
  public void test_composite_condition() {
    Assert.assertEquals(
        "(field1=? AND field2=?)",
        Condition.and(Condition.equalThan("field1", null),
            Condition.equalThan("field2", null)).sql());
  }

  @Test
  public void test_empty_composite_condition() {
    Assert.assertNull(Condition.and(null, null));
  }

  @Test
  public void test_list_composite_condition() {
    List<Condition> toEvaluate = new ArrayList<Condition>();
    toEvaluate.add(Condition.equalThan("field1", null));
    toEvaluate.add(Condition.equalThan("field2", null));
    Assert.assertEquals("(field1=? AND field2=?)", Condition
        .and(toEvaluate).sql());
  }

  @Test
  public void test_emptu_list_composite_condition() {
    List<Condition> toEvaluate = null;
    Assert.assertNull(Condition.and(toEvaluate));
  }

}
