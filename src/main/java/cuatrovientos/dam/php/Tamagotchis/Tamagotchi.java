package cuatrovientos.dam.php.Tamagotchis;

import java.util.Random;
import java.util.Scanner;

public class Tamagotchi extends Thread {

    private final int id;
    private boolean vivo = true;
    private int suciedad = 0;
    private final long tiempoNacimiento;
    private final long tiempoVida = 300000;
    private final long tiempoComer; 
    private int contadorComer;
    private int contadorJugar;
    private boolean enBaño = false;
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

        try {
            while (vivo) {
                long edad = System.currentTimeMillis() - tiempoNacimiento;

                // 5 minutos de vida
                if (edad >= tiempoVida) {
                    morir();
                    break;
                }

                // cada 20 segundos, ensuciar
                if (System.currentTimeMillis() - ultimoEnsuciamiento >= 20000 && !enBaño) {
                    ensuciar();
                    ultimoEnsuciamiento = System.currentTimeMillis();
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

        System.out.println("\nTamagotchi " + id + " quiere jugar.");

        while (vivo) {
            System.out.print("\n¿Cuál es el resultado de " + num1 + " + " + num2 + "? ");
            int resultado = sc.nextInt();

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
    
    private String estadoAnimo() {
        if (contadorJugar == 0) {
            return "Está apagado y triste. Hace mucho que no juegas con él...";
        } else if (contadorJugar == 1) {
            return "Se siente algo aburrido. Un rato de juego le animaría.";
        } else {
            return "¡Está feliz y lleno de energía! Le encanta pasar tiempo contigo.";
        }
    }


    private void ensuciar() {
        suciedad++;
        if (suciedad == 5) {
            System.out.println("\nTamagotchi " + id + " está empezando a estar muy sucio.");
        } else if (suciedad >= 10) {
            System.out.println("\nTamagotchi " + id + " ha muerto de suciedad.");
            vivo = false;
        }
    }

    public void limpiar() {
    	ocupado = true;
    	enBaño = true;
        if (!vivo) return;
        new Thread(() -> {
            System.out.println("\nTamagotchi " + id + " entra al baño (5s)...");
            try { Thread.sleep(5000); } catch (InterruptedException ignored) {}
            suciedad = 0;
            System.out.println("\nTamagotchi " + id + " sale limpio y feliz");
            ocupado = false;
            enBaño = false;
        }).start();
    }

    public void comer() {
    	ocupado = true;
        if (!vivo) return;
        new Thread(() -> {
            System.out.println("\nTamagotchi " + id + " comienza a comer ("+tiempoComer+"ms)...");
            try {
                Thread.sleep(tiempoComer);
            } catch (InterruptedException ignored) {}
            System.out.println("\nTamagotchi " + id + " ha terminado de comer.");
            contadorComer++;
            ocupado = false;
        }).start();
    }

    public void matar() {
        System.out.println("\nHas matado a Tamagotchi " + id);
        vivo = false;
    }

    private void morir() {
        System.out.println("\nTamagotchi " + id + " ha muerto de vejez");
        vivo = false;
    }
    
    public void verEstado() {
    	System.out.println("\nTamagotchi " + id);
    	System.out.println("Nº de veces que ha comido: "+ contadorComer);
    	System.out.println("Suciedad (0-10): "+ suciedad);
    	System.out.println("Estado de ánimo: "+ estadoAnimo());
    }
}
