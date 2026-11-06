package pcd_p3;

import java.util.Random;

/**
 * Hilo Productor para la Práctica 3.
 * @author josea
 */
public class Productor extends Thread {

    private final ColaLenta lacola;

    public Productor(ColaLenta cola) {
        this.lacola = cola;
    }

    public void producir() {
        Random r = new Random(System.nanoTime());

        for (int i = 0; i < 10; i++) {
            float num = r.nextFloat() * 100;
            try {
                lacola.acola(num);
                System.out.println("Productor [Hilo ID: " + getId() + "] introduce: " + num);
                // Pequeña pausa para ver la evolución
                sleep(100);
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