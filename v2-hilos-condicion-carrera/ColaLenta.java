package PCD_P2;

/**
 * Cola circular con retardos para provocar condiciones de carrera
 * @author josea
 */
public class ColaLenta implements ICola {

    private int head;
    private int tail;
    private final int capacidad;
    private int numelementos;
    private Object[] datos;

    public ColaLenta(int capacidad) {
        this.capacidad = capacidad;
        this.datos = new Object[capacidad];
        this.head = 0;
        this.tail = 0;
        this.numelementos = 0;
    }

    @Override
    public int getNum() {
        return this.numelementos;
    }

    @Override
    public void acola(Object elemento) throws Exception {
        if (colallena()) {
            throw new Exception("La cola está llena, no se puede acolar.");
        }
        Thread.sleep(100);
        datos[tail] = elemento;
        Thread.sleep(100);
        tail = (tail + 1) % capacidad;
        Thread.sleep(100);
        numelementos++;
    }

    @Override
    public Object desacola() throws Exception {
        if (colavacia()) {
            throw new Exception("La cola está vacía, no se puede desacolar.");
        }
        Thread.sleep(100);
        Object elemento = datos[head];
        Thread.sleep(100);
        head = (head + 1) % capacidad;
        Thread.sleep(100);
        numelementos--;
        return elemento;
    }

    @Override
    public Object primero() throws Exception {
        if (colavacia()) {
            throw new Exception("La cola está vacía, no hay primer elemento.");
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