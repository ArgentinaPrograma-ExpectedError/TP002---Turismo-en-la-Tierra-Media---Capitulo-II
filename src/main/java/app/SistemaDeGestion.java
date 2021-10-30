package app;

import java.io.IOException;

import model.Parque;

public class SistemaDeGestion {

	public static void main(String[] args) throws IOException {
		Parque tierraMedia = new Parque("Parque de Atracciones de la Tierra Media");
		iniciarParque(tierraMedia);
	}

	public static void iniciarParque(Parque parque) throws IOException {
		System.out.println("******** Bievenido a " + parque.getNombre() + " ********\n");
		parque.mostrarMenu();
	}
}