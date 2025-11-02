package cuatrovientos.dam.php.Tamagotchis;

import java.util.Random;
import java.util.Scanner;

public class Tamagotchi extends Thread {

    private final int id;
    private boolean vivo = true;
    private int suciedad = 0;
    private int hambre = 0;
    private final long tiempoNacimiento;
    private final long tiempoVida = 300000;
    private final long tiempoComer; 
    private boolean ocupado = false;

    public Tamagotchi(int id) {
        Random rnd = new Random();
        this.id = id;
        this.tiempoComer = 3000 + rnd.nextInt(4000); // entre 3 y 7 segundos
        this.tiempoNacimiento = System.currentTimeMillis();
    }

    public boolean estaVivo() { return vivo; }
    public boolean estaOcupado() {return ocupado;}

    @Override
    public void run() {
        System.out.println("¡Tamagotchi " + id + " está vivo!");
        long ultimoEnsuciamiento = System.currentTimeMillis();
        long ultimoHambriento = System.currentTimeMillis();

        try {
            while (vivo) {
                long edad = System.currentTimeMillis() - tiempoNacimiento;

                // 5 minutos de vida
                if (edad >= tiempoVida && vivo) {
                    morir();
                    break;
                }

                // cada 20 segundos, ensuciar
                if (System.currentTimeMillis() - ultimoEnsuciamiento >= 20000 && vivo) {
                    ensuciar();
                    ultimoEnsuciamiento = System.currentTimeMillis();
                }
                
             // cada 10 segundos, más hambriento
                if (System.currentTimeMillis() - ultimoHambriento >= 10000 && vivo) {
                    hambriento();
                    ultimoHambriento = System.currentTimeMillis();
                }

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // hilo interrumpido al morir
        }
    }

    public void jugar() {
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();
        ocupado = true;
        int num1, num2;
        do {
            num1 = rnd.nextInt(9) + 1;
            num2 = rnd.nextInt(9) + 1;
        } while (num1 + num2 >= 10);

        System.out.println("Tamagotchi " + id + " quiere jugar.");

        while (vivo) {
            System.out.print("¿Cuál es el resultado de " + num1 + " + " + num2 + "? ");
            int resultado = sc.nextInt();

            if (resultado == num1 + num2) {
                System.out.println("¡Enhorabuena, has acertado!");
                ocupado = false;
                break;
            } else {
                System.out.println("Respuesta incorrecta, sigue jugando...");
            }
        }
    }

    private void ensuciar() {
        suciedad++;
        if (suciedad == 5) {
            System.out.println("\nTamagotchi " + id + " está empezando a estar muy sucio.");
        } else if (suciedad >= 10) {
            System.out.println("💀 Tamagotchi " + id + " ha muerto de suciedad.");
            vivo = false;
        }
    }

    public void limpiar() {
    	ocupado = true;
        if (!vivo) return;
        new Thread(() -> {
            System.out.println("Tamagotchi " + id + " entra al baño (5s)...");
            try { Thread.sleep(5000); } catch (InterruptedException ignored) {}
            suciedad = 0;
            System.out.println("Tamagotchi " + id + " sale limpio y feliz 😊");
            ocupado = false;
        }).start();
    }
    
    private void hambriento() {
        hambre++;
        if (hambre == 5) {
            System.out.println("\nTamagotchi " + id + " está empezando a tener hambre.");
        } else if (suciedad >= 10) {
            System.out.println("💀 Tamagotchi " + id + " ha muerto de hambre.");
            vivo = false;
        }
    }

    public void comer() {
    	ocupado = true;
        if (!vivo) return;
        new Thread(() -> {
            System.out.println("Tamagotchi " + id + " comienza a comer ("+tiempoComer+"ms)...");
            try {
                Thread.sleep(tiempoComer);
            } catch (InterruptedException ignored) {}
            System.out.println("Tamagotchi " + id + " ha terminado de comer.");
            hambre=0;
            ocupado = false;

        }).start();
    }

    public void matar() {
        if (!vivo) return;
        if (ocupado) {
        	System.out.println("No puedes matar al Tamagothi "+id+", esta ocupado");
        	return;
        }
        System.out.println("Has matado a Tamagotchi " + id + " 💀");
        vivo = false;
    }

    private void morir() {
        System.out.println("Tamagotchi " + id + " ha muerto de vejez 😢");
        vivo = false;
    }
    
    public void verEstado() {
    	System.out.println("Tamagotchi " + id);
    	System.out.println("Hambre (0-10): "+ hambre);
    	System.out.println("Suciedad (0-10): "+ suciedad);
    	System.out.println("Aburrimiento (0-10): ");
    }
}
