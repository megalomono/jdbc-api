package es.teseractos.query.select;

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

public class Select<E> extends Query implements Selection {

	private String select;
	private String orderBy;
	private List<Condition> conditions;

	/**
	 * Construye un objeto para gestionar la creación y parametrización de una
	 * consulta sobre base de datos.
	 * 
	 * @param table
	 *            Tabla o tablas sobre las que se realiza la consulta.
	 */
	public Select(String table) {
		this.select = "SELECT * FROM ".concat(table);
		this.conditions = new ArrayList<Condition>();
		this.orderBy = "";
	}

	/**
	 * Seleccion determinadas columnas de la tabla sobre la que se realiza la
	 * consulta.
	 * 
	 * @param columns
	 *            Columnas sobre las que se quiere realizar la proyección.
	 */
	public void projection(String... columns) {
		String projection = "";

		for (String column : columns) {
			projection = projection.concat(column).concat(", ");
		}
		projection = projection.substring(0, projection.lastIndexOf(","));
		this.select = this.select.replace("*", projection);
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

	/**
	 * Añade una condición de ordenación a la consulta.
	 * 
	 * @param field
	 *            Campo por el que se quiere ordenar.
	 */
	public void orderBy(String field) {
		orderBy = orderBy.isEmpty() ? orderBy.concat(" ORDER BY ") : orderBy
				.concat(", ");
		orderBy = orderBy.concat(field);
	}

	/**
	 * Añade una condición de ordenación a la consulta y el sentido del orden,
	 * ascendente o descendente.
	 * 
	 * @param field
	 *            Campo por el que se quiere ordenar.
	 * @param order
	 *            Sentido de la ordenación.
	 */
	public void orderBy(String field, Order order) {
		orderBy(field);
		orderBy = orderBy.concat(order.sql());
	}

	@Override
	protected String buildSql() {
		return select.concat(where(conditions)).concat(orderBy);
	}

	/**
	 * Ejecuta la consulta sobre la base de datos.
	 * 
	 * @param conexion
	 *            Conexión a la base de datos.
	 * @param reader
	 *            Objeto encargado de la lectura de las tuplas devueltas por la
	 *            consulta.
	 * @return El primer objeto devuelto por la consulta o null si no existen
	 *         resultados.
	 */
	public E uniqueResult(Connection conexion, ResultReader<E> reader) {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conexion.prepareStatement(sql());
			setValues(stmt, getParameters());
			rs = stmt.executeQuery();
			return rs.next() ? reader.buildResult(rs) : null;
		} catch (SQLException e) {
			// Log error
			throw new NonExistingObjectException(e);
		} finally {
			closeConnections(rs, stmt, null);
		}
	}

	/**
	 * Ejecuta la consulta sobre la base de datos.
	 * 
	 * @param conexion
	 *            Conexión a la base de datos.
	 * @param reader
	 *            Objeto encargado de la lectura de las tuplas devueltas por la
	 *            consulta.
	 * @return Una lista con los objetos devueltos por la consulta.
	 */
	public List<E> list(Connection conexion, ResultReader<E> reader) {
		List<E> toReturn = new ArrayList<E>();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		int numRegistros = 0;

		try {
			stmt = conexion.prepareStatement(sql());
			setValues(stmt, getParameters());
			rs = stmt.executeQuery();
			while (rs.next() && numRegistros < 1000) {
				toReturn.add(reader.buildResult(rs));
				numRegistros++;
			}
			return toReturn;

		} catch (SQLException e) {
			// Log error
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
