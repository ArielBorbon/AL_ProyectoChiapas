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
public final class BFS {                                                                                // T(n) = 140n"2 + 108n + 108           O(n"2)

    private static BFSListener listener;

    private BFS() {
    }

    /**
     * Registrar un listener para notificación de nuevas aristas durante el recorrido.
     */
    public static void setListener(BFSListener l) {
        listener = l;               //1
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
            this.orden = orden;                    //1
            this.arbol = arbol;                     //1
            this.nivel = nivel;                     //1
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
    public static Resultado ejecutarBFS(GrafoTDA grafo, Vertice semillaInicial) {                                                       //28n"2 + 20n + 20
        Map<Vertice, ColorVertice> color = new HashMap<>();                     //2
        Map<Vertice, Integer> nivel = new HashMap<>();                     //2
        Map<Vertice, List<Arista>> arbol = new HashMap<>();                     //2
        List<Vertice> orden = new ArrayList<>();                     //2

        for (Vertice v : grafo.obtenerVertices()) {                     //2n + 2
            color.put(v, ColorVertice.BLANCO);                     //2*n
            nivel.put(v, Integer.MAX_VALUE);                     //2*n
            arbol.put(v, new ArrayList<>());                     //2*n
        }

        // Método auxiliar que arranca un BFS **con su propia cola**
        BiConsumer<Vertice, Boolean> bfsDesde = (semilla, primeraVez) -> {
            Queue<Vertice> cola = new LinkedList<>();                        //2
            color.put(semilla, ColorVertice.GRIS);                     //2
            nivel.put(semilla, 0);                     //1
            cola.add(semilla);                     //1
            orden.add(semilla);                     //1

            while (!cola.isEmpty()) {                     //n+1
                Vertice u = cola.poll();                             //2*n
                for (Arista a : grafo.obtenerAdyacentes(u)) {                     //(2n + 1)  * n
                    Vertice v = a.getDestino();                                           //2*n*n
                    if (color.get(v) == ColorVertice.BLANCO) {                       //3*n*n
                        color.put(v, ColorVertice.GRIS);                                 //2*n*n
                        nivel.put(v, nivel.get(u) + 1);                                  //3*n*n
                        cola.add(v);                                                    //1*n*n
                        orden.add(v);                                                            //1*n*n

                        Arista nueva = new Arista(u, v, a.getDistancia());                       //3*n*n
                        arbol.get(u).add(nueva);                                                     //2*n*n
                        arbol.get(v).add(new Arista(v, u, a.getDistancia()));                            //4*n*n

                        // Notificar al listener y pausar para animación
                        if (listener != null) {                                  //1*n*n
                            listener.onNuevaArista(nueva);                                       //1*n*n
                            try {
                                Thread.sleep(500);                                           //1*n*n
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();                                          //2*n*n
                            }
                        }
                    }
                }
                color.put(u, ColorVertice.NEGRO);                                                //2*n
            }
        };

        bfsDesde.accept(semillaInicial, true);                               //1

        // 2) Si hay vértices aún blancos, arrancarles su propio BFS
        for (Vertice v : grafo.obtenerVertices()) {                                      //2n + 1
            if (color.get(v) == ColorVertice.BLANCO) {                                   //3 * n
                bfsDesde.accept(v, false);                                   //1*n
            }
        }

        return new Resultado(orden, arbol, nivel);                                   //1
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
        return ejecutarBFS(grafo, semilla).orden;               //28n"2 + 20n + 20                       
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
        return ejecutarBFS(grafo, semilla).arbol;   //28n"2 + 20n + 20
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
        return ejecutarBFS(grafo, semilla).nivel;           //28n"2 + 20n + 20
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
    public static Map<Integer, List<Vertice>> agruparPorNivel(GrafoTDA grafo, Vertice semilla) throws InterruptedException {                //28n"2    + 28n + 24
        Resultado res = ejecutarBFS(grafo, semilla);                //28n"2 + 20n + 20
        Map<Integer, List<Vertice>> grupos = new LinkedHashMap<>();                     //2
        for (Vertice v : res.orden) {                                    //2n + 1
            int nl = res.nivel.get(v);                                                       //(3)*n
            grupos.computeIfAbsent(nl, k -> new ArrayList<>()).add(v);                  //(3)*n
        }
        return grupos;                  //1
    }
}
