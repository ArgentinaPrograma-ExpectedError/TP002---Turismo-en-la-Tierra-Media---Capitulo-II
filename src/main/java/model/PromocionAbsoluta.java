package model;

import java.util.ArrayList;

public class PromocionAbsoluta extends Promocion {

	private int costoPromocion;

	public PromocionAbsoluta(Integer id, String nombre, TipoAtraccion tipoPromocion,
			ArrayList<Atraccion> atraccionesDeLaPromocion, int precio) {
		super(id, nombre, tipoPromocion, atraccionesDeLaPromocion);
		setCosto(precio);
	}

	public PromocionAbsoluta(String nombre, TipoAtraccion tipoPromocion, ArrayList<Atraccion> atraccionesDeLaPromocion,
			int precio) {
		super(nombre, tipoPromocion, atraccionesDeLaPromocion);
		setCosto(precio);
	}

	/*
	 * Devuelve true si el descuento neto es menor que el costo total sin descuento
	 * de las atracciones incluidas en la Promocion.
	 */
	public boolean precioCorrecto(double valor) {
		return valor < super.getCostoTotalSinDescuento();
	}

	/*
	 * Setea el valor del descuento neto siempre que se cumpla la condicion
	 * descuentoCorrecto()
	 */
	public void setCosto(int valor) {
		if (precioCorrecto(valor)) {
			this.costoPromocion = valor;
		}
	}

	/* Calcula el costo de la Promocion con el descuento aplicado. */
	@Override
	public int calcularCostoPromocion() {
		return this.costoPromocion;
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
