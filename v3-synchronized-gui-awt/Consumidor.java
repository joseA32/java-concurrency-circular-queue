package pcd_p3;

/**
 * Hilo Consumidor para la Práctica 3 (heredando de Thread).
 * @author josea
 */
public class Consumidor extends Thread {

    private final ColaLenta lacola;

    public Consumidor(ColaLenta cola) {
        this.lacola = cola;
    }

    public void consumir() {
        for (int i = 0; i < 10; i++) {
            try {
                Object extraido = lacola.desacola();
                System.out.println("Consumidor [Hilo ID: " + getId() + "] extrae: " + extraido);
                sleep(150);
            } catch (Exception ex) {
                System.out.println("Consumidor [Hilo ID: " + getId() + "] Error: " + ex.getMessage());
            }
        }
    }

    @Override
    public void run() {
        consumir();
    }
}