package es.teseractos.query;

import es.teseractos.query.condition.Condition;

/**
 * Esta interfaz se emplea para indicar que una consulta realizar� un filtrado
 * sobre los campos de una tupla, y por lo tanto ofrecer� m�todos para
 * establecer dichos filtros.
 * 
 * Se ha a�adido para facilitar la reutilizaci�n de m�todos que tomen como
 * par�metro consultas de tipo Select, Update o Delete, ya que acostumbran a
 * establecer valores en la cl�usula WHERE.
 * 
 * Su uso no es obligatorio.
 * 
 * @author Fonso
 * 
 */
public interface Selection {

  /**
   * A�ade una condici�n de filtrado a la consulta.
   * 
   * @param condition
   *            Condici�n a a�adir.
   */
  public void addCondition(Condition condition);
}
