package model;

import java.util.ArrayList;

public interface Sugerencia {

	String getNombre();

	TipoAtraccion getTipo();

	Integer getCosto();

	Double getDuracion();

	ArrayList<Atraccion> getAtraccionesPromocion();

	boolean hayCupo();

	String getDatos();

}
