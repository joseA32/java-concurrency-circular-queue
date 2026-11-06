package PCD_P2;

import java.util.Random;

/**
 * Hilo Productor creado heredando de la clase Thread.
 * @author josea
 */
public class Productor extends Thread {

    private ColaLenta lacola;

    public Productor(ColaLenta cola) {
        this.lacola = cola;
    }

    public void producir() {
        Random r = new Random(System.nanoTime());

        for (int i = 0; i < 10; i++) {
            float num = r.nextFloat() * 100; // Genera un float aleatorio entre 0.0 y 100.0
            try {
                lacola.acola(num);
                System.out.println("Productor [Hilo ID: " + getId() + "] introduce: " + num);
            } catch (Exception ex) {
                System.out.println("Productor [Hilo ID: " + getId() + "] Error: " + ex.getMessage());
            }
        }
    }

    @Override
    public void run() {
        producir();
    }
}