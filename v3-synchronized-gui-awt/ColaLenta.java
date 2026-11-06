package pcd_p3;

/**
 * Cola circular sincronizada y conectada con CanvasCola para la Práctica 3.
 * @author josea
 */
public class ColaLenta implements ICola {

    private int head;
    private int tail;
    private final int capacidad;
    private int numelementos;
    private Object[] datos;
    private final CanvasCola canvas;

    /**
     * Constructor de la clase ColaLenta.
     * @param capacidad tamaño máximo de la cola
     * @param elcanvas lienzo donde se representará el estado
     */
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
     * Método sincronizado para insertar un elemento en exclusión mutua.
     */
    @Override
    public synchronized void acola(Object elemento) throws Exception {
        if (colallena()) {
            canvas.avisa("COLA LLENA"); // Avisamos al canvas antes de lanzar la excepción
            throw new Exception("La cola está llena, no se puede acolar.");
        }
        Thread.sleep(100);
        datos[tail] = elemento;
        Thread.sleep(100);
        tail = (tail + 1) % capacidad;
        Thread.sleep(100);
        numelementos++;

        // Actualizamos la representación gráfica con el nuevo estado de la cola
        canvas.representa(datos, head, tail, numelementos);
    }

    /**
     * Método sincronizado para extraer un elemento en exclusión mutua.
     */
    @Override
    public synchronized Object desacola() throws Exception {
        if (colavacia()) {
            canvas.avisa("COLA VACIA"); // Avisamos al canvas antes de lanzar la excepción
            throw new Exception("La cola está vacía, no se puede desacolar.");
        }
        Thread.sleep(100);
        Object elemento = datos[head];
        Thread.sleep(100);
        head = (head + 1) % capacidad;
        Thread.sleep(100);
        numelementos--;

        // Actualizamos la representación gráfica con el nuevo estado de la cola
        canvas.representa(datos, head, tail, numelementos);
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