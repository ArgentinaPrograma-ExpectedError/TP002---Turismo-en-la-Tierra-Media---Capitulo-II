package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ConnectionProvider;
import model.TipoAtraccion;
import model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	public int update(Usuario user) {
		try {
			String sql = "UPDATE usuarios SET dinero_disponible = ?, tiempo_disponible = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, user.getDineroDisponible());
			statement.setDouble(2, user.getTiempoDisponible());
			statement.setString(3, user.getNombre());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public ArrayList<Usuario> cargar() {
		try {
			String sql = "SELECT usuarios.id,usuarios.nombre,usuarios.apellido,tipo_atraccion.tipo,usuarios.dinero_disponible,usuarios.tiempo_disponible FROM usuarios JOIN tipo_atraccion on usuarios.tipo_preferido=tipo_atraccion.id";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			ResultSet resultados = statement.executeQuery();
			ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
			while (resultados.next()) {
				usuarios.add(crearUsuario(resultados));
			}

			return usuarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Usuario crearUsuario(ResultSet resultados) throws SQLException {
		return new Usuario(resultados.getInt(1), resultados.getString(2), resultados.getString(3),
				TipoAtraccion.valueOf(resultados.getString(4)), resultados.getInt(5), resultados.getDouble(6));
	}

	public Usuario encontrarPorId(Integer i) {
		try {
			String sql = "SELECT usuarios.id,usuarios.nombre,usuarios.apellido,tipo_atraccion.tipo,usuarios.dinero_disponible,usuarios.tiempo_disponible FROM usuarios JOIN tipo_atraccion on usuarios.tipo_preferido=tipo_atraccion.id where usuarios.id = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, i);

			ResultSet resultados = statement.executeQuery();
			Usuario usuario = crearUsuario(resultados);
			return usuario;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int reiniciar() {
		try {
			String sql = "UPDATE usuarios\r\n"
					+ "SET tiempo_disponible = (SELECT tiempo_disponible FROM usuarios_auxiliar WHERE id = usuarios.id),\r\n"
					+ "dinero_disponible = (SELECT dinero_disponible FROM usuarios_auxiliar WHERE id = usuarios.id)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
}