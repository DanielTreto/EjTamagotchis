package cuatrovientos.dam.php.Tamagotchis;

import java.util.Random;


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
        this.tiempoComer = 3000 + rnd.nextInt(4000);
        this.tiempoNacimiento = System.currentTimeMillis();
    }

    public boolean estaVivo() { return vivo; }
    public boolean estaOcupado() {return ocupado;}

    @Override
    public void run() {
        
    }


}
