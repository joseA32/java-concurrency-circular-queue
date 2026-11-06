package pcd_p4;

import java.util.Random;

/**
 * Hilo Consumidor
 * @author josea
 */
public class Consumidor extends Thread {

    private final ColaLenta lacola;

    public Consumidor(ColaLenta cola) {
        this.lacola = cola;
    }

    public void consumir() {
        Random r = new Random(System.nanoTime());

        try {
            for (int i = 0; i < 15; i++) {
                // Intervalo aleatorio entre 1 y 3 segundos (1000 a 3000 ms)
                int retardo = 1000 + r.nextInt(2000);
                sleep(retardo);

                System.out.println("Consumidor [Hilo ID: " + getId() + "] intenta desacolar (" + (i + 1) + "/15)...");
                Object extraido = lacola.desacola();
                System.out.println("Consumidor [Hilo ID: " + getId() + "] EXTRAE CON EXITO: " + extraido);
            }
            System.out.println("Consumidor [Hilo ID: " + getId() + "] ha extraido sus 15 elementos.");

            // Esperamos 3.5 segundos para asegurarnos de que el último productor que acoló
            // haya terminado su sleep aleatorio (de 1 a 3 seg) y ya esté dentro del wait()
            sleep(3500);

            // Lanzamos los 3 notifyAll() para agotar los 3 intentos de todos los productores restantes
            for (int i = 0; i < 3; i++) {
                synchronized (lacola) {
                    lacola.notifyAll();
                }
                sleep(200); // Pausa breve para que les dé tiempo a despertar, sumar intento y volver al wait
            }

        } catch (Exception ex) {
            System.out.println("Consumidor [Hilo ID: " + getId() + "] FINALIZA POR EXCEPCION: " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        consumir();
    }
}
