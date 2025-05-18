package Algoritmos.RutaMasCorta;

import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;
import java.util.*;

/**
 * Implementación del algoritmo de Bellman–Ford para grafos representados
 * mediante GrafoTDA (listas de adyacencia). Permite detectar ciclos de peso
 * negativo y guardar la evolución de las distancias en cada iteración.
 */
public final class BellmanFord {                                                    // T(n) = 30n"2 + 60 +  112                              O(n"2)

    private BellmanFord() {
    }

    /**
     * Resultado completo de ejecutar Bellman–Ford.
     */
    public static class Resultado {     // T(n) = 6     O(1)

        /**
         * Distancias finales desde el origen a cada vértice
         */
        public final Map<Vertice, Double> distancias;
        /**
         * Vértice previo en el camino más corto
         */
        public final Map<Vertice, Vertice> previos;
        /**
         * Listado de snapshots de distancias tras cada iteración (tamaño = |V|,
         * incluyendo estado inicial en índice 0).
         */
        public final List<Map<Vertice, Double>> iteraciones;

        private Resultado(Map<Vertice, Double> distancias,
                Map<Vertice, Vertice> previos,
                List<Map<Vertice, Double>> iteraciones) {
            this.distancias = distancias;              //2
            this.previos = previos;              //2
            this.iteraciones = iteraciones;              //2
        }
    }

    /**
     * Ejecuta Bellman–Ford sobre el grafo, partiendo de 'origen'.
     *
     * @param grafo La estructura de datos de tipo GrafoTDA.
     * @param origen Vértice de partida.
     * @return Un objeto Resultado conteniendo distancias, previos y tabla de
     * iteraciones.
     * @throws java.lang.InterruptedException
     * @throws IllegalArgumentException si detecta un ciclo de peso negativo
     * alcanzable.
     */
    public static Resultado ejecutar(GrafoTDA grafo, Vertice origen) throws InterruptedException {                  // 15n"2  +  15n + 41
        Map<Vertice, Double> dist = new HashMap<>();              //2
        Map<Vertice, Vertice> prev = new HashMap<>();              //2
        for (Vertice v : grafo.obtenerVertices()) {              //2n +1
            dist.put(v, Double.POSITIVE_INFINITY);              //2*n
            prev.put(v, null);              //1*n
        }
        dist.put(origen, 0.0);              //1

        List<Arista> aristas = new ArrayList<>();              //2
        for (Vertice u : grafo.obtenerVertices()) {
            aristas.addAll(grafo.obtenerAdyacentes(u));                     //2n +1
        }

        List<Map<Vertice, Double>> iteraciones = new ArrayList<>();              //2
        iteraciones.add(new HashMap<>(dist));              //2

        int V = grafo.obtenerVertices().size();              //3
        for (int i = 1; i < V; i++) {              //2n +2
            boolean cambiado = false;              //1*n
            for (Arista a : aristas) {              //(2n +1 ) *n
                Vertice u = a.getOrigen();              //2*n*n
                Vertice v = a.getDestino();              //2*n*n
                double peso = a.getDistancia();              //2*n*n
                double alt = dist.get(u) + peso;              //3*n*n


                if (alt < dist.get(v)) {                  //2*n*n
                    dist.put(v, alt);              //1*n*n
                    prev.put(v, u);              //1*n*n
                    cambiado = true;              //1*n*n
                }
            }
            iteraciones.add(new HashMap<>(dist));              //1*n
            if (!cambiado) {              //1*n
                break;              //1*n
            }
        }

        for (Arista a : aristas) {                //2n +1
            Vertice u = a.getOrigen();              //2*n
            Vertice v = a.getDestino();              //2*n
            if (dist.get(u) + a.getDistancia() < dist.get(v)) {              //5*n
                throw new IllegalArgumentException(
                        "Se ha detectado un ciclo de peso negativo en el grafo."              //1*n
                );
            }
        }

        return new Resultado(dist, prev, iteraciones);              //7
    }
    
    
    
    
    
    

    /**
     * Reconstruye el camino más corto desde el origen (usado en ejecutar) hasta
     * 'destino', en forma de lista ordenada de aristas.
     *
     * @param resultado El resultado devuelto por ejecutar(...).
     * @param destino Vértice final del camino.
     * @return Lista de aristas en orden desde origen hasta destino. Si no hay
     * camino, la lista queda vacía.
     */
    public static List<Arista> reconstruirCamino(Resultado resultado, Vertice destino) {            // 11n + 9 
        List<Arista> camino = new ArrayList<>();              //2
        Map<Vertice, Vertice> prev = resultado.previos;              //2
        Map<Vertice, Double> dist = resultado.distancias;              //2

        Vertice paso = destino;              //1
        while (prev.get(paso) != null) {              //2n+1
            Vertice ant = prev.get(paso);              //2n
            double peso = dist.get(paso) - dist.get(ant);                   //4n
            camino.add(0, new Arista(ant, paso, peso));              //2n
            paso = ant;              //n
        }
        return camino;              //1
    }

    /**
     * Devuelve una representación textual del camino más corto desde el origen
     * hasta el destino, usando el mapa de previos generado por Dijkstra o
     * Bellman–Ford.
     * @param previos
     * @param destino
     * @return 
     */
    public static String caminoMasTexto(Map<Vertice, Vertice> previos, Vertice destino) {                   // 4n + 9
        List<String> nombres = new ArrayList<>();              //2
        Vertice paso = destino;              //1

        while (paso != null) {              //1
            nombres.add(0, paso.getNombre());              //2n
            paso = previos.get(paso);              //2n
        }

        if (nombres.size() == 1) {              //2
            return "No hay camino hasta " + destino.getNombre();                     //1
        }

        return String.join(" -> ", nombres);              //2
    }

    
    
    
    
    public static GrafoTDA caminoMasCortoTodas(GrafoTDA grafo, Vertice origen)          // 15n"2 + 30n + 47
            throws InterruptedException {
        Resultado res = ejecutar(grafo, origen);        // 15n"2  +  15n + 42

        GrafoTDA subGrafo = new GrafoTDA();              //2
        for (Vertice v : grafo.obtenerVertices()) {              //2n+1
            subGrafo.agregarVertice(v);              //n
        }
        for (Vertice v : grafo.obtenerVertices()) {              //2n+1
            Vertice padre = res.previos.get(v);              //2n
            if (padre != null) {              //n
                double peso = res.distancias.get(v) - res.distancias.get(padre);              //6n
                subGrafo.agregarArista(padre, v, peso);              //n
            }
        }
        return subGrafo;              //1
    }

}
