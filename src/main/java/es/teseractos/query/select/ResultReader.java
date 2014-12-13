package es.teseractos.query.select;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public abstract class ResultReader<E> {

  public abstract E buildResult(ResultSet rs) throws SQLException;

  protected int getInt(ResultSet rs, String field) throws SQLException {
    if (existsColumn(rs, field))
      return rs.getInt(field);
    return 0;
  }

  protected String getString(ResultSet rs, String field)
      throws SQLException {
    if (existsColumn(rs, field))
      return rs.getString(field);
    return null;
  }

  protected Calendar getCalendar(ResultSet rs, String field)
      throws SQLException {
    if (existsColumn(rs, field))
      return getCalendar(rs.getDate(field));
    return null;
  }

  protected boolean getBoolean(ResultSet rs, String field)
      throws SQLException {
    if (existsColumn(rs, field))
      return rs.getBoolean(field);
    return false;
  }

  protected Calendar getCalendar(Date date) {
    Calendar cal = null;
    if (date != null)
      cal = Calendar.getInstance();
    cal.setTimeInMillis(date.getTime());
    return cal;
  }

  private boolean existsColumn(ResultSet rs, String field)
      throws SQLException {
    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
      if (rs.getMetaData().getColumnName(i).equals(field))
        return true;
    }
    return false;
  }

}
