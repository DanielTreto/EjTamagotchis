package cuatrovientos.dam.php.Tamagotchis;

import java.util.Random;
import java.util.Scanner;

public class Tamagotchi extends Thread {

	private final int id;
	private boolean vivo = true;
	private int suciedad = 0;
	private final long tiempoNacimiento;
	private final long tiempoComer; // tiempo variable para comer (ms)
	private int contadorComer;
	private int contadorJugar;
	private boolean enBaño = false;
	private boolean ocupado = false;

	// Constantes
	private static final long TIEMPO_VIDA = 300_000; // 5 minutos
	private static final long TIEMPO_ENSUCIARSE = 20_000; // 20 segundos
	private static final long TIEMPO_COMER_MIN = 3_000; // 3 segundos
	private static final long TIEMPO_COMER_RANGO = 4_000; // +4 segundos
	private static final long TIEMPO_BAÑO = 5_000; // 5 segundos
	private static final long SLEEP_HILO = 1_000; // 1 segundo
	private static final int SUCIEDAD_ADVERTENCIA = 5;
	private static final int SUCIEDAD_MUERTE = 10;

	public Tamagotchi(int id) {
		Random rnd = new Random();
		this.id = id;
		this.tiempoComer = TIEMPO_COMER_MIN + rnd.nextInt((int) TIEMPO_COMER_RANGO); // entre 3 y 7 segundos
		this.tiempoNacimiento = System.currentTimeMillis();
	}

	// Getters
	public boolean estaVivo() {
		return vivo;
	}

	public boolean estaOcupado() {
		return ocupado;
	}

	// Hilo principal del Tamagotchi
	@Override
	public void run() {
		System.out.println("¡Tamagotchi " + id + " está vivo!");
		long ultimoEnsuciamiento = System.currentTimeMillis();

		try {
			while (vivo) {
				long edad = System.currentTimeMillis() - tiempoNacimiento;

				// Morir de vejez a los 5 minutos
				if (edad >= TIEMPO_VIDA) {
					morir();
					break;
				}

				// Ensuciarse cada 20 segundos si no está en el baño
				if (System.currentTimeMillis() - ultimoEnsuciamiento >= TIEMPO_ENSUCIARSE && !enBaño) {
					ensuciar();
					ultimoEnsuciamiento = System.currentTimeMillis();
				}

				Thread.sleep(SLEEP_HILO);
			}
		} catch (InterruptedException e) {
			// Hilo interrumpido al morir
		}
	}

	// Jugar con el Tamagotchi
	public void jugar() {
		Scanner sc = new Scanner(System.in);
		Random rnd = new Random();
		int resultado;
		ocupado = true;

		// Generar suma válida (<10)
		int num1, num2;
		do {
			num1 = rnd.nextInt(9) + 1;
			num2 = rnd.nextInt(9) + 1;
		} while (num1 + num2 >= 10);

		System.out.println("\nTamagotchi " + id + " quiere jugar.");

		// Bucle hasta que el usuario acierte o el Tamagotchi muera
		while (vivo) {
			while (true) {
				try {
					System.out.print("\n¿Cuál es el resultado de " + num1 + " + " + num2 + "? ");
					String resultadoStr = sc.nextLine().trim();
					resultado = Integer.parseInt(resultadoStr);
					break;
				} catch (NumberFormatException e) {
					System.out.println("Debes escribir un número entero válido.");
				}
			}

			if (resultado == num1 + num2) {
				System.out.println("\n¡Enhorabuena, has acertado!");
				ocupado = false;
				contadorJugar++;
				break;
			} else {
				System.out.println("\nRespuesta incorrecta, sigue jugando...");
			}
		}
	}

	// Estado de ánimo según veces jugadas
	private String estadoAnimo() {
		if (contadorJugar == 0) {
			return "Está apagado y triste. Hace mucho que no juegas con él...";
		} else if (contadorJugar == 1) {
			return "Se siente algo aburrido. Un rato de juego le animaría.";
		} else {
			return "¡Está feliz y lleno de energía! Le encanta pasar tiempo contigo.";
		}
	}

	// Aumentar suciedad
	private void ensuciar() {
		suciedad++;
		if (suciedad == SUCIEDAD_ADVERTENCIA) {
			System.out.println("\nTamagotchi " + id + " está empezando a estar muy sucio.");
		} else if (suciedad >= SUCIEDAD_MUERTE) {
			System.out.println("\nTamagotchi " + id + " ha muerto de suciedad.");
			vivo = false;
		}
	}

	// Limpiar Tamagotchi usando un hilo independiente
	// para que el cuidador pueda seguir interactuando
	// con otros Tamagotchis mientras este está en el baño
	public void limpiar() {
		ocupado = true;
		enBaño = true;
		new Thread(() -> {
			System.out.println("\nTamagotchi " + id + " entra al baño (" + TIEMPO_BAÑO / 1000 + "s)...");
			try {
				Thread.sleep(TIEMPO_BAÑO);
			} catch (InterruptedException ignored) {
			}
			if (vivo) {
				suciedad = 0;
				System.out.println("\nTamagotchi " + id + " sale limpio y feliz");
				ocupado = false;
				enBaño = false;
			}
		}).start();
	}

	// Comer usando un hilo independiente
	// para que el cuidador pueda seguir interactuando
	// con otros Tamagotchis mientras este come
	public void comer() {
		ocupado = true;
		new Thread(() -> {
			System.out.println("\nTamagotchi " + id + " comienza a comer (" + tiempoComer + "ms)...");
			try {
				Thread.sleep(tiempoComer);
			} catch (InterruptedException ignored) {
			}
			if (vivo) {
				System.out.println("\nTamagotchi " + id + " ha terminado de comer.");
				contadorComer++;
				ocupado = false;
			}
		}).start();
	}

	// Matar Tamagotchi manualmente
	public void matar() {
		System.out.println("\nHas matado a Tamagotchi " + id);
		vivo = false;
	}

	// Morir de vejez
	private void morir() {
		System.out.println("\nTamagotchi " + id + " ha muerto de vejez");
		vivo = false;
	}

	// Mostrar estado actual del Tamagotchi
	public void verEstado() {
		System.out.println("\nTamagotchi " + id);
		System.out.println("Nº de veces que ha comido: " + contadorComer);
		System.out.println("Suciedad (0-10): " + suciedad);
		System.out.println("Estado de ánimo: " + estadoAnimo());
	}
}