package Algoritmos.MST;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class Kruskal {

    private static List<List<Vertice>> conjuntos;

    public static Map<Vertice, List<Arista>> mst = new HashMap<>();


    /**
     * Calcula el Árbol de Expansión Mínima (MST) del grafo utilizando el
     * algoritmo de Kruskal.
     *
     * @return Mapa que representa el MST como listas de adyacencia.
     */
    public static Map<Vertice, List<Arista>> arbolEsparcimientoMinimo(GrafoTDA grafo) {
        List<Arista> aristas = new ArrayList<>();

        for (Vertice vertice : grafo.obtenerVertices()) {
            makeSet(vertice);
            aristas.addAll(grafo.obtenerAdyacentes(vertice));
        }

        // Ordena las aristas por distancia (peso)
        aristas = aristas.stream()
                .sorted(Comparator.comparingDouble(Arista::getDistancia))
                .collect(Collectors.toList());

        for (Arista arista : aristas) {
            // Agrega la arista al MST si no forma un ciclo
            if (findSet(arista.getOrigen()) != findSet(arista.getDestino())) {
                mst.putIfAbsent(arista.getOrigen(), new ArrayList<>());
                mst.putIfAbsent(arista.getDestino(), new ArrayList<>());

                mst.get(arista.getOrigen()).add(arista);
                mst.get(arista.getDestino())
                        .add(new Arista(arista.getDestino(), arista.getOrigen(), arista.getDistancia()));
                union(arista.getOrigen(),
                 arista.getDestino());
            }
        }
        return mst;
    }

    private static void makeSet(Vertice vertice) {
        List<Vertice> nuevoConjunto = new ArrayList<>();
        nuevoConjunto.add(vertice);
        conjuntos.add(nuevoConjunto);
    }

    private static List<Vertice> findSet(Vertice vertice) {
        for (List<Vertice> conjunto : conjuntos) {
            if (conjunto.contains(vertice)) {
                return conjunto;
            }
        }
        return null;
    }

    private static void union(Vertice u, Vertice v) {
        List<Vertice> conjuntoU = findSet(u);
        List<Vertice> conjuntoV = findSet(v);

        if (conjuntoU != null && conjuntoV != null && conjuntoU != conjuntoV) {
            conjuntoU.addAll(conjuntoV);
            conjuntos.remove(conjuntoV);
        }
    }

}
