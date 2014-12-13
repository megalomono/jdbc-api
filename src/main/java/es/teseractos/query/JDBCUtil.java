package es.teseractos.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class JDBCUtil {

  public static int getNextId(Connection con, String fieldId,
      String table) throws SQLException {
    String query = "SELECT MAX(".concat(fieldId).concat(") FROM ")
        .concat(table);
    PreparedStatement stmt = con.prepareStatement(query);
    ResultSet rs = stmt.executeQuery();
    return rs.next() ? rs.getInt(1) + 1 : 1;
  }

  public static int setValue(PreparedStatement stmt, int index, Object value)
      throws SQLException {
    if (value == null) {
      return setNull(stmt, index);
    }
    if (value instanceof Character) {
      return setValue(stmt, index, ((Character) value).toString());
    }
    if (value instanceof String) {
      return setValue(stmt, index, (String) value);
    }
    if (value instanceof Integer) {
      return setValue(stmt, index, (Integer) value);
    }
    if (value instanceof Float) {
      return setValue(stmt, index, (Float) value);
    }
    if (value instanceof Date) {
      return setValue(stmt, index, (Date) value);
    }
    if (value instanceof Calendar) {
      return setValue(stmt, index, (Calendar) value);
    }
    if (value instanceof Boolean) {
      return setValue(stmt, index, (Boolean) value);
    }
    return index;

  }

  private static int setNull(PreparedStatement stmt, int index)
      throws SQLException {
    stmt.setNull(index++, java.sql.Types.NULL);
    return index;
  }

  private static int setValue(PreparedStatement stmt, int index, String value)
      throws SQLException {
    stmt.setString(index++, value);
    return index;
  }

  private static int setValue(PreparedStatement stmt, int index, Integer value)
      throws SQLException {
    stmt.setInt(index++, value);
    return index;
  }

  private static int setValue(PreparedStatement stmt, int index, Float value)
      throws SQLException {
    stmt.setFloat(index++, value);
    return index;
  }

  private static int setValue(PreparedStatement stmt, int index, Date value)
      throws SQLException {
    stmt.setDate(index++, new java.sql.Date(value.getTime()));
    return index;
  }

  private static int setValue(PreparedStatement stmt, int index,
      Calendar value) throws SQLException {
    stmt.setDate(index++, new java.sql.Date(value.getTimeInMillis()));
    return index;
  }

  private static int setValue(PreparedStatement stmt, int index, Boolean value)
      throws SQLException {
    stmt.setString(index++, value ? "t" : "f");
    return index;
  }
}
