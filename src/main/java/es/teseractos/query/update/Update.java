package es.teseractos.query.update;

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
import es.teseractos.query.Selection;
import es.teseractos.query.condition.Condition;
import es.teseractos.query.field.Field;

public class Update extends Query implements Modification, Selection {

	private String update;
	private List<Field> fields;
	private List<Condition> conditions;

	/**
	 * Construye un objeto para gestionar la creación y parametrización de una
	 * actualizacion sobre base de datos.
	 * 
	 * @param table
	 *            Tabla o tablas sobre las que se realiza la actualizacion.
	 */
	public Update(String table) {
		this.update = "UPDATE ".concat(table);
		this.fields = new ArrayList<Field>();
		this.conditions = new ArrayList<Condition>();
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
		return update.concat(fields()).concat(where(conditions));
	}

	/**
	 * Ejecuta la actualización en base de datos.
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
		String fields = " SET ";
		for (Field c : this.fields) {
			fields = fields.concat(c.getSql()).concat("=?, ");
		}
		return fields.substring(0, fields.lastIndexOf(","));
	}

	private List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.addAll(fields);
		parameters.addAll(conditions);
		return parameters;
	}
}
