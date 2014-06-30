package es.teseractos.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import es.teseractos.query.condition.Condition;

public abstract class Query {

	protected String sql = null;

	/**
	 * Devuelve la consulta SQL que se ejecutará contra la base de datos.
	 * 
	 * @return Consulta SQL.
	 */
	public String sql() {
		return this.sql != null ? this.sql : buildSql();
	}

	/**
	 * Permite establecer directamente la consulta SQL que se ejecutará contra
	 * la base de datos. Es responsabilidad del invocador establecer los
	 * parámetros en el mismo orden en que se declaran en la consulta.
	 * 
	 * @param sql
	 *            La consulta a ejecutar.
	 */
	public void setQuery(String sql) {
		this.sql = sql;
	}

	protected abstract String buildSql();

	protected String where(List<Condition> conditions) {
		String where = "";
		for (Condition c : conditions) {
			if (!c.sql().isEmpty()) {
				where = where.contains("WHERE") ? where.concat(" AND ") : where
						.concat(" WHERE ");
				where = where.concat(c.sql());
			}
		}
		return where;
	}

	protected void setValues(PreparedStatement stmt,
			List<Parameter> parametros) throws SQLException {
		int i = 1;
		for (Parameter p : parametros) {
			try {
				i = p.setValues(stmt, i);
			} catch (NullPointerException e) {
				throw new QueryErrorException(e);
			}
		}
	}

	protected void closeConnections(ResultSet rs, PreparedStatement stmt,
			Connection con) {
		try {
			if (rs != null)
				rs.close();

			if (stmt != null)
				stmt.close();

			if (con != null)
				con.close();

		} catch (SQLException sqle) {
			// TODO Log error
		}
	}
}
