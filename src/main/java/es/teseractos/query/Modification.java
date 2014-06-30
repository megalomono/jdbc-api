package es.teseractos.query;

/**
 * Esta interfaz se emplea para indicar que una consulta realizará una
 * modificación sobre los campos de una tupla, y por lo tanto ofrecerá métodos
 * para establecer los valores correspondientes.
 * 
 * Se ha añadido para facilitar la reutilización de métodos que tomen como
 * parámetro consultas de tipo Insert o Update, ya que acostumbran a modificar
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
	 * Añade un campo para insertar.
	 * 
	 * @param field
	 *            Campo a añadir.
	 * @param value
	 *            El valor a insertar.
	 */
	public void addField(String field, Object value);
}
