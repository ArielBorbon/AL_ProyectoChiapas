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
     *
     * @param grafo
     * @param origen
     * @param destino
     * @return
     * @throws java.lang.InterruptedException
     */
    public static Map<Vertice, List<Arista>> caminoMasCorto(
            GrafoTDA grafo, Vertice origen, Vertice destino) throws InterruptedException {

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

            System.out.println("Desencolando vértice: "
                    + u.getNombre() + " con distancia provisional " + du);
            Thread.sleep(500);

            for (Arista a : grafo.obtenerAdyacentes(u)) {
                Vertice v = a.getDestino();
                double alt = du + a.getDistancia();
                if (alt < distancias.get(v)) {

                    System.out.println(" Relax: mejora distancia de "
                            + v.getNombre() + " de " + distancias.get(v)
                            + " a " + alt);
                    Thread.sleep(300);

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

        System.out.println("Ruta final de " + origen.getNombre()
                + " a " + destino.getNombre() + ": " + caminoMasTexto(previos, destino));
        Thread.sleep(500);
        return subGrafo;
    }

    /**
     * Nueva versión que devuelve solo la lista de aristas del camino mínimo.
     * @param grafo
     * @param origen
     * @param destino
     * @return 
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

    /**
     * Devuelve una representación textual del camino más corto desde el origen
     * hasta el destino, usando el mapa de previos generado por Dijkstra o
     * Bellman–Ford.
     */
    private static String caminoMasTexto(Map<Vertice, Vertice> previos, Vertice destino) {
        List<String> nombres = new ArrayList<>();
        Vertice paso = destino;

        while (paso != null) {
            nombres.add(0, paso.getNombre()); 
            paso = previos.get(paso);
        }

        if (nombres.size() == 1) {
            return "No hay camino hasta " + destino.getNombre();
        }

        return String.join(" -> ", nombres);
    }

}
