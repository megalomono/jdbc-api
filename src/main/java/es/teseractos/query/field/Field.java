package es.teseractos.query.field;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import es.teseractos.query.JDBCUtil;
import es.teseractos.query.Parameter;

public class Field implements Parameter {

	private String field;
	private Object value;

	public Field(String field, Object value) {
		this.field = field;
		this.value = value;
	}

	public String getSql() {
		return field;
	}

	public int setValues(PreparedStatement stmt, int index)
			throws SQLException {
		return JDBCUtil.setValue(stmt, index, value);
	}
}
