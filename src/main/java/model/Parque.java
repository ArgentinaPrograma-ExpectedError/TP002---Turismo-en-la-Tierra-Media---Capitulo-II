package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import dao.AtraccionDAO;
import dao.DAOFactory;
import dao.ItinerarioDAO;
import dao.PromocionDAO;
import dao.UsuarioDAO;

public class Parque {

	private List<Usuario> usuariosParque = new ArrayList<Usuario>();
	private ArrayList<Sugerencia> sugerenciasParque = new ArrayList<Sugerencia>();
	private String nombreParque;
	private UsuarioDAO usuarioDAO = DAOFactory.getUserDAO();
	private AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
	private PromocionDAO promoDAO = DAOFactory.getPromocionDAO();
	private ItinerarioDAO itinerarioDAO = DAOFactory.getItinerarioDAO();

	public Parque(String nombre) {
		this.nombreParque = nombre;
		this.usuariosParque = usuarioDAO.cargar();
		this.sugerenciasParque = cargarSugerencias(atraccionDAO);
		OrdenarAtracciones(sugerenciasParque);
	}

	public String getNombre() {
		return nombreParque;
	}

	private ArrayList<Sugerencia> cargarSugerencias(AtraccionDAO atraccionDAO) {
		ArrayList<Sugerencia> sugerencias = new ArrayList<Sugerencia>();
		ArrayList<Atraccion> atracciones = atraccionDAO.cargar();
		ArrayList<Promocion> promociones = promoDAO.cargarPromociones(atracciones);

		sugerencias.addAll(atracciones);
		sugerencias.addAll(promociones);

		return sugerencias;
	}

	/*
	 * Si la Sugerencia es una Atraccion devuelve true si se encuentra dentro de las
	 * Atraccion compradas por el Usuario.
	 */
	public boolean contieneAtraccionSimple(Usuario u, Sugerencia s) {
		boolean valor = false;
		if (u.getAtraccionesCompradas().contains(s)
				|| itinerarioDAO.cargarAtraccionesCompradas(u).contains(s.getNombre())) {
			valor = true;
		}
		return valor;
	}

	/*
	 * Si la Sugerencia es una Promocion recorre el ArrayList de Atraccion compradas
	 * por el Usuario y por cada Atraccion comprada pregunta si est� contenida
	 * dentro del ArrayList de las Promocion sugeridas.
	 */
	public boolean contieneAtraccionEnPromocion(Usuario u, Sugerencia s) {
		boolean valor = false;
		for (Atraccion a : u.getAtraccionesCompradas()) {
			if (s.getAtraccionesPromocion().contains(a))
				valor = true;
		}
		for (Atraccion a : s.getAtraccionesPromocion()) {
			if (itinerarioDAO.cargarAtraccionesCompradas(u).contains(a.getNombre())) {
				valor = true;
			}
		}
		return valor;
	}

	private void OrdenarAtracciones(ArrayList<Sugerencia> sugerencias) {
		Collections.sort(sugerencias, new Ordenador());
	}

	public void sugerir(Usuario u) throws IOException {

		System.out.println(u.getDatos());
		System.out.println("---------------------------------------------------------");
		boolean seguirOfertando = true;
		for (Sugerencia s : this.sugerenciasParque) {
			if (seguirOfertando && s.getTipo() == u.getTipoPreferido() && s.getCosto() <= u.getDineroDisponible()
					&& s.getDuracion() <= u.getTiempoDisponible() && !contieneAtraccionSimple(u, s)
					&& !contieneAtraccionEnPromocion(u, s) && s.hayCupo()) {
				seguirOfertando = mostrarSugerencias(u, s);
			}
		}
		for (Sugerencia s : this.sugerenciasParque) {
			if (seguirOfertando && s.getTipo() != u.getTipoPreferido() && s.getCosto() <= u.getDineroDisponible()
					&& s.getDuracion() <= u.getTiempoDisponible() && !contieneAtraccionSimple(u, s)
					&& !contieneAtraccionEnPromocion(u, s) && s.hayCupo()) {
				seguirOfertando = mostrarSugerencias(u, s);
			}
		}
		System.out.println("---------------------------------------------------------");
		System.out.println("---No hay más sugerencias para el usuario seleccionado---");
		System.out.println("---------------------------------------------------------");

		escribirUsuario(u);
		escribirAtracciones(sugerenciasParque);
	}

	private boolean mostrarSugerencias(Usuario u, Sugerencia s) throws IOException {
		System.out.println(s.getDatos());
		System.out.println("Desea aceptar la sugerencia? s/n | x para volver al menú.");
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		String caracter = in.nextLine();
		caracter = caracter.toLowerCase();
		try {
			if (caracter.equals("x")) {
				return false;
			}
			if (caracter.equals("s")) {
				u.comprar(s);
				return true;
			} else if (!caracter.equals("n")) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("---------------------------------------------------------");
			System.out.println("----Valor ingresado no válido. Por favor ingrese s/n-----");
			System.out.println("---------------------------------------------------------");
			mostrarSugerencias(u, s);
		}
		return true;
	}

	private ArrayList<Integer> idUsuarios(List<Usuario> usuarios) {
		ArrayList<Integer> id_usuarios = new ArrayList<Integer>();
		for (Usuario u : usuarios) {
			id_usuarios.add(u.getId());
		}
		return id_usuarios;
	}

	private Usuario encontrarPorID(List<Usuario> usuarios, Integer i) {
		Usuario usuario = null;
		for (Usuario u : usuarios) {
			if (u.getId().equals(i)) {
				usuario = u;
			}
		}
		return usuario;
	}

	public void mostrarMenu() throws IOException {

		System.out.println("Usuarios registrados:\n");
		for (Usuario u : usuariosParque) {
			System.out.println(u.getId() + ") " + u.getNombre() + " " + u.getApellido());
		}
		System.out.println("Presiona 'x' para salir");
		System.out.println("\nIngrese el ID del usuario que realizará la compra:");
		System.out.println("---------------------------------------------------------");
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		String seleccion = in.next().toLowerCase();
		if (seleccion.equals("x")) {
			System.out.println("******** Fin del servicio ********");
			return;
		}
		try {
			if (idUsuarios(usuariosParque).contains(Integer.parseInt(seleccion))) {
				sugerir(encontrarPorID(usuariosParque, Integer.parseInt(seleccion)));
				mostrarMenu();
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("---------------------------------------------------------");
			System.out.println("------------------Ingrese un ID válido-------------------");
			System.out.println("---------------------------------------------------------");

			mostrarMenu();
		}
	}

	public void escribirUsuario(Usuario u) {
		usuarioDAO.update(u);
		itinerarioDAO.agregarAItinerario(u);
		u.borrarListaDeAtraccionesCompradas();
	}

	public void escribirAtracciones(ArrayList<Sugerencia> sugerencias) {
		for (Sugerencia a : sugerencias) {
			if (a instanceof Atraccion) {
				atraccionDAO.update((Atraccion) a);
			}
		}
	}
}
