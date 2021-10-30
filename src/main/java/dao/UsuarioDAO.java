package dao;

import java.util.ArrayList;

import model.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario> {

	public ArrayList<Usuario> cargar();

	public int update(Usuario t);

	public Usuario encontrarPorId(Integer i);
	
	public int reiniciar();

}