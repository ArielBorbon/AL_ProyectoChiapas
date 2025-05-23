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
public final class BellmanFord {

    private BellmanFord() {
    }

    /**
     * Resultado completo de ejecutar Bellman–Ford.
     */
    public static class Resultado {

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
            this.distancias = distancias;
            this.previos = previos;
            this.iteraciones = iteraciones;
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
    public static Resultado ejecutar(GrafoTDA grafo, Vertice origen) throws InterruptedException {
        Map<Vertice, Double> dist = new HashMap<>();
        Map<Vertice, Vertice> prev = new HashMap<>();
        for (Vertice v : grafo.obtenerVertices()) {
            dist.put(v, Double.POSITIVE_INFINITY);
            prev.put(v, null);
        }
        dist.put(origen, 0.0);

        List<Arista> aristas = new ArrayList<>();
        for (Vertice u : grafo.obtenerVertices()) {
            aristas.addAll(grafo.obtenerAdyacentes(u));
        }

        List<Map<Vertice, Double>> iteraciones = new ArrayList<>();
        iteraciones.add(new HashMap<>(dist));

        int V = grafo.obtenerVertices().size();
        for (int i = 1; i < V; i++) {
            boolean cambiado = false;
//            System.out.println("=== Iteración " + i + " de relaxación ===");

            for (Arista a : aristas) {
                Vertice u = a.getOrigen();
                Vertice v = a.getDestino();
                double peso = a.getDistancia();
                double alt = dist.get(u) + peso;

//                System.out.println(" Verificando arista "
//                        + u.getNombre() + "→" + v.getNombre()
//                        + " (peso " + peso + ")");

                if (alt < dist.get(v)) {
//                    System.out.println("  Relax: mejora distancia de "
//                            + v.getNombre() + " de " + dist.get(v)
//                            + " a " + alt);

                    dist.put(v, alt);
                    prev.put(v, u);
                    cambiado = true;
                }
            }
            iteraciones.add(new HashMap<>(dist));
            if (!cambiado) {
//                System.out.println("No hubo cambios en la iteración " + i
//                        + ", deteniendo anticipadamente.");
                break;
            }
        }

        for (Arista a : aristas) {
            Vertice u = a.getOrigen();
            Vertice v = a.getDestino();
            if (dist.get(u) + a.getDistancia() < dist.get(v)) {
                throw new IllegalArgumentException(
                        "Se ha detectado un ciclo de peso negativo en el grafo."
                );
            }
        }

        return new Resultado(dist, prev, iteraciones);
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
    public static List<Arista> reconstruirCamino(Resultado resultado, Vertice destino) {
        List<Arista> camino = new ArrayList<>();
        Map<Vertice, Vertice> prev = resultado.previos;
        Map<Vertice, Double> dist = resultado.distancias;

        Vertice paso = destino;
        while (prev.get(paso) != null) {
            Vertice ant = prev.get(paso);
            double peso = dist.get(paso) - dist.get(ant);
            camino.add(0, new Arista(ant, paso, peso));
            paso = ant;
        }
        return camino;
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

    public static GrafoTDA caminoMasCortoTodas(GrafoTDA grafo, Vertice origen)
            throws InterruptedException {
        Resultado res = ejecutar(grafo, origen);

        GrafoTDA subGrafo = new GrafoTDA();
        for (Vertice v : grafo.obtenerVertices()) {
            subGrafo.agregarVertice(v);
        }
        for (Vertice v : grafo.obtenerVertices()) {
            Vertice padre = res.previos.get(v);
            if (padre != null) {
                double peso = res.distancias.get(v) - res.distancias.get(padre);
                subGrafo.agregarArista(padre, v, peso);
            }
        }
        return subGrafo;
    }

}
