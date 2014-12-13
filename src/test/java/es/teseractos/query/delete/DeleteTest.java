package es.teseractos.query.delete;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.teseractos.query.condition.Condition;

@RunWith(JUnit4.class)
public class DeleteTest {

  @Test
  public void test_anhadirCondicion() {
    Delete delete = new Delete("test_table");
    delete.addCondition(Condition.equalThan("campo1", null));
    delete.addCondition(Condition.isNull("campo2"));
    Assert.assertEquals(
        "DELETE FROM test_table WHERE campo1=? AND campo2 IS NULL",
        delete.sql());
  }
}
