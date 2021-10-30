package model;

import java.util.ArrayList;

public class PromocionPorcentual extends Promocion {

	private int descuento;
	private int costoPromocion;

	public PromocionPorcentual(Integer id, String nombre, TipoAtraccion tipoPromocion, ArrayList<Atraccion> atracciones,
			int descuento) {
		super(id, nombre, tipoPromocion, atracciones);
		this.setDescuento(descuento);
		this.costoPromocion = this.calcularCostoPromocion();
	}

	public PromocionPorcentual(String nombre, TipoAtraccion tipoPromocion, ArrayList<Atraccion> atracciones,
			int descuento) {
		super(nombre, tipoPromocion, atracciones);
		this.setDescuento(descuento);
		this.costoPromocion = this.calcularCostoPromocion();
	}

	/*
	 * Devuelve el porcentaje de descuento que uso para armar la Promocion
	 * Porcentual.
	 */
	public int getDescuento() {
		return descuento;
	}

	/*
	 * Devuelve true si el porcentaje de descuento es mayor que 0 y menor que 100.
	 */
	private boolean descuentoCorrecto(double valor) {
		return valor < 100 && valor > 0;
	}

	/*
	 * Setea el valor del porcentaje de descuento siempre que se cumpla la condicion
	 * descuentoCorrecto()
	 */
	private void setDescuento(int descuento) {
		if (descuentoCorrecto(descuento)) {
			this.descuento = descuento;
		}
	}

	/* Calcula el costo de la Promocion con el descuento aplicado. */
	public int calcularCostoPromocion() {
		return (int) (super.calcularCostoTotalSinDescuento() * (100 - this.getDescuento()) / 100);
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
				+ super.getNombreAtracciones() + "\nCosto: " + getCosto() + " monedas de oro" + "\nDuración: "
				+ super.getDuracion() + " horas";
	}
}
