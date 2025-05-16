package Algoritmos.Busqueda;

import Implementacion.Arista;
import Implementacion.ColorVertice;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;
import java.util.*;
import java.util.function.Consumer;

/**
 * Implementación de DFS (Depth‑First Search) para grafos representados con
 * GrafoTDA (lista de adyacencia). Permite: 1. Obtener el orden de
 * descubrimiento de los vértices desde una semilla. 2. Construir el árbol DFS
 * resultante (sub‑grafo de aristas “tree edges”).
 */
public final class DFS {

    private DFS() {
    }

    /**
     * Resultado de un recorrido DFS: - orden: lista de vértices en orden de
     * descubrimiento. - arbol: sub‑grafo (lista de adyacencia) con las aristas
     * del árbol DFS.
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
     *
     * @param grafo El grafo sobre el que trabajar.
     * @param semilla Vértice de inicio.
     * @return Un objeto Resultado con el orden de descubrimiento y el árbol
     * DFS.
     * @throws java.lang.InterruptedException
     */
    public static Resultado ejecutarDFS(GrafoTDA grafo, Vertice semilla) throws InterruptedException {
        Map<Vertice, ColorVertice> color = new HashMap<>();
        List<Vertice> orden = new ArrayList<>();
        Map<Vertice, List<Arista>> arbol = new HashMap<>();

        for (Vertice v : grafo.obtenerVertices()) {
            color.put(v, ColorVertice.BLANCO);
            arbol.put(v, new ArrayList<>());
        }

        // Visita recursiva
        Consumer<Vertice> dfsVisit = new Consumer<>() {
            @Override
            public void accept(Vertice u) {
                color.put(u, ColorVertice.GRIS);
                orden.add(u);
                for (Arista a : grafo.obtenerAdyacentes(u)) {
                    Vertice v = a.getDestino();
                    if (color.get(v) == ColorVertice.BLANCO) {
                        arbol.get(u).add(new Arista(u, v, a.getDistancia()));
                        arbol.get(v).add(new Arista(v, u, a.getDistancia()));
                        accept(v);  // recursión
                    }
                }
                color.put(u, ColorVertice.NEGRO);
            }
        };

        // Primer componente
        dfsVisit.accept(semilla);

        // Cualquier otro vértice desconectado
        for (Vertice v : grafo.obtenerVertices()) {
            if (color.get(v) == ColorVertice.BLANCO) {
                dfsVisit.accept(v);
            }
        }

        return new Resultado(orden, arbol);
    }

    /**
     * Versión que solo devuelve el orden de descubrimiento.
     *
     * @param grafo
     * @param semilla
     * @return
     * @throws java.lang.InterruptedException
     */
    public static List<Vertice> recorridoOrden(GrafoTDA grafo, Vertice semilla) throws InterruptedException {
        Resultado res = ejecutarDFS(grafo, semilla);
        return res.orden;
    }

    /**
     * Versión que solo devuelve el árbol DFS.
     *
     * @param grafo
     * @param semilla
     * @return
     * @throws java.lang.InterruptedException
     */
    public static Map<Vertice, List<Arista>> obtenerArbol(GrafoTDA grafo, Vertice semilla) throws InterruptedException {
        Resultado res = ejecutarDFS(grafo, semilla);
        return res.arbol;
    }

    private static void dfsVisitar(
            GrafoTDA grafo,
            Vertice u,
            Map<Vertice, ColorVertice> color,
            List<Vertice> orden,
            Map<Vertice, List<Arista>> arbol) throws InterruptedException {

        color.put(u, ColorVertice.GRIS);

//        System.out.println("Entrando en DFS: " + u.getNombre());
//        Thread.sleep(500);
        orden.add(u);

        for (Arista a : grafo.obtenerAdyacentes(u)) {
            Vertice v = a.getDestino();
            if (color.get(v) == ColorVertice.BLANCO) {

//                System.out.println("  Arista de árbol DFS: "
//                        + u.getNombre() + " → " + v.getNombre());
//                Thread.sleep(500);
                arbol.get(u).add(new Arista(u, v, a.getDistancia()));
                arbol.get(v).add(new Arista(v, u, a.getDistancia()));
                dfsVisitar(grafo, v, color, orden, arbol);
            }
        }

        color.put(u, ColorVertice.NEGRO);
//        System.out.println("Saliendo de DFS: " + u.getNombre());
//        Thread.sleep(300);
    }
}
