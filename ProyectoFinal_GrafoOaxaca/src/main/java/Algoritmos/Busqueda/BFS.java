package Algoritmos.Busqueda;

import Implementacion.Arista;
import Implementacion.ColorVertice;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;
import java.util.*;

/**
 * Implementación de BFS (Breadth‑First Search) para grafos no dirigidos
 * representados con GrafoTDA (lista de adyacencia). Permite: 1. Obtener la
 * secuencia de descubrimiento nivel por nivel. 2. Construir el árbol BFS
 * resultante (sub‑grafo de aristas “tree edges”). 3. Conocer el nivel
 * (distancia en número de aristas) de cada vértice.
 */
public final class BFS {

    private BFS() {
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
     * @param semilla Vértice de partida.
     * @return Un objeto Resultado con la secuencia de visita, árbol BFS y
     * niveles.
     * @throws java.lang.InterruptedException
     */
    public static Resultado ejecutar(GrafoTDA grafo, Vertice semilla) throws InterruptedException {
        Map<Vertice, ColorVertice> color = new HashMap<>();
        Map<Vertice, Integer> nivel = new HashMap<>();
        List<Vertice> orden = new ArrayList<>();

        Map<Vertice, List<Arista>> arbol = new HashMap<>();
        for (Vertice v : grafo.obtenerVertices()) {
            color.put(v, ColorVertice.BLANCO);
            nivel.put(v, Integer.MAX_VALUE);
            arbol.put(v, new ArrayList<>());
        }

        Queue<Vertice> cola = new LinkedList<>();
        color.put(semilla, ColorVertice.GRIS);
        nivel.put(semilla, 0);
        cola.add(semilla);
        orden.add(semilla);

        while (!cola.isEmpty()) {
            Vertice u = cola.poll();
            System.out.println("Procesando vértice: " + u.getNombre());
            Thread.sleep(500);
            int nl = nivel.get(u);
            for (Arista a : grafo.obtenerAdyacentes(u)) {
                Vertice v = a.getDestino();
                if (color.get(v) == ColorVertice.BLANCO) {
                    System.out.println(" → Descubriendo vértice: " + v.getNombre()
                            + " desde " + u.getNombre()
                            + " (nivel " + (nl + 1) + ")");

                    color.put(v, ColorVertice.GRIS);
                    nivel.put(v, nl + 1);
                    cola.add(v);

                    orden.add(v);

                    arbol.get(u).add(new Arista(u, v, a.getDistancia()));
                    arbol.get(v).add(new Arista(v, u, a.getDistancia()));
                }
            }
            color.put(u, ColorVertice.NEGRO);
            System.out.println("Terminando vértice: " + u.getNombre());
            Thread.sleep(300);
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
        return ejecutar(grafo, semilla).orden;
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
        return ejecutar(grafo, semilla).arbol;
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
        return ejecutar(grafo, semilla).nivel;
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
        Resultado res = ejecutar(grafo, semilla);
        Map<Integer, List<Vertice>> grupos = new LinkedHashMap<>();
        for (Vertice v : res.orden) {
            int nl = res.nivel.get(v);
            grupos.computeIfAbsent(nl, k -> new ArrayList<>()).add(v);
        }
        return grupos;
    }
}
