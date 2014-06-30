package es.teseractos.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Parameter {

	public int setValues(PreparedStatement stmt, int index)
			throws SQLException;
}
