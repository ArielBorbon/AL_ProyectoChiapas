package Algoritmos.RutaMasCorta;

import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public final class Dijkstra {                                                               //T(n) = 76n"2 + 112n + 97                 O(n"2)

    private Dijkstra() {
    }

    /**
     * Versión original que devuelve un sub‑grafo con las aristas del camino
     * óptimo.
     *
     * @param grafo
     * @param origen
     * @param destino
     * @return
     * @throws java.lang.InterruptedException
     */
    public static Map<Vertice, List<Arista>> caminoMasCorto(
            GrafoTDA grafo, Vertice origen, Vertice destino) throws InterruptedException {

        Map<Vertice, Double> distancias = new HashMap<>();              //2
        Map<Vertice, Vertice> previos = new HashMap<>();                     //2         
        PriorityQueue<DistanciaNodo> cola
                = new PriorityQueue<>(Comparator.comparingDouble(DistanciaNodo::getDistancia));              //4

        for (Vertice v : grafo.obtenerVertices()) {              //2n + 1
            distancias.put(v, Double.POSITIVE_INFINITY);              //2*n
            previos.put(v, null);                             //1*n
        }

        distancias.put(origen, 0.0);              //1
        cola.add(new DistanciaNodo(origen, 0.0));              //4

        while (!cola.isEmpty()) {              //n+1
            DistanciaNodo dn = cola.poll();              //2*n
            Vertice u = dn.getNodo();              //2*n
            double du = dn.getDistancia();              //2*n

            for (Arista a : grafo.obtenerAdyacentes(u)) {                           //(2n+1)*n
                Vertice v = a.getDestino();              //2*n*n
                double alt = du + a.getDistancia();              //3*n*n
                if (alt < distancias.get(v)) {              //2*n*n

                    distancias.put(v, alt);              //1*n*n
                    previos.put(v, u);              //1*n*n
                    cola.add(new DistanciaNodo(v, alt));              //4*n*n
                }
            }
        }

        Map<Vertice, List<Arista>> subGrafo = new HashMap<>();              //2
        for (Vertice v : grafo.obtenerVertices()) {              //2n + 1
            subGrafo.put(v, new ArrayList<>());              //2*n
        }

        Vertice paso = destino;              //1
        while (previos.get(paso) != null) {              //2n+1
            Vertice ant = previos.get(paso);              //2*n
            double peso = distancias.get(paso) - distancias.get(ant);              //4*n
            subGrafo.get(ant).add(new Arista(ant, paso, peso));              //3*n
            subGrafo.get(paso).add(new Arista(paso, ant, peso));              //2*n
            paso = ant;              //1*n

        }

        return subGrafo;              //1
    }

    public static ResultadoPrevia ejecutarPrevia(GrafoTDA grafo, Vertice origen) {
        Map<Vertice, Double> dist = new HashMap<>();              //2
        Map<Vertice, Vertice> prev = new HashMap<>();              //2
        PriorityQueue<DistanciaNodo> pq
                = new PriorityQueue<>(Comparator.comparingDouble(DistanciaNodo::getDistancia));              //4

        for (Vertice v : grafo.obtenerVertices()) {              //2n +1
            dist.put(v, Double.POSITIVE_INFINITY);              //2*n
            prev.put(v, null);              //1*n
        }
        dist.put(origen, 0.0);              //1
        pq.add(new DistanciaNodo(origen, 0.0));              //4

        while (!pq.isEmpty()) {              //2n+1
            DistanciaNodo dn = pq.poll();              //2*n
            Vertice u = dn.getNodo();              //2*n
            double du = dn.getDistancia();              //2*n
            for (Arista a : grafo.obtenerAdyacentes(u)) {                   //(2n+1)*n
                Vertice v = a.getDestino();              //2*n*n
                double alt = du + a.getDistancia();              //3*n*n
                if (alt < dist.get(v)) {              //2*n*n
                    dist.put(v, alt);              //1*n*n
                    prev.put(v, u);              //1*n*n
                    pq.add(new DistanciaNodo(v, alt));              //4*n*n
                }
            }
        }

        return new ResultadoPrevia(dist, prev);              //3
    }

    /**
     * Nueva versión que devuelve solo la lista de aristas del camino mínimo.
     *
     * @param grafo
     * @param origen
     * @param destino
     * @return
     */
    public static List<Arista> caminoMasCortoListaAristas(
            GrafoTDA grafo, Vertice origen, Vertice destino) throws InterruptedException {

        Map<Vertice, Double> distancias = new HashMap<>();              //2
        Map<Vertice, Vertice> previos = new HashMap<>();              //2
        PriorityQueue<DistanciaNodo> cola
                = new PriorityQueue<>(Comparator.comparingDouble(DistanciaNodo::getDistancia));              //2

        for (Vertice v : grafo.obtenerVertices()) {              //2n +1 
            distancias.put(v, Double.POSITIVE_INFINITY);                    //2*n
            previos.put(v, null);              //1*n
        }
        distancias.put(origen, 0.0);              //1
        cola.add(new DistanciaNodo(origen, 0.0));                       //2

        while (!cola.isEmpty()) {              //2n +1 
            DistanciaNodo dn = cola.poll();              //2*n
            Vertice u = dn.getNodo();              //2*n
            double du = dn.getDistancia();              //2*n

            for (Arista a : grafo.obtenerAdyacentes(u)) {                             //(2n + 1) * n
                Vertice v = a.getDestino();              //2*n*n
                double alt = distancias.get(u) + a.getDistancia();              //4*n*n
                if (alt < distancias.get(v)) {              //2*n*n

                    distancias.put(v, alt);              //1*n*n
                    previos.put(v, u);              //1*n*n
                    cola.add(new DistanciaNodo(v, alt));              //4*n*n
                }
            }
        }

        List<Arista> camino = new ArrayList<>();              //2
        Vertice paso = destino;              //1
        while (previos.get(paso) != null) {              //2n+1
            Vertice ant = previos.get(paso);              //2*n
            double peso = distancias.get(paso) - distancias.get(ant);              //4*n
            camino.add(0, new Arista(ant, paso, peso));              //2*n
            paso = ant;              //1*n
        }
        return camino;              //1
    }

    /**
     * Versión “una ciudad a todas”: devuelve un GrafoTDA que contiene, para
     * cada vértice alcanzable desde 'origen', la arista que lo conecta con su
     * previo en el camino más corto.
     *
     * @param grafo Grafo original
     * @param origen Vértice fuente
     * @return subGrafo con los VÉRTICES de grafo y las aristas de los caminos
     * @throws InterruptedException opcional si quieres animar dentro
     */
    public static GrafoTDA caminoMasCortoTodas(GrafoTDA grafo, Vertice origen)
            throws InterruptedException {

        Map<Vertice, Double> dist = new HashMap<>();              //2
        Map<Vertice, Vertice> prev = new HashMap<>();              //2
        PriorityQueue<DistanciaNodo> pq
                = new PriorityQueue<>(Comparator.comparingDouble(DistanciaNodo::getDistancia));              //2

        for (Vertice v : grafo.obtenerVertices()) {              //2n+1
            dist.put(v, Double.POSITIVE_INFINITY);              //2*n
            prev.put(v, null);              //1*n
        }
        dist.put(origen, 0.0);              //1
        pq.add(new DistanciaNodo(origen, 0.0));              //4

        while (!pq.isEmpty()) {              //2n+1
            DistanciaNodo dn = pq.poll();              //2*n
            Vertice u = dn.getNodo();              //2*n
            double du = dn.getDistancia();              //2*n

            for (Arista a : grafo.obtenerAdyacentes(u)) {                   //(2n+1)*n
                Vertice v = a.getDestino();              //2*n*n
                double alt = du + a.getDistancia();              //3*n*n
                if (alt < dist.get(v)) {              //2*n*n
                    dist.put(v, alt);              //1*n*n
                    prev.put(v, u);              //1*n*n
                    pq.add(new DistanciaNodo(v, alt));                //4*n*n
                }
            }
        }

        GrafoTDA subGrafo = new GrafoTDA();              //2
        for (Vertice v : grafo.obtenerVertices()) {              //2n +1
            subGrafo.agregarVertice(v);              //1*n
        }
        for (Vertice v : grafo.obtenerVertices()) {              //2n+1
            Vertice padre = prev.get(v);              //2*n
            if (padre != null) {              //1*n
                double peso = dist.get(v) - dist.get(padre);              //5*n
                subGrafo.agregarArista(padre, v, peso);              //1*n
            }
        }

        for (Vertice v : grafo.obtenerVertices()) {              //2n+1
            if (v != origen) {              //1*n

            }
        }

        return subGrafo;                // 1
    }

    private static class DistanciaNodo {              //T(n) = 4        O(1)

        private final Vertice nodo;
        private final double distancia;

        public DistanciaNodo(Vertice nodo, double distancia) {
            this.nodo = nodo;              //1
            this.distancia = distancia;              //1
        }

        public Vertice getNodo() {
            return nodo;              //1
        }

        public double getDistancia() {
            return distancia;              //1
        }
    }

    /**
     * Devuelve una representación textual del camino más corto desde el origen
     * hasta el destino, usando el mapa de previos generado por Dijkstra o
     * Bellman–Ford.
     */
    public static String caminoMasTexto(Map<Vertice, Vertice> previos, Vertice destino) {
        List<String> nombres = new ArrayList<>();              //2
        Vertice paso = destino;              //1

        while (paso != null) {              //n+1
            nombres.add(0, paso.getNombre());              //2*n
            paso = previos.get(paso);              //2*n
        }

        if (nombres.size() == 1) {              //2
            return "No hay camino hasta " + destino.getNombre();              //2
        }

        return String.join(" -> ", nombres);              //2
    }

    /**
     * Devuelve el mapa de predecesores para cada vértice alcanzable desde el
     * origen. Sirve para reconstruir los caminos más cortos desde 'origen' a
     * cualquier otro nodo.
     *
     * @param grafo grafo original
     * @param origen vértice fuente
     * @return mapa de predecesores (cada vértice apunta a su anterior en el
     * camino más corto)
     */
    public static Map<Vertice, Vertice> caminoMasCortoPrevia(GrafoTDA grafo, Vertice origen) {
        Map<Vertice, Double> distancias = new HashMap<>();              //2
        Map<Vertice, Vertice> previos = new HashMap<>();              //2
        PriorityQueue<DistanciaNodo> cola
                = new PriorityQueue<>(Comparator.comparingDouble(DistanciaNodo::getDistancia));              //2

        for (Vertice v : grafo.obtenerVertices()) {              //2n+1
            distancias.put(v, Double.POSITIVE_INFINITY);              //2*n
            previos.put(v, null);              //1*n
        }

        distancias.put(origen, 0.0);              //1
        cola.add(new DistanciaNodo(origen, 0.0));              //4

        while (!cola.isEmpty()) {              //n +1
            DistanciaNodo dn = cola.poll();              //2*n
            Vertice u = dn.getNodo();              //2*n
            double du = dn.getDistancia();              //2*n

            for (Arista a : grafo.obtenerAdyacentes(u)) {                    //(2n +1) *n
                Vertice v = a.getDestino();              //2*n*n
                double alt = du + a.getDistancia();              //3*n*n
                if (alt < distancias.get(v)) {              //2*n*n
                    distancias.put(v, alt);              //1*n*n
                    previos.put(v, u);              //1*n*n
                    cola.add(new DistanciaNodo(v, alt));              //4*n*n
                }
            }
        }

        return previos;              //1
    }


    public static class ResultadoPrevia {               // T(n) = 2       O(1)

        public final Map<Vertice, Double> distancias;
        public final Map<Vertice, Vertice> previos;

        public ResultadoPrevia(Map<Vertice, Double> dist, Map<Vertice, Vertice> prev) {
            this.distancias = dist;              //1
            this.previos = prev;              //1
        }
    }

}
