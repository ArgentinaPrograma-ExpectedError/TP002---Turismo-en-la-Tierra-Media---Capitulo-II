package dao;

import java.util.ArrayList;

import model.Atraccion;

public interface AtraccionDAO extends GenericDAO<Atraccion> {

	public ArrayList<Atraccion> cargar();

	public int update(Atraccion t);

	public Atraccion encontrarPorId(Integer i);
	
	public int reiniciar();

}