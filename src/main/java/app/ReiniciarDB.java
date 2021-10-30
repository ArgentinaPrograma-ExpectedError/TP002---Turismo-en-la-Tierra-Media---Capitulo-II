package app;

import dao.AtraccionDAO;
import dao.DAOFactory;
import dao.ItinerarioDAO;
import dao.UsuarioDAO;

public class ReiniciarDB {

	public static void main(String[] args) {

		UsuarioDAO usuarioDAO = DAOFactory.getUserDAO();
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		ItinerarioDAO itinerarioDAO = DAOFactory.getItinerarioDAO();

		usuarioDAO.reiniciar();
		atraccionDAO.reiniciar();
		itinerarioDAO.borrarDatos();
	}
}
