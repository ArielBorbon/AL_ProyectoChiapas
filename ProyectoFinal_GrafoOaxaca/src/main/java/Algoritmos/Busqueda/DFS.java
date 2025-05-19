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
public final class DFS { // Complejidad total depende del método llamado, usualmente O(V+E)

    private static DFSListener listener; //1

    private DFS() { //1
    }

    /**
     * Registrar un listener para recibir notificaciones de nuevas aristas.
     */
    public static void setListener(DFSListener l) { // T(N) = 1, O(1)
        listener = l; //1
    }

    /**
     * Resultado de un recorrido DFS:
     * - orden: lista de vértices en orden de descubrimiento.
     * - arbol: sub‑grafo (lista de adyacencia) con las aristas del árbol DFS.
     */
    public static class Resultado { // T(N) = 2, O(1)
        public final List<Vertice> orden; //1
        public final Map<Vertice, List<Arista>> arbol; //1

        private Resultado(List<Vertice> orden, Map<Vertice, List<Arista>> arbol) { // T(N) = 2, O(1)
            this.orden = orden; //1
            this.arbol = arbol; //1
        }
    }

    /**
     * Ejecuta DFS desde la ciudad semilla.
     * @param grafo El grafo sobre el que trabajar.
     * @param semilla Vértice de inicio.
     * @return Un objeto Resultado con el orden de descubrimiento y el árbol DFS.
     * @throws InterruptedException
     */
    // V = número de vértices, E = número de aristas
    public static Resultado ejecutarDFS(GrafoTDA grafo, Vertice semilla) throws InterruptedException { // T(N) = O(V+E)
        Map<Vertice, ColorVertice> color = new HashMap<>(); //1
        List<Vertice> orden = new ArrayList<>(); //1
        Map<Vertice, List<Arista>> arbol = new HashMap<>(); //1

        // Inicializar estructuras
        for (Vertice v : grafo.obtenerVertices()) { // Bucle V veces
            color.put(v, ColorVertice.BLANCO); //1 (promedio HashMap)
            arbol.put(v, new ArrayList<>()); //1 (promedio HashMap)
        } // Total: V * 2 = 2V -> O(V)

        // Definir visita recursiva con notificación de listener
        Consumer<Vertice> dfsVisit = new Consumer<>() { //1 (declaración)
            // La función accept(u) se ejecuta una vez por cada vértice.
            // El bucle interno itera sobre las aristas adyacentes.
            // En total, todas las llamadas a accept procesarán cada arista dos veces (grafo no dirigido) o una vez (dirigido).
            // Complejidad de todas las llamadas a accept: O(V+E)
            @Override
            public void accept(Vertice u) { // Se llama V veces en total
                color.put(u, ColorVertice.GRIS); //1 (promedio)
                orden.add(u); //1 (amortizado)
                for (Arista a : grafo.obtenerAdyacentes(u)) { // Suma de grados = 2E (o E para dirigidos)
                    Vertice v = a.getDestino(); //1
                    if (color.get(v) == ColorVertice.BLANCO) { //1 (promedio)
                        // Agregar arista al árbol
                        Arista nueva = new Arista(u, v, a.getDistancia()); //1
                        arbol.get(u).add(nueva); //1 (promedio get + amortizado add)
                        arbol.get(v).add(new Arista(v, u, a.getDistancia())); //1 (promedio get + amortizado add)

                        // Notificar al listener y pausar para animación
                        if (listener != null) { //1
                            listener.onNuevaArista(nueva); //1 (asumiendo que el listener es O(1))
                            try {
                                Thread.sleep(500); //1 (constante)
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt(); //1
                            }
                        }

                        // Recursión
                        accept(v); // Llamada recursiva
                    }
                }
                color.put(u, ColorVertice.NEGRO); //1 (promedio)
            }
        };

        // Primera llamada desde la semilla
        dfsVisit.accept(semilla); // O(V_c + E_c) donde V_c, E_c son vértices/aristas del componente conectado

        // Para componentes desconectados
        for (Vertice v : grafo.obtenerVertices()) { // Bucle V veces
            if (color.get(v) == ColorVertice.BLANCO) { //1 (promedio)
                dfsVisit.accept(v); // Se ejecuta si el vértice no ha sido visitado
                                    // El costo total de todas las llamadas a dfsVisit sigue siendo O(V+E)
            }
        } // Total: V * 1 = V -> O(V) para las comprobaciones, las llamadas a dfsVisit ya están contabilizadas

        return new Resultado(orden, arbol); //2 -> O(1)
    } // Complejidad total de ejecutarDFS: O(V) + O(V+E) + O(V) = O(V+E)

    /**
     * Versión que solo devuelve el orden de descubrimiento.
     */
    public static List<Vertice> recorridoOrden(GrafoTDA grafo, Vertice semilla) throws InterruptedException { // T(N) = O(V+E)
        Resultado res = ejecutarDFS(grafo, semilla); // O(V+E)
        return res.orden; //1
    }

    /**
     * Versión que solo devuelve el árbol DFS.
     */
    public static Map<Vertice, List<Arista>> obtenerArbol(GrafoTDA grafo, Vertice semilla) throws InterruptedException { // T(N) = O(V+E)
        Resultado res = ejecutarDFS(grafo, semilla); // O(V+E)
        return res.arbol; //1
    }
}