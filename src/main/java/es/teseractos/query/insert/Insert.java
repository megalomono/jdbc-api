package es.teseractos.query.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.teseractos.query.Modification;
import es.teseractos.query.NonExistingObjectException;
import es.teseractos.query.Parameter;
import es.teseractos.query.Query;
import es.teseractos.query.field.Field;

public class Insert extends Query implements Modification {

  private String insert;
  private List<Field> fields;

  /**
   * Construye un objeto para gestionar la creación y parametrización de una
   * inserción en base de datos.
   * 
   * @param table
   *            Tabla o tablas en las que se realiza la inserción.
   */
  public Insert(String table) {
    this.insert = "INSERT INTO ".concat(table);
    this.fields = new ArrayList<Field>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * es.indra.ssp.servicios.controlador.jdbc.util.consulta.ConsultaModificacion
   * #anhadirCampo(java.lang.String, java.lang.Object)
   */
  public void addField(String field, Object value) {
    fields.add(new Field(field, value));
  }

  @Override
  protected String buildSql() {
    return insert.concat(fields()).concat(values());
  }

  /**
   * Ejecuta la inserción en base de datos.
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
      // TODO Log error
      throw new NonExistingObjectException(e);
    } finally {
      closeConnections(rs, stmt, null);
    }
  }

  /* Métodos privados */

  private String fields() {
    String fields = "(";
    for (Field c : this.fields) {
      fields = fields.concat(c.getSql()).concat(", ");
    }
    fields = fields.substring(0, fields.lastIndexOf(","));
    return fields.concat(")");
  }

  private String values() {
    String valores = " VALUES (";
    for (int i = 0; i < fields.size(); i++) {
      valores = valores.concat("?, ");
    }
    valores = valores.substring(0, valores.lastIndexOf(","));
    return valores.concat(")");
  }

  private List<Parameter> getParameters() {
    List<Parameter> parameters = new ArrayList<Parameter>();
    parameters.addAll(fields);
    return parameters;
  }
}
