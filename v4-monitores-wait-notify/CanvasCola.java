package pcd_p4;

import java.awt.*;

/**
 * Lienzo sobre el que se dibuja el estado de la cola circular.
 * @author josea
 */
public class CanvasCola extends Canvas {

    private int head;
    private int tail;
    private final int capacidad;
    private int numelementos;
    private Object[] datos;
    private String mensaje;
    

    public CanvasCola() {
        this.capacidad = 0;
    }
   
    public CanvasCola(int capacidad) {
        this.capacidad = capacidad;
        this.head = 0;
        this.tail = 0;
        this.numelementos = 0;
        this.datos = new Object[capacidad];
        this.mensaje = "";
        this.setBackground(Color.PINK); // Color de fondo del Canvas como en clase
    }

    /**
     * Sobrecarga update para no borrar el fondo y evitar parpadeos.
     */
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * Muestra un mensaje de aviso ("COLA LLENA" o "COLA VACIA").
     * @param mensaje texto del aviso
     */
    public void avisa(String mensaje) {
        this.mensaje = mensaje;
        repaint();
    }

    /**
     * Recoge el estado de la cola y solicita repintar el canvas.
     */
    public void representa(Object[] buf, int head, int tail, int numele) {
        this.datos = buf;
        this.head = head;
        this.tail = tail;
        this.numelementos = numele;
        this.mensaje = ""; // Limpiamos el aviso de error si la operación tuvo éxito
        repaint();
    }

    /**
     * Dibuja en el Canvas usando una imagen de buffer (og) para evitar parpadeo.
     */
    @Override
    public void paint(Graphics g) {
        Font fuenteTitulo = new Font("Tahoma", Font.BOLD, 18);
        Font fuenteDatos = new Font("Tahoma", Font.PLAIN, 12);
        Font fuenteAviso = new Font("Segoe Script", Font.BOLD | Font.ITALIC, 22);

        // Creamos la imagen buffer del tamaño actual del canvas
        Image img = createImage(getWidth(), getHeight());
        Graphics og = img.getGraphics();

        // 1. Pintamos el fondo en el buffer para limpiar el fotograma anterior
        og.setColor(getBackground());
        og.fillRect(0, 0, getWidth(), getHeight());

        // 2. Información general arriba
        og.setFont(fuenteTitulo);
        og.setColor(Color.BLACK);
        og.drawString("Estado de la Cola Circular (Elementos: " + numelementos + "/" + capacidad + ")", 50, 40);

        // 3. Dibujamos las casillas del vector de la cola
        int xInicial = 40;
        int yCasilla = 130;
        int anchoCasilla = 65;
        int altoCasilla = 50;

        for (int i = 0; i < capacidad; i++) {
            int x = xInicial + (i * anchoCasilla);

            // Comprobamos si la casilla 'i' tiene un elemento válido actualmente en la cola
            boolean ocupada = false;
            if (numelementos > 0) {
                for (int k = 0; k < numelementos; k++) {
                    if ((head + k) % capacidad == i) {
                        ocupada = true;
                        break;
                    }
                }
            }

            // Relleno de la casilla (Blanco si tiene dato, Gris claro si está libre)
            if (ocupada) {
                og.setColor(Color.WHITE);
            } else {
                og.setColor(Color.LIGHT_GRAY);
            }
            og.fillRect(x, yCasilla, anchoCasilla, altoCasilla);

            // Borde de la casilla e índice
            og.setColor(Color.BLACK);
            og.drawRect(x, yCasilla, anchoCasilla, altoCasilla);
            og.setFont(fuenteDatos);
            og.drawString("[" + i + "]", x + 22, yCasilla - 5);
            
            //o.drawImage(...)//Se usa para poner imagenes. Hay que cargarlas 

            // Si está ocupada, escribimos el número dentro (formateado a 1 decimal si es float)
            if (ocupada && datos[i] != null) {
                og.setColor(Color.BLUE);
                String textoDato = (datos[i] instanceof Float)
                        ? String.format("%.1f", (Float) datos[i])
                        : datos[i].toString();
                og.drawString(textoDato, x + 10, yCasilla + 30);
            }

            // Indicador de puntero HEAD (arriba de la casilla)
            if (i == head) {
                og.setColor(Color.BLUE);
                og.fillOval(x + 25, yCasilla - 35, 15, 15);
                og.drawString("HEAD", x + 15, yCasilla - 40);
            }

            // Indicador de puntero TAIL (debajo de la casilla)
            if (i == tail) {
                og.setColor(Color.DARK_GRAY);
                og.fillOval(x + 25, yCasilla + altoCasilla + 10, 15, 15);
                og.drawString("TAIL", x + 18, yCasilla + altoCasilla + 40);
            }
        }

        // 4. Si hay mensaje de aviso ("COLA LLENA" o "COLA VACIA"), lo mostramos en rojo
        if (mensaje != null && !mensaje.isEmpty()) {
            og.setFont(fuenteAviso);
            og.setColor(Color.RED);
            og.drawString("AVISO: " + mensaje, 200, 300);
        }

        // 5. Volcamos la imagen buffer completa sobre el Graphics real del Canvas
        g.drawImage(img, 0, 0, null);
    }
}
