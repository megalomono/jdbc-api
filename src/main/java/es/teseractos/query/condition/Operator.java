package es.teseractos.query.condition;

public enum Operator {
  AND, OR;

  protected String sql() {
    switch (this) {
    case AND:
      return " AND ";
    case OR:
      return " OR ";
    default:
      return "";
    }
  }
}
