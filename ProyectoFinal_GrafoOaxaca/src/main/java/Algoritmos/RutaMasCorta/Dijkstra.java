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

//            System.out.println("Desencolando vértice: "
//                    + u.getNombre() + " con distancia provisional " + du);
            for (Arista a : grafo.obtenerAdyacentes(u)) {
                Vertice v = a.getDestino();
                double alt = du + a.getDistancia();
                if (alt < distancias.get(v)) {

//                    System.out.println(" Relax: mejora distancia de "
//                            + v.getNombre() + " de " + distancias.get(v)
//                            + " a " + alt);
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

//        System.out.println("Ruta final de " + origen.getNombre()
//                + " a " + destino.getNombre() + ": " + caminoMasTexto(previos, destino));
        return subGrafo;
    }

    public static ResultadoPrevia ejecutarPrevia(GrafoTDA grafo, Vertice origen) {
        Map<Vertice, Double> dist = new HashMap<>();
        Map<Vertice, Vertice> prev = new HashMap<>();
        PriorityQueue<DistanciaNodo> pq
                = new PriorityQueue<>(Comparator.comparingDouble(DistanciaNodo::getDistancia));

        for (Vertice v : grafo.obtenerVertices()) {
            dist.put(v, Double.POSITIVE_INFINITY);
            prev.put(v, null);
        }
        dist.put(origen, 0.0);
        pq.add(new DistanciaNodo(origen, 0.0));

        while (!pq.isEmpty()) {
            DistanciaNodo dn = pq.poll();
            Vertice u = dn.getNodo();
            double du = dn.getDistancia();
            for (Arista a : grafo.obtenerAdyacentes(u)) {
                Vertice v = a.getDestino();
                double alt = du + a.getDistancia();
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    pq.add(new DistanciaNodo(v, alt));
                }
            }
        }

        return new ResultadoPrevia(dist, prev);
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
                double alt = distancias.get(u) + a.getDistancia();
                if (alt < distancias.get(v)) {

//                    System.out.println(" Relax: mejora distancia de "
//                            + v.getNombre() + " de " + distancias.get(v)
//                            + " a " + alt);
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

        Map<Vertice, Double> dist = new HashMap<>();
        Map<Vertice, Vertice> prev = new HashMap<>();
        PriorityQueue<DistanciaNodo> pq
                = new PriorityQueue<>(Comparator.comparingDouble(DistanciaNodo::getDistancia));

        for (Vertice v : grafo.obtenerVertices()) {
            dist.put(v, Double.POSITIVE_INFINITY);
            prev.put(v, null);
        }
        dist.put(origen, 0.0);
        pq.add(new DistanciaNodo(origen, 0.0));

        while (!pq.isEmpty()) {
            DistanciaNodo dn = pq.poll();
            Vertice u = dn.getNodo();
            double du = dn.getDistancia();

            for (Arista a : grafo.obtenerAdyacentes(u)) {
                Vertice v = a.getDestino();
                double alt = du + a.getDistancia();
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    pq.add(new DistanciaNodo(v, alt));
                }
            }
        }

        GrafoTDA subGrafo = new GrafoTDA();
        for (Vertice v : grafo.obtenerVertices()) {
            subGrafo.agregarVertice(v);
        }
        for (Vertice v : grafo.obtenerVertices()) {
            Vertice padre = prev.get(v);
            if (padre != null) {
                double peso = dist.get(v) - dist.get(padre);
                subGrafo.agregarArista(padre, v, peso);
            }
        }

        for (Vertice v : grafo.obtenerVertices()) {
            if (v != origen) {
//                System.out.println("Ruta final de " + origen.getNombre()
//                        + " a " + v.getNombre() + ": " + caminoMasTexto(prev, v));
            }
        }

        return subGrafo;
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
    public static String caminoMasTexto(Map<Vertice, Vertice> previos, Vertice destino) {
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

        return previos;
    }


    public static class ResultadoPrevia {

        public final Map<Vertice, Double> distancias;
        public final Map<Vertice, Vertice> previos;

        public ResultadoPrevia(Map<Vertice, Double> dist, Map<Vertice, Vertice> prev) {
            this.distancias = dist;
            this.previos = prev;
        }
    }

}
