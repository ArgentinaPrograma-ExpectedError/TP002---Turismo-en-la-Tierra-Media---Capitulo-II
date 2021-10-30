package model;

import java.util.Comparator;

public class Ordenador implements Comparator<Sugerencia> {

	public int compare(Sugerencia primeraSugerencia, Sugerencia segundaSugerencia) {
		if (primeraSugerencia instanceof Atraccion && segundaSugerencia instanceof Atraccion) {
			return compare2(primeraSugerencia, segundaSugerencia);
		}
		if (primeraSugerencia instanceof Promocion && segundaSugerencia instanceof Promocion) {
			return compare2(primeraSugerencia, segundaSugerencia);
		}
		if (primeraSugerencia instanceof Promocion) {
			return -1;
		} else {
			return 1;
		}
	}

	public int compare2(Sugerencia primeraSugerencia, Sugerencia segundaSugerencia) {
		if (primeraSugerencia.getCosto().compareTo(segundaSugerencia.getCosto()) == 0) {
			return primeraSugerencia.getDuracion().compareTo(segundaSugerencia.getDuracion()) * -1;
		}
		return primeraSugerencia.getCosto().compareTo(segundaSugerencia.getCosto()) * -1;
	}
}
