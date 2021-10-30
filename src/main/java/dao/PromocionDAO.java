package dao;

import java.util.ArrayList;

import model.Atraccion;
import model.Promocion;

public interface PromocionDAO {

	public abstract ArrayList<Promocion> cargarPromociones(ArrayList<Atraccion> atracciones);

}