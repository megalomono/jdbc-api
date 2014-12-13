package es.teseractos.query.delete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.teseractos.query.NonExistingObjectException;
import es.teseractos.query.Parameter;
import es.teseractos.query.Query;
import es.teseractos.query.Selection;
import es.teseractos.query.condition.Condition;

public class Delete extends Query implements Selection {

  private String delete;
  private List<Condition> conditions;

  /**
   * Construye un objeto para gestionar la creación y parametrización de un
   * borrado sobre base de datos.
   * 
   * @param table
   *            Tabla o tablas sobre las que se realiza el borrado.
   */
  public Delete(String table) {
    this.delete = "DELETE FROM ".concat(table);
    this.conditions = new ArrayList<Condition>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.indra.ssp.servicios.controlador.jdbc.util.consulta.Seleccion#
   * anhadirCondicion
   * (es.indra.ssp.servicios.controlador.jdbc.util.consulta.condicion
   * .Condicion)
   */
  public void addCondition(Condition condition) {
    if (condition != null)
      conditions.add(condition);
  }

  @Override
  protected String buildSql() {
    return delete.concat(where(conditions));
  }

  /**
   * Ejecuta el borrado en base de datos.
   * 
   * @param connection
   *            Conexión con la base de datos.
   */
  public void execute(Connection connection) {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = connection.prepareStatement(sql());
      setValues(stmt, getParameters());
      int result = stmt.executeUpdate();
      if (result == 0) {
        throw new NonExistingObjectException();
      }
    } catch (SQLException e) {
      // TODO Log errors
      throw new NonExistingObjectException(e);
    } finally {
      closeConnections(rs, stmt, null);
    }
  }

  /* Métodos privados */

  private List<Parameter> getParameters() {
    List<Parameter> parameters = new ArrayList<Parameter>();
    parameters.addAll(conditions);
    return parameters;
  }

}
