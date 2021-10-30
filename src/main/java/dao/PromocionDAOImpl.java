package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Promocion;
import model.PromocionAbsoluta;
import model.PromocionAxB;
import model.PromocionPorcentual;
import model.TipoAtraccion;

public class PromocionDAOImpl implements PromocionDAO {

	public ArrayList<Promocion> cargarPromociones(ArrayList<Atraccion> atracciones) {
		try {
			String sql = "SELECT promociones.id,tipo_promocion.tipo,promociones.nombre,tipo_atraccion.tipo,promociones.descuento,promociones.precio FROM promociones JOIN tipo_promocion on promociones.tipo_promocion=tipo_promocion.id JOIN tipo_atraccion on promociones.tipo_atracciones=tipo_atraccion.id";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			ArrayList<Promocion> promociones = new ArrayList<Promocion>();
			while (resultados.next()) {
				int id_promocion = resultados.getInt(1);
				ArrayList<Integer> resultados2 = cargarIdAtracciones(id_promocion);
				if (resultados.getString(2).equals("absoluta")) {
					promociones.add(crearPromocionAbsoluta(resultados, resultados2, atracciones));
				}
				if (resultados.getString(2).equals("porcentual")) {
					promociones.add(crearPromocionPorcentual(resultados, resultados2, atracciones));
				}
				if (resultados.getString(2).equals("axb")) {
					promociones.add(crearPromocionAxB(resultados, resultados2, atracciones));
				}

			}

			return promociones;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private ArrayList<Integer> cargarIdAtracciones(int id_promocion) {
		try {
			ArrayList<Integer> id_atracciones = new ArrayList<Integer>();
			String sql = "SELECT atracciones.id FROM atracciones JOIN promociones_atracciones on promociones_atracciones.id_atraccion=atracciones.id JOIN promociones on promociones_atracciones.id_promocion=promociones.id WHERE promociones.id = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id_promocion);
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {
				id_atracciones.add(resultados.getInt(1));
			}

			return id_atracciones;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Promocion crearPromocionAbsoluta(ResultSet resultados, ArrayList<Integer> atracciones_promo,
			ArrayList<Atraccion> atracciones) throws SQLException {
		ArrayList<Atraccion> atraccionesPromo = new ArrayList<Atraccion>();
		for (Integer i : atracciones_promo) {
			for (Atraccion a : atracciones) {
				if (i.equals(a.getId())) {
					atraccionesPromo.add(a);
				}
			}
		}
		return new PromocionAbsoluta(resultados.getInt(1), resultados.getString(3),
				TipoAtraccion.valueOf(resultados.getString(4)), atraccionesPromo, resultados.getInt(6));
	}

	private Promocion crearPromocionAxB(ResultSet resultados, ArrayList<Integer> atracciones_promo,
			ArrayList<Atraccion> atracciones) throws SQLException {
		ArrayList<Atraccion> atraccionesPromo = new ArrayList<Atraccion>();
		for (Integer i : atracciones_promo) {
			for (Atraccion a : atracciones) {
				if (i.equals(a.getId())) {
					atraccionesPromo.add(a);
				}
			}
		}
		return new PromocionAxB(resultados.getInt(1), resultados.getString(3),
				TipoAtraccion.valueOf(resultados.getString(4)), atraccionesPromo);
	}

	private Promocion crearPromocionPorcentual(ResultSet resultados, ArrayList<Integer> atracciones_promo,
			ArrayList<Atraccion> atracciones) throws SQLException {
		ArrayList<Atraccion> atraccionesPromo = new ArrayList<Atraccion>();
		for (Integer i : atracciones_promo) {
			for (Atraccion a : atracciones) {
				if (i.equals(a.getId())) {
					atraccionesPromo.add(a);
				}
			}
		}
		return new PromocionPorcentual(resultados.getInt(1), resultados.getString(3),
				TipoAtraccion.valueOf(resultados.getString(4)), atraccionesPromo, resultados.getInt(5));
	}

}