package Algoritmos.Busqueda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import Implementacion.Arista;
import Implementacion.ColorVertice;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;

/**
 * Implementación de DFS (Depth‑First Search) para grafos representados con
 * GrafoTDA (lista de adyacencia). Permite: 1. Obtener el orden de
 * descubrimiento de los vértices desde una semilla. 2. Construir el árbol DFS
 * resultante (sub‑grafo de aristas “tree edges”). Además, notifica cada arista
 * descubierta a través de DFSListener para animación paso a paso.
 */
public final class DFS {

    private static DFSListener listener;

    private DFS() {
    }

    /**
     * Registrar un listener para recibir notificaciones de nuevas aristas.
     */
    public static void setListener(DFSListener l) {
        listener = l;
    }

    /**
     * Resultado de un recorrido DFS:
     * - orden: lista de vértices en orden de descubrimiento.
     * - arbol: sub‑grafo (lista de adyacencia) con las aristas del árbol DFS.
     */
    public static class Resultado {
        public final List<Vertice> orden;
        public final Map<Vertice, List<Arista>> arbol;

        private Resultado(List<Vertice> orden, Map<Vertice, List<Arista>> arbol) {
            this.orden = orden;
            this.arbol = arbol;
        }
    }

    /**
     * Ejecuta DFS desde la ciudad semilla.
     * @param grafo El grafo sobre el que trabajar.
     * @param semilla Vértice de inicio.
     * @return Un objeto Resultado con el orden de descubrimiento y el árbol DFS.
     * @throws InterruptedException
     */
    public static Resultado ejecutarDFS(GrafoTDA grafo, Vertice semilla) throws InterruptedException {
        Map<Vertice, ColorVertice> color = new HashMap<>();
        List<Vertice> orden = new ArrayList<>();
        Map<Vertice, List<Arista>> arbol = new HashMap<>();

        // Inicializar estructuras
        for (Vertice v : grafo.obtenerVertices()) {
            color.put(v, ColorVertice.BLANCO);
            arbol.put(v, new ArrayList<>());
        }

        // Definir visita recursiva con notificación de listener
        Consumer<Vertice> dfsVisit = new Consumer<>() {
            @Override
            public void accept(Vertice u) {
                color.put(u, ColorVertice.GRIS);
                orden.add(u);
                for (Arista a : grafo.obtenerAdyacentes(u)) {
                    Vertice v = a.getDestino();
                    if (color.get(v) == ColorVertice.BLANCO) {
                        // Agregar arista al árbol
                        Arista nueva = new Arista(u, v, a.getDistancia());
                        arbol.get(u).add(nueva);
                        arbol.get(v).add(new Arista(v, u, a.getDistancia()));

                        // Notificar al listener y pausar para animación
                        if (listener != null) {
                            listener.onNuevaArista(nueva);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                            }
                        }

                        // Recursión
                        accept(v);
                    }
                }
                color.put(u, ColorVertice.NEGRO);
            }
        };

        // Primera llamada desde la semilla
        dfsVisit.accept(semilla);

        // Para componentes desconectados
        for (Vertice v : grafo.obtenerVertices()) {
            if (color.get(v) == ColorVertice.BLANCO) {
                dfsVisit.accept(v);
            }
        }

        return new Resultado(orden, arbol);
    }

    /**
     * Versión que solo devuelve el orden de descubrimiento.
     */
    public static List<Vertice> recorridoOrden(GrafoTDA grafo, Vertice semilla) throws InterruptedException {
        Resultado res = ejecutarDFS(grafo, semilla);
        return res.orden;
    }

    /**
     * Versión que solo devuelve el árbol DFS.
     */
    public static Map<Vertice, List<Arista>> obtenerArbol(GrafoTDA grafo, Vertice semilla) throws InterruptedException {
        Resultado res = ejecutarDFS(grafo, semilla);
        return res.arbol;
    }
}
