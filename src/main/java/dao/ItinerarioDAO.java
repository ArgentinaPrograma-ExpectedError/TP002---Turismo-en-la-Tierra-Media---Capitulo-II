package dao;

import java.util.ArrayList;

import model.Usuario;

public interface ItinerarioDAO {

	public int agregarAItinerario(Usuario user);

	public ArrayList<String> cargarAtraccionesCompradas(Usuario u);
	
	public int borrarDatos();
}
