package pcd_p3;

import java.awt.Color;

/**
 * Ventana principal (Frame) de la Práctica 3.
 * @author josea
 */
public class ColaFrame extends java.awt.Frame {

    /**
     * Creates new form ColaFrame
     */
    public ColaFrame() {
        initComponents();
    }

    private void initComponents() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        // Usamos BorderLayout para que el Canvas se ajuste automáticamente a los bordes del Frame
        this.setLayout(new java.awt.BorderLayout());
        pack();
    }

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {
        System.out.println("Fin de la aplicacion");
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws InterruptedException {
        int alto = 400;
        int ancho = 750;
        int capacidadCola = 4;

        // 1. Crear los objetos ColaFrame, CanvasCola, ColaLenta, Productor y Consumidor
        ColaFrame frame = new ColaFrame();
        CanvasCola cv = new CanvasCola(capacidadCola);
        ColaLenta colaCompartida = new ColaLenta(capacidadCola, cv);

        Productor p = new Productor(colaCompartida);
        Consumidor c = new Consumidor(colaCompartida);

        // 2. Definir el tamaño y colores del Frame y añadir el Canvas al mismo
        frame.setSize(ancho, alto);
        frame.setTitle("Practica 3 - Visualización de Cola Circular");
        frame.setBackground(Color.YELLOW);
        frame.setLocation(100, 50);

        cv.setSize(ancho, alto);
        frame.add(cv);
        frame.setVisible(true);

        // 3. Lanzar los hilos Productor y Consumidor
        p.start();
        c.start();

        // 4. Esperar a que finalicen, esperar 3 segundos y finalizar la ejecución
        p.join();
        c.join();

        System.out.println("Hilos finalizados. Esperando 3 segundos antes de cerrar...");
        Thread.sleep(3000);
        System.exit(0);
    }
}