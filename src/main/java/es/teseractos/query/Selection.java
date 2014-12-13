package es.teseractos.query;

import es.teseractos.query.condition.Condition;

/**
 * Esta interfaz se emplea para indicar que una consulta realizará un filtrado
 * sobre los campos de una tupla, y por lo tanto ofrecerá métodos para
 * establecer dichos filtros.
 * 
 * Se ha añadido para facilitar la reutilización de métodos que tomen como
 * parámetro consultas de tipo Select, Update o Delete, ya que acostumbran a
 * establecer valores en la cláusula WHERE.
 * 
 * Su uso no es obligatorio.
 * 
 * @author Fonso
 * 
 */
public interface Selection {

  /**
   * Añade una condición de filtrado a la consulta.
   * 
   * @param condition
   *            Condición a añadir.
   */
  public void addCondition(Condition condition);
}
