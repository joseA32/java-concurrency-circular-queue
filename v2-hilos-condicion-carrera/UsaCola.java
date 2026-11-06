package PCD_P2;

/**
 * Clase principal de la Práctica 2.
 * @author josea
 */
public class UsaCola {

    public static void main(String[] args) {
        // 1. Crear una cola de 10 elementos compartida por todos
        ColaLenta colaCompartida = new ColaLenta(10);

        try {
            System.out.println("--- FASE 1: Lanzando Productor ---");
            // Productor hereda de Thread, se lanza directamente
            Productor p = new Productor(colaCompartida);
            p.start();
            p.join(); // Esperamos a que el productor termine antes de lanzar los consumidores

            System.out.println("\n--- FASE 2: Lanzando 2 Consumidores a la vez ---");
            // Consumidor implementa Runnable, necesitamos envolverlos en objetos Thread
            Consumidor c1 = new Consumidor(colaCompartida);
            Consumidor c2 = new Consumidor(colaCompartida);

            Thread h1 = new Thread(c1);
            Thread h2 = new Thread(c2);

            // Los lanzamos de forma concurrente
            h1.start();
            h2.start();

            // Esperamos a que ambos finalicen
            h1.join();
            h2.join();

        } catch (InterruptedException ex) {
            System.out.println("Hilo principal interrumpido: " + ex.getMessage());
        }

        System.out.println("\nElementos restantes según la cola: " + colaCompartida.getNum());
        System.out.println("FIN DE LA MAIN");
    }
}