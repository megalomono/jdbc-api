package es.teseractos.query.update;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.teseractos.query.condition.Condition;

@RunWith(JUnit4.class)
public class UpdateTest {

	@Test
	public void test_addField() {
		Update update = new Update("test_table");
		update.addField("field1", null);
		update.addField("field2", null);
		Assert.assertEquals("UPDATE test_table SET field1=?, field2=?",
				update.sql());
	}

	@Test
	public void test_addCondition() {
		Update update = new Update("test_table");
		update.addField("field1", null);
		update.addCondition(Condition.equalThan("field2", null));
		update.addCondition(Condition.isNull("field3"));
		Assert.assertEquals(
				"UPDATE test_table SET field1=? WHERE field2=? AND field3 IS NULL",
				update.sql());
	}
}
