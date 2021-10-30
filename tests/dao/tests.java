package dao;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.TipoAtraccion;
import model.Usuario;

public class tests {

	@Before
	public void setUp() throws SQLException {
		Connection conexion = ConnectionProvider.getConnection();
		conexion.setAutoCommit(false);
	}

	@After
	public void tearDown() throws SQLException {
		Connection conexion = ConnectionProvider.getConnection();
		conexion.rollback();
		conexion.setAutoCommit(true);
	}

	@Test
	public void actualizarCupoAtraccionTest() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		Atraccion mordor = new Atraccion(4, "Mordor", 30, 5.0, 10, TipoAtraccion.AVENTURA);
		atraccionDAO.update(mordor);
		Atraccion mordorActualizada = atraccionDAO.encontrarPorId(4);
		assertEquals(mordorActualizada.getCupoAtraccion(), mordor.getCupoAtraccion());
	}

	@Test
	public void actualizarDineroUsuarioTest() {
		UsuarioDAO usuarioDAO = DAOFactory.getUserDAO();
		Usuario bill = new Usuario(7, "Bill", "Gates", TipoAtraccion.AVENTURA, 30, 8.0);
		usuarioDAO.update(bill);
		Usuario billActualizado = usuarioDAO.encontrarPorId(7);
		assertEquals(billActualizado.getDineroDisponible(), bill.getDineroDisponible());
	}
	
	@Test
	public void actualizarTiempoUsuarioTest() {
		UsuarioDAO usuarioDAO = DAOFactory.getUserDAO();
		Usuario bill = new Usuario(7, "Bill", "Gates", TipoAtraccion.AVENTURA, 30, 8.0);
		usuarioDAO.update(bill);
		Usuario billActualizado = usuarioDAO.encontrarPorId(7);
		assertEquals(billActualizado.getTiempoDisponible(), bill.getTiempoDisponible(), 0);
	}
	
	

}