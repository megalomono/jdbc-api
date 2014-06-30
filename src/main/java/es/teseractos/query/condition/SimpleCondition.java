package es.teseractos.query.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import es.teseractos.query.JDBCUtil;

public class SimpleCondition extends Condition {

	private String sql;
	private Object field;

	protected SimpleCondition(String campo, Comparison comparison, Object valor) {
		this.sql = campo.concat(comparison.sql());
		this.field = comparison.equals(Comparison.CONTAINS) ? valor.toString()
				: valor;
	}

	@Override
	public String sql() {
		return sql;
	}

	public int setValues(PreparedStatement stmt, int index) throws SQLException {
		return field != null ? JDBCUtil.setValue(stmt, index, field) : index;
	}
}
