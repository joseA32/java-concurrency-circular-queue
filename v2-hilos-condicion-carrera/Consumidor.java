package PCD_P2;

/**
 * Hilo Consumidor creado implementando la interfaz Runnable.
 * @author josea
 */
public class Consumidor implements Runnable {

    private ColaLenta lacola;

    public Consumidor(ColaLenta cola) {
        this.lacola = cola;
    }

    public void consumir() {
        Thread actual = Thread.currentThread();

        for (int i = 0; i < 10; i++) {
            try {
                Object extraido = lacola.desacola();
                System.out.println("Consumidor [Hilo ID: " + actual.getId() + "] extrae: " + extraido);
            } catch (Exception ex) {
                System.out.println("Consumidor [Hilo ID: " + actual.getId() + "] Error: " + ex.getMessage());
            }
        }
    }

    @Override
    public void run() {
        consumir();
    }
}