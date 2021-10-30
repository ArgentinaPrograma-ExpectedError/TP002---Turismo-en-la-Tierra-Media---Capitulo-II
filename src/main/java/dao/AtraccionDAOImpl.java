package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.TipoAtraccion;

public class AtraccionDAOImpl implements AtraccionDAO {

	public int update(Atraccion attraction) {
		try {
			String sql = "UPDATE atracciones SET cupo = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, attraction.getCupoAtraccion());
			statement.setString(2, attraction.getNombre());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public ArrayList<Atraccion> cargar() {
		try {
			String sql = "SELECT atracciones.id,nombre,costo,duracion,cupo,tipo_atraccion.tipo FROM atracciones JOIN tipo_atraccion on atracciones.tipo=tipo_atraccion.id";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			ResultSet resultados = statement.executeQuery();
			ArrayList<Atraccion> usuarios = new ArrayList<Atraccion>();
			while (resultados.next()) {
				usuarios.add(crearAtraccion(resultados));
			}

			return usuarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Atraccion crearAtraccion(ResultSet resultados) throws SQLException {
		return new Atraccion(resultados.getInt(1), resultados.getString(2), resultados.getInt(3),
				resultados.getDouble(4), resultados.getInt(5), TipoAtraccion.valueOf(resultados.getString(6)));
	}

	public Atraccion encontrarPorId(Integer i) {
		try {
			String sql = "SELECT atracciones.id,nombre,costo,duracion,cupo,tipo_atraccion.tipo FROM atracciones JOIN tipo_atraccion on atracciones.tipo=tipo_atraccion.id where atracciones.id = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, i);

			ResultSet resultados = statement.executeQuery();
			Atraccion atraccion = crearAtraccion(resultados);
			return atraccion;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int reiniciar() {
		try {
			String sql = "UPDATE atracciones\r\n"
					+ "SET cupo = (SELECT cupo FROM atracciones_auxiliar WHERE id = atracciones.id)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
}