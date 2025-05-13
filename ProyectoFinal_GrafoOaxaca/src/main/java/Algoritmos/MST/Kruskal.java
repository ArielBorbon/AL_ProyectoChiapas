package Algoritmos.MST;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Implementacion.Arista;
import Implementacion.GrafoTDA;

public class Kruskal {

    private static List<List<Integer>> conjuntos;

    /**
     * Calcula el Árbol de Expansión Mínima (MST) del grafo utilizando el
     * algoritmo de Kruskal.
     *
     * @return Mapa que representa el MST como listas de adyacencia.
     */
    public static Map<Integer, List<Arista>> arbolEsparcimientoMinimo(GrafoTDA grafo) {
        Map<Integer, List<Arista>> mst = new HashMap<>();
        List<Arista> aristas = new ArrayList<>();

        for (Integer vertice : grafo.obtenerVertices()) {
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
                        .add(new Arista(arista.getDestino(), arista.getOrigen(), arista.getDestino()));
                union(arista.getOrigen(), arista.getDestino());
            }
        }
        return mst;
    }

    private static void makeSet(Integer vertice) {
        List<Integer> nuevoConjunto = new ArrayList<>();
        nuevoConjunto.add(vertice);
        conjuntos.add(nuevoConjunto);
    }

    private static List<Integer> findSet(Integer vertice) {
        for (List<Integer> conjunto : conjuntos) {
            if (conjunto.contains(vertice)) {
                return conjunto;
            }
        }
        return null;
    }

    private static void union(Integer u, Integer v) {
        List<Integer> conjuntoU = findSet(u);
        List<Integer> conjuntoV = findSet(v);

        if (conjuntoU != null && conjuntoV != null && conjuntoU != conjuntoV) {
            conjuntoU.addAll(conjuntoV);
            conjuntos.remove(conjuntoV);
        }
    }

}
