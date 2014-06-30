package es.teseractos.query;

/**
 * Esta interfaz se emplea para indicar que una consulta realizar� una
 * modificaci�n sobre los campos de una tupla, y por lo tanto ofrecer� m�todos
 * para establecer los valores correspondientes.
 * 
 * Se ha a�adido para facilitar la reutilizaci�n de m�todos que tomen como
 * par�metro consultas de tipo Insert o Update, ya que acostumbran a modificar
 * los mismos campos.
 * 
 * Su uso no es obligatorio.
 * 
 * @author Fonso
 * 
 */
public interface Modification {

	/**
	 * 
	 * A�ade un campo para insertar.
	 * 
	 * @param field
	 *            Campo a a�adir.
	 * @param value
	 *            El valor a insertar.
	 */
	public void addField(String field, Object value);
}
