package Algoritmos.Busqueda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiConsumer;

import Implementacion.Arista;
import Implementacion.ColorVertice;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;

/**
 * Implementación de BFS (Breadth‑First Search) para grafos no dirigidos
 * representados con GrafoTDA (lista de adyacencia). Permite: 1. Obtener la
 * secuencia de descubrimiento nivel por nivel. 2. Construir el árbol BFS
 * resultante (sub‑grafo de aristas “tree edges”). 3. Conocer el nivel
 * (distancia en número de aristas) de cada vértice.
 */
public final class BFS {

    private static BFSListener listener;

    private BFS() {
    }

    /**
     * Registrar un listener para notificación de nuevas aristas durante el recorrido.
     */
    public static void setListener(BFSListener l) {
        listener = l;
    }

    /**
     * Resultado de un recorrido BFS: - orden: lista de vértices en orden de
     * descubrimiento (semilla primero). - arbol: sub‑grafo (mapa de adyacencia)
     * con las aristas del árbol BFS. - nivel: mapa que asigna a cada vértice su
     * nivel (0 = semilla).
     */
    public static class Resultado {

        public final List<Vertice> orden;
        public final Map<Vertice, List<Arista>> arbol;
        public final Map<Vertice, Integer> nivel;

        private Resultado(List<Vertice> orden,
                Map<Vertice, List<Arista>> arbol,
                Map<Vertice, Integer> nivel) {
            this.orden = orden;
            this.arbol = arbol;
            this.nivel = nivel;
        }
    }

    /**
     * Ejecuta BFS desde la ciudad semilla.
     *
     * @param grafo La estructura de datos GrafoTDA.
     * @param semillaInicial
     * @return Un objeto Resultado con la secuencia de visita, árbol BFS y
     * niveles.
     */
    public static Resultado ejecutarBFS(GrafoTDA grafo, Vertice semillaInicial) {
        Map<Vertice, ColorVertice> color = new HashMap<>();
        Map<Vertice, Integer> nivel = new HashMap<>();
        Map<Vertice, List<Arista>> arbol = new HashMap<>();
        List<Vertice> orden = new ArrayList<>();

        for (Vertice v : grafo.obtenerVertices()) {
            color.put(v, ColorVertice.BLANCO);
            nivel.put(v, Integer.MAX_VALUE);
            arbol.put(v, new ArrayList<>());
        }

        // Método auxiliar que arranca un BFS **con su propia cola**
        BiConsumer<Vertice, Boolean> bfsDesde = (semilla, primeraVez) -> {
            Queue<Vertice> cola = new LinkedList<>();
            color.put(semilla, ColorVertice.GRIS);
            nivel.put(semilla, 0);
            cola.add(semilla);
            orden.add(semilla);

            while (!cola.isEmpty()) {
                Vertice u = cola.poll();
                for (Arista a : grafo.obtenerAdyacentes(u)) {
                    Vertice v = a.getDestino();
                    if (color.get(v) == ColorVertice.BLANCO) {
                        color.put(v, ColorVertice.GRIS);
                        nivel.put(v, nivel.get(u) + 1);
                        cola.add(v);
                        orden.add(v);

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
                    }
                }
                color.put(u, ColorVertice.NEGRO);
            }
        };

        bfsDesde.accept(semillaInicial, true);

        // 2) Si hay vértices aún blancos, arrancarles su propio BFS
        for (Vertice v : grafo.obtenerVertices()) {
            if (color.get(v) == ColorVertice.BLANCO) {
                bfsDesde.accept(v, false);
            }
        }

        return new Resultado(orden, arbol, nivel);
    }

    /**
     * Devuelve solo la lista de vértices en orden de descubrimiento.
     *
     * @param grafo
     * @param semilla
     * @return
     * @throws java.lang.InterruptedException
     */
    public static List<Vertice> recorrido(GrafoTDA grafo, Vertice semilla) throws InterruptedException {
        return ejecutarBFS(grafo, semilla).orden;
    }

    /**
     * Devuelve solo el sub‑grafo árbol BFS.
     *
     * @param grafo
     * @param semilla
     * @return
     * @throws java.lang.InterruptedException
     */
    public static Map<Vertice, List<Arista>> obtenerArbol(GrafoTDA grafo, Vertice semilla) throws InterruptedException {
        return ejecutarBFS(grafo, semilla).arbol;
    }

    /**
     * Devuelve el mapa de niveles asignados a cada vértice. (0 = semilla, 1 =
     * vecinos de semilla, etc.)
     *
     * @param grafo
     * @param semilla
     * @return
     * @throws java.lang.InterruptedException
     */
    public static Map<Vertice, Integer> obtenerNiveles(GrafoTDA grafo, Vertice semilla) throws InterruptedException {
        return ejecutarBFS(grafo, semilla).nivel;
    }

    /**
     * Agrupa la secuencia de descubrimiento por niveles: devuelve un map de
     * nivel para lista de vértices en ese nivel, en orden.
     *
     * @param grafo
     * @param semilla
     * @return
     * @throws java.lang.InterruptedException
     */
    public static Map<Integer, List<Vertice>> agruparPorNivel(GrafoTDA grafo, Vertice semilla) throws InterruptedException {
        Resultado res = ejecutarBFS(grafo, semilla);
        Map<Integer, List<Vertice>> grupos = new LinkedHashMap<>();
        for (Vertice v : res.orden) {
            int nl = res.nivel.get(v);
            grupos.computeIfAbsent(nl, k -> new ArrayList<>()).add(v);
        }
        return grupos;
    }
}
