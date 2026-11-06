# Simulador Concurrente Productor-Consumidor sobre Cola Circular (Java)

Este repositorio contiene el desarrollo incremental (en **4 versiones evolutivas**) de un sistema concurrente basado en el problema clásico **Productor-Consumidor** sobre una estructura de datos de **Cola Circular** implementada desde cero en Java, con monitorización gráfica en tiempo real mediante **Java AWT**.

El proyecto demuestra el dominio de estructuras de datos, programación multihilo (`Thread` / `Runnable`), análisis de condiciones de carrera (*Race Conditions*), exclusión mutua (`synchronized`), sincronización condicional mediante monitores (`wait()` / `notifyAll()`) y renderizado gráfico sin parpadeo (*Double Buffering*).

---

## Arquitectura y Evolución Incremental del Proyecto

El código está estructurado en cuatro directorios que reflejan la evolución de la arquitectura desde un modelo secuencial hasta un entorno multihilo completamente sincronizado:

### 📁 `v1-secuencial-base` (Versión 1.0)
* **Diseño de Estructura de Datos:** Implementación de una cola circular acotada (`Cola`) basada en un vector estático (`Object[]`) y contrato definido mediante interfaz (`ICola`).
* **Aritmética Modular:** Gestión de punteros `head` y `tail` en tiempo constante $O(1)$ mediante cálculo de módulo `(puntero + 1) % capacidad`, reutilizando posiciones desalojadas sin desplazamiento de memoria.

### 📁 `v2-hilos-race-conditions` (Versión 2.0)
* **Introducción a la Concurrencia:** Implementación de hilos concurrentes mediante herencia directa (`Productor extends Thread`) e interfaz funcional (`Consumidor implements Runnable`).
* **Exposición de Condiciones de Carrera:** Creación de `ColaLenta` con retardos deliberados (`Thread.sleep`) intercalados entre la lectura/escritura de datos y la actualización de punteros para evidenciar la corrupción de estado cuando múltiples hilos acceden a una sección crítica sin sincronización.
* **Semillas de Alta Precisión:** Uso de `System.nanoTime()` por hilo para evitar colisiones en la generación pseudoaleatoria concurrente.

### 📁 `v3-synchronized-gui-awt` (Versión 3.0)
* **Exclusión Mutua:** Resolución de las condiciones de carrera garantizando la atomicidad en las operaciones críticas (`acola` y `desacola`) mediante cerrojos intrínsecos (`synchronized`).
* **Monitor Gráfico en Tiempo Real (AWT):** Desarrollo de una interfaz visual (`ColaFrame` y `CanvasCola`) acoplada al estado del recurso compartido para visualizar en directo los punteros `HEAD`, `TAIL`, el contenido de las celdas y las alertas de estado (`COLA LLENA` / `COLA VACIA`).
* **Técnica de *Double Buffering*:** Sobrecarga de `update(Graphics g)` y renderizado en un búfer de imagen en memoria (*off-screen image*) previo al volcado en pantalla para eliminar el parpadeo (*flickering*) durante los repintados de alta frecuencia.

### 📁 `v4-monitores-wait-notify` (Versión 4.0)
* **Sincronización Condicional (Monitores):** Sustitución del fallo inmediato por espera pasiva mediante `wait()` y notificación global con `notifyAll()` en un escenario de alta contención (**4 hilos Productores frente a 1 Consumidor**).
* **Protección frente a Despertares Espurios:** Evaluación de las condiciones de guarda (`colallena()` / `colavacia()`) dentro de bucles `while` antes de reanudar la ejecución en sección crítica.
* **Política de Reintentos y Prevención de *Deadlock*:** Límite máximo de 3 bloqueos consecutivos por operación antes de desistir por excepción, complementado con un protocolo de cierre en el consumidor que despierta ordenadamente a los productores remanentes para garantizar la finalización limpia del proceso.

---

## Compilación y Ejecución

Cada versión es independiente y puede ejecutarse desde cualquier IDE (NetBeans, IntelliJ, Eclipse) o directamente por terminal situándose en la carpeta correspondiente. Por ejemplo, para lanzar la simulación completa de la **Versión 4**:

```bash
cd v4-monitores-wait-notify
javac -d . *.java
java pcd_p4.ColaFrame
