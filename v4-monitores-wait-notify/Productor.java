package pcd_p4;

import java.util.Random;

/**
 * 
 * @author josea
 */

//Hilo Productor
public class Productor extends Thread {

    private final ColaLenta lacola;

    public Productor(ColaLenta cola) {
        this.lacola = cola;
    }

    public void producir() {
        Random r = new Random(System.nanoTime());

        try {
            for (int i = 0; i < 15; i++) {
                // Intervalo aleatorio entre 1 y 3 segundos (1000 a 3000 ms)
                int retardo = 1000 + r.nextInt(2000);
                sleep(retardo);

                float num = r.nextFloat() * 100;
                System.out.println("Productor [Hilo ID: " + getId() + "] intenta acolar (" + (i + 1) + "/15): " + num);
                lacola.acola(num);
                System.out.println("Productor [Hilo ID: " + getId() + "] ACOLA CON EXITO: " + num);
            }
            System.out.println("Productor [Hilo ID: " + getId() + "] ha terminado sus 15 inserciones.");
        } catch (Exception ex) {
            // Al estar el try-catch envolviendo el for, si salta la excepción el hilo finaliza directamente
            System.out.println("Productor [Hilo ID: " + getId() + "] FINALIZA POR EXCEPCION: " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        producir();
    }
}
