package model;

import java.util.ArrayList;
import java.util.Collections;

public class PromocionAxB extends Promocion {

	private int costoPromocion;

	public PromocionAxB(Integer id, String nombre, TipoAtraccion tipoPromocion,
			ArrayList<Atraccion> atraccionesDeLaPromocion) {
		super(id, nombre, tipoPromocion, atraccionesDeLaPromocion);
		this.costoPromocion = this.calcularCostoPromocion();
	}

	public PromocionAxB(String nombre, TipoAtraccion tipoPromocion, ArrayList<Atraccion> atraccionesDeLaPromocion) {
		super(nombre, tipoPromocion, atraccionesDeLaPromocion);
		this.costoPromocion = this.calcularCostoPromocion();
	}

	public int calcularCostoPromocion() {
		ArrayList<Atraccion> atracciones = super.getAtraccionesPromocion();
		Collections.sort(atracciones);
		Integer descuento = atracciones.get(0).getCosto();
		return super.getCostoTotalSinDescuento() - descuento;
	}

	/*
	 * Devuelve el costo de la Promocion y lo convierte a tipo Integer para poder
	 * comparar luego.
	 */
	@Override
	public Integer getCosto() {
		return this.costoPromocion;
	}

	/* Devuelve en formato String los datos de la Promocion. */
	public String getDatos() {
		return super.getNombre() + "\nTipo: " + super.getTipo() + "\nAtracciones incluidas: "
				+ super.getNombreAtracciones() + "\nCosto: " + this.getCosto() + " monedas de oro" + "\nDuración: "
				+ super.getDuracion() + " horas";
	}
}
