package es.teseractos.query.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompositeCondition extends Condition {

  private Operator operator;
  private List<Condition> conditions;

  protected CompositeCondition(Operator operator, Condition... conditions)
      throws EmptyConditionException {
    this.operator = operator;
    this.conditions = new ArrayList<Condition>();
    for (Condition condition : conditions) {
      addCondition(condition);
    }
    if (this.conditions.isEmpty())
      throw new EmptyConditionException();
  }

  public void addCondition(Condition condition) {
    if (condition != null)
      this.conditions.add(condition);
  }

  @Override
  public String sql() {
    StringBuilder sql = new StringBuilder();

    sql.append('(');

    for (Condition c : conditions) {
      sql.append(c.sql());
      sql.append(operator.sql());
    }

    sql.delete(sql.lastIndexOf(operator.sql()), sql.length());
    sql.append(')');

    return sql.toString();
  }

  public int setValues(PreparedStatement stmt, int index) throws SQLException {
    int i = index;
    for (Condition c : conditions) {
      c.setValues(stmt, i++);
    }
    return i;
  }

}
