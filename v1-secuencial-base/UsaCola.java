package pcd_p1;

import java.util.*;

/**
 * Clase principal para probar el funcionamiento de la clase Cola.
 * @author josea
 */
public class UsaCola {

    public static void main(String[] args) {
        // Creamos una cola con capacidad para 4 elementos
        Cola c = new Cola(4); // También puedes poner: ICola c = new Cola(4);
        

        Random r = new Random(System.currentTimeMillis());

        // Bucle con 10 iteraciones
        for (int i = 0; i < 10; i++) {
            // Genera un número aleatorio entre 0 y 1
            // Nota: r.nextInt(2) genera 0 o 1 (el límite superior 2 es exclusivo)
            int operacion = r.nextInt(2); 

            try {
                if (operacion == 0) {
                    System.out.println("Iteracion " + i + " [Salio 0 -> Extraer]: Intentando desacolar...");
                    Object extraido = c.desacola();
                    System.out.println(" -> Exito: Se ha extraido el numero " + extraido);
                } else {
                    System.out.println("Iteracion " + i + " [Salio 1 -> Insertar]: Intentando acolar el " + i + "...");
                    c.acola(i);
                    System.out.println(" -> Exito: Se ha insertado el numero " + i);
                }
            } catch (Exception ex) {
                System.out.println(" -> Salta la excepcion: " + ex.getMessage());
            } finally {
                System.out.println("    [Estado actual: " + c.getNum() + " elementos en la cola]");
            }
        }

        System.out.println("FIN DE LA MAIN");
    }
}