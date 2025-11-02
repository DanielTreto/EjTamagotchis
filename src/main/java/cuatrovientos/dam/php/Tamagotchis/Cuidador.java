package cuatrovientos.dam.php.Tamagotchis;

import java.util.ArrayList;
import java.util.Scanner;

public class Cuidador {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int numTamagotchis = 0;
        int numT;
        ArrayList<Tamagotchi> tamagotchis = new ArrayList<>();

        // Petición del número de Tamagotchis
        while (true) {
            try {
                System.out.print("¿A cuántos Tamagotchis quieres ponerles las pilas?: ");
                String input = sc.nextLine().trim();
                numTamagotchis = Integer.parseInt(input);

                if (numTamagotchis <= 0) {
                    System.out.println("Debe ser un número mayor que cero. Inténtalo de nuevo.\n");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, escribe un número entero.\n");
            }
        }

        // Crear y lanzar los Tamagotchis
        System.out.println("\nPoniendo pilas a " + numTamagotchis + " y lanzándolos al mundo...\n");
        for (int i = 0; i < numTamagotchis; i++) {
            Tamagotchi t = new Tamagotchi(i + 1);
            tamagotchis.add(t);
            t.start();
        }

        // Bucle principal del cuidador
        while (true) {
            Thread.sleep(1000);

            // Elegir Tamagotchi
            while (true) {
                try {
                    System.out.print("\n¿A qué Tamagotchi quieres atender (1-" + numTamagotchis + ")? ");
                    String input = sc.nextLine().trim();
                    numT = Integer.parseInt(input);

                    if (numT < 1 || numT > numTamagotchis) {
                        System.out.println("Número fuera de rango. Intenta de nuevo.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Debes escribir un número entero válido.");
                }
            }

            Tamagotchi t = tamagotchis.get(numT - 1);

            if (!t.estaVivo()) {
                System.out.println("Ese Tamagotchi está muerto.");
                continue;
            }

            if (!t.estaOcupado()) {
                int opcion = 0;

                //  Menú de acciones
                while (true) {
                    try {
                        System.out.println("\n¿Qué quieres hacer con Tamagotchi " + numT + "?");
                        System.out.println("1. Alimentar");
                        System.out.println("2. Limpiar");
                        System.out.println("3. Jugar");
                        System.out.println("4. Ver estado");
                        System.out.println("5. Matar");
                        System.out.print("Elige una opción (1-5): ");

                        String input = sc.nextLine().trim();
                        opcion = Integer.parseInt(input);

                        if (opcion < 1 || opcion > 5) {
                            System.out.println("Opción fuera de rango. Intenta de nuevo.\n");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada no válida. Por favor, escribe un número entre 1 y 5.\n");
                    }
                }

                // Ejecutar acción
                switch (opcion) {
                    case 1:
                        t.comer();
                        break;
                    case 2:
                        t.limpiar();
                        break;
                    case 3:
                        t.jugar();
                        break;
                    case 4:
                        t.verEstado();
                        break;
                    case 5:
                        t.matar();
                        break;
                }
            } else {
                System.out.println("El Tamagotchi " + numT + " está ocupado realizando otra acción.");
            }

            // Comprobación si todos murieron
            int muertos = 0;
            for (Tamagotchi tamagotchi : tamagotchis) {
                if (!tamagotchi.estaVivo()) {
                    muertos++;
                }
            }

            if (muertos == tamagotchis.size()) {
                System.out.println("\nTodos los Tamagotchis han muerto, que pena.");
                break;
            }
        }

    }
}
