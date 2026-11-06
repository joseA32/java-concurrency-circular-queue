package pcd_p1;

/**
 * Implementación de una cola circular usando un vector.
 * @author josea
 * @version 1.0
 */
public class Cola implements ICola {

    private int head;
    private int tail;
    private final int capacidad;
    private int numelementos;
    private Object[] datos;

    /**
     * Constructor de la clase. Inicializa la cola y los atributos de la clase.
     * @param capacidad cantidad de elementos con que se inicializa el vector.
     */
    public Cola(int capacidad) {
        this.capacidad = capacidad;
        this.head = 0;
        this.tail = 0;
        this.numelementos = 0;
        this.datos = new Object[capacidad];
    }

    /**
     * Comprueba si la cola está vacía.
     * @return true si no hay elementos, false en caso contrario.
     */
    private boolean colavacia() {
        return this.numelementos == 0;
    }

    /**
     * Comprueba si la cola está llena.
     * @return true si el número de elementos alcanza la capacidad, false en caso contrario.
     */
    private boolean colallena() {
        return this.numelementos == this.capacidad;
    }

    /**
     * Devuelve el número de elementos que hay en la cola.
     * @return número de elementos actuales.
     */
    @Override
    public int getNum() {
        return this.numelementos;
    }

    /**
     * Añade el elemento a la cola si no está llena.
     * @param elemento que se acola.
     * @throws Exception si la cola está llena.
     */
    @Override
    public void acola(Object elemento) throws Exception {
        if (this.colallena()) {
            throw new Exception("La cola está llena, no se puede acolar");
        } else {
            this.datos[this.tail] = elemento;
            // Posición circular: sumamos 1 y calculamos el módulo de la capacidad
            this.tail = (this.tail + 1) % this.capacidad;
            this.numelementos++;
        }
    }

    /**
     * Extrae el primer elemento de la cola si existe.
     * @return elemento que se extrae.
     * @throws Exception si la cola está vacía.
     */
    @Override
    public Object desacola() throws Exception {
        if (this.colavacia()) {
            throw new Exception("La cola esta vacia, no se puede desacolar");
        } else {
            Object elemento = this.datos[this.head];
            // Posición circular: sumamos 1 y calculamos el módulo de la capacidad
            this.head = (this.head + 1) % this.capacidad;
            this.numelementos--;
            return elemento;
        }
    }

    /**
     * Devuelve el primer elemento de la cola sin extraerlo.
     * @return elemento que está el primero en la cola.
     * @throws Exception si la cola está vacía.
     */
    @Override
    public Object primero() throws Exception {
        if (this.colavacia()) {
            throw new Exception("La cola esta vacia, no hay primer elemento");
        } else {
            return this.datos[this.head];
        }
    }
}