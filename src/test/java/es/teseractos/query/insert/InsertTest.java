package es.teseractos.query.insert;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class InsertTest {

	@Test
	public void test_addField() {
		Insert insert = new Insert("test_table");
		insert.addField("field1", null);
		insert.addField("field2", null);
		Assert.assertEquals(
				"INSERT INTO test_table(field1, field2) VALUES (?, ?)",
				insert.sql());
	}
}
