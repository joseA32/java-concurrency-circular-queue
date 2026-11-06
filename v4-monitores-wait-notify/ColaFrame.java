package pcd_p4;

import java.awt.Color;

/**
 * Ventana principal (Frame) de la Práctica 4.
 * @author josea
 */
public class ColaFrame extends java.awt.Frame {

    public ColaFrame() {
        initComponents();
    }

    private void initComponents() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        this.setLayout(new java.awt.BorderLayout());
        pack();
    }

    private void exitForm(java.awt.event.WindowEvent evt) {
        System.out.println("Fin de la aplicacion");
        System.exit(0);
    }

    public static void main(String args[]) throws InterruptedException {
        int alto = 400;
        int ancho = 750;
        int capacidadCola = 4;

        ColaFrame frame = new ColaFrame();
        CanvasCola cv = new CanvasCola(capacidadCola);
        ColaLenta colaCompartida = new ColaLenta(capacidadCola, cv);

        frame.setSize(ancho, alto);
        frame.setTitle("Practica 4 - Condicion de Sincronizacion (wait / notifyAll)");
        frame.setBackground(Color.YELLOW);
        frame.setLocation(100, 50);

        cv.setSize(ancho, alto);
        frame.add(cv);
        frame.setVisible(true);

        // Instanciamos el pool de 4 hilos productores y 1 hilo consumidor
        Productor[] productores = new Productor[4];
        for (int i = 0; i < 4; i++) {
            productores[i] = new Productor(colaCompartida);
        }
        Consumidor c = new Consumidor(colaCompartida);

        // Lanzamos los 4 productores y el consumidor
        for (int i = 0; i < 4; i++) {
            productores[i].start();
        }
        c.start();

        // Esperamos a que el consumidor termine y luego los productores
        c.join();
        for (int i = 0; i < 4; i++) {
            productores[i].join();
        }

        System.out.println("Todos los hilos han finalizado. Cerrando en 3 segundos...");
        Thread.sleep(3000);
        System.exit(0);
    }
}
