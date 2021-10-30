package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Usuario;

public class ItinerarioDAOImpl implements ItinerarioDAO {

	public int agregarAItinerario(Usuario user) {
		try {
			String sql = "INSERT INTO itinerarios (id_usuario, id_atraccion) VALUES (?, ?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			int rows = 0;
			for (Atraccion atraccion : user.getAtraccionesCompradas()) {
				statement.setInt(1, user.getId());
				statement.setInt(2, atraccion.getId());
				rows += statement.executeUpdate();
			}
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public ArrayList<String> cargarAtraccionesCompradas(Usuario u) {
		try {
			String sql = "SELECT atracciones.nombre FROM atracciones JOIN itinerarios on itinerarios.id_atraccion=atracciones.id JOIN usuarios on itinerarios.id_usuario=usuarios.id WHERE usuarios.id = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, u.getId());
			ResultSet resultados = statement.executeQuery();

			ArrayList<String> atraccionesCompradas = new ArrayList<String>();
			while (resultados.next()) {
				atraccionesCompradas.add(resultados.getString(1));
			}

			return atraccionesCompradas;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int borrarDatos() {
		try {
			String sql = "DELETE from itinerarios";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
}
