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

public final class Dijkstra {

    private Dijkstra() {
    }

    /**
     * Versión original que devuelve un sub‑grafo con las aristas del camino
     * óptimo.
     * @param grafo
     * @param origen
     * @param destino
     * @return 
     */
    public static Map<Vertice, List<Arista>> caminoMasCorto(
            GrafoTDA grafo, Vertice origen, Vertice destino) {

        Map<Vertice, Double> distancias = new HashMap<>();
        Map<Vertice, Vertice> previos = new HashMap<>();
        PriorityQueue<DistanciaNodo> cola
                = new PriorityQueue<>(Comparator.comparingDouble(DistanciaNodo::getDistancia));

        for (Vertice v : grafo.obtenerVertices()) {
            distancias.put(v, Double.POSITIVE_INFINITY);
            previos.put(v, null);
        }

        distancias.put(origen, 0.0);
        cola.add(new DistanciaNodo(origen, 0.0));

        while (!cola.isEmpty()) {
            DistanciaNodo dn = cola.poll();
            Vertice u = dn.getNodo();
            double du = dn.getDistancia();

            for (Arista a : grafo.obtenerAdyacentes(u)) {
                Vertice v = a.getDestino();
                double alt = du + a.getDistancia();
                if (alt < distancias.get(v)) {
                    distancias.put(v, alt);
                    previos.put(v, u);
                    cola.add(new DistanciaNodo(v, alt));
                }
            }
        }

        Map<Vertice, List<Arista>> subGrafo = new HashMap<>();
        for (Vertice v : grafo.obtenerVertices()) {
            subGrafo.put(v, new ArrayList<>());
        }

        Vertice paso = destino;
        while (previos.get(paso) != null) {
            Vertice ant = previos.get(paso);
            double peso = distancias.get(paso) - distancias.get(ant);
            subGrafo.get(ant).add(new Arista(ant, paso, peso));
            subGrafo.get(paso).add(new Arista(paso, ant, peso));
            paso = ant;
        }

        return subGrafo;
    }

    /**
     * Nueva versión que devuelve solo la lista de aristas del camino mínimo.
     */
    public static List<Arista> caminoMasCortoListaAristas(
            GrafoTDA grafo, Vertice origen, Vertice destino) {

        Map<Vertice, Double> distancias = new HashMap<>();
        Map<Vertice, Vertice> previos = new HashMap<>();
        PriorityQueue<DistanciaNodo> cola
                = new PriorityQueue<>(Comparator.comparingDouble(DistanciaNodo::getDistancia));

        for (Vertice v : grafo.obtenerVertices()) {
            distancias.put(v, Double.POSITIVE_INFINITY);
            previos.put(v, null);
        }
        distancias.put(origen, 0.0);
        cola.add(new DistanciaNodo(origen, 0.0));

        while (!cola.isEmpty()) {
            DistanciaNodo dn = cola.poll();
            Vertice u = dn.getNodo();
            for (Arista a : grafo.obtenerAdyacentes(u)) {
                Vertice v = a.getDestino();
                double alt = distancias.get(u) + a.getDistancia();
                if (alt < distancias.get(v)) {
                    distancias.put(v, alt);
                    previos.put(v, u);
                    cola.add(new DistanciaNodo(v, alt));
                }
            }
        }

        List<Arista> camino = new ArrayList<>();
        Vertice paso = destino;
        while (previos.get(paso) != null) {
            Vertice ant = previos.get(paso);
            double peso = distancias.get(paso) - distancias.get(ant);
            camino.add(0, new Arista(ant, paso, peso));
            paso = ant;
        }
        return camino;
    }

    private static class DistanciaNodo {

        private final Vertice nodo;
        private final double distancia;

        public DistanciaNodo(Vertice nodo, double distancia) {
            this.nodo = nodo;
            this.distancia = distancia;
        }

        public Vertice getNodo() {
            return nodo;
        }

        public double getDistancia() {
            return distancia;
        }
    }
}
