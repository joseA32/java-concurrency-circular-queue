package pcd_p4;

/**
 *
 * @author josea
 */

 //Cola circular sincronizada con condición de sincronización (wait/notifyAll) y 3 intentos.
public class ColaLenta implements ICola {// 

    private int head;
    private int tail;
    private final int capacidad;
    private int numelementos;
    private Object[] datos;
    private final CanvasCola canvas;

    public ColaLenta(int capacidad, CanvasCola elcanvas) {
        this.capacidad = capacidad;
        this.datos = new Object[capacidad];
        this.head = 0;
        this.tail = 0;
        this.numelementos = 0;
        this.canvas = elcanvas;
    }

    @Override
    public int getNum() {
        return this.numelementos;
    }

    /**
     * Intenta acolar esperando hasta 3 veces si la cola está llena.
     */
    @Override
    public synchronized void acola(Object elemento) throws Exception {
        int intentos = 0;

        
        while (colallena()) {
            canvas.avisa("COLA LLENA");
            if (intentos >= 3) {
                throw new Exception("La cola esta llena tras 3 intentos, el hilo desiste.");
            }
            intentos++;
            System.out.println("  -> [Cola llena] Hilo " + Thread.currentThread().getId() 
                    + " espera hueco (Intento " + intentos + "/3)...");
            wait(); // El hilo se duerme y libera el cerrojo hasta que alguien haga notifyAll()
        }

        Thread.sleep(100);
        datos[tail] = elemento;
        Thread.sleep(100);
        tail = (tail + 1) % capacidad;
        Thread.sleep(100);
        numelementos++;

        canvas.representa(datos, head, tail, numelementos);
        
        // Avisamos a todos los hilos que estén esperando (productores o consumidores)
        notifyAll();
    }

    /**
     * Intenta desacolar esperando hasta 3 veces si la cola está vacía.
     */
    @Override
    public synchronized Object desacola() throws Exception {
        int intentos = 0;

        while (colavacia()) {
            canvas.avisa("COLA VACIA");
            if (intentos >= 3) {
                throw new Exception("La cola esta vacia tras 3 intentos, el hilo desiste.");
            }
            intentos++;
            System.out.println("  -> [Cola vacia] Hilo " + Thread.currentThread().getId() 
                    + " espera elemento (Intento " + intentos + "/3)...");
            wait();
        }

        Thread.sleep(100);
        Object elemento = datos[head];
        Thread.sleep(100);
        head = (head + 1) % capacidad;
        Thread.sleep(100);
        numelementos--;

        canvas.representa(datos, head, tail, numelementos);
        
        // Avisamos a todos los productores que esperan un hueco libre
        notifyAll();
        return elemento;
    }

    @Override
    public Object primero() throws Exception {
        if (colavacia()) {
            throw new Exception("La cola esta vacia, no hay primer elemento.");
        }
        return datos[head];
    }

    private boolean colavacia() {
        return numelementos == 0;
    }

    private boolean colallena() {
        return numelementos == capacidad;
    }
}