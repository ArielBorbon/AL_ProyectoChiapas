package Algoritmos.MST;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class Kruskal extends Thread {

    private List<List<Vertice>> conjuntos;
    private volatile boolean ejecutando = true;
    private volatile GrafoTDA mst = new GrafoTDA();
    private GrafoTDA grafo;

    public Kruskal(GrafoTDA grafo) {
        this.grafo = grafo;
        conjuntos = new ArrayList<>();
    }

       /**
     * Calcula el Árbol de Expansión Mínima (MST) del grafo utilizando el
     * algoritmo de Kruskal.
     *
     */
    @Override
    public void run() {
        try {
            List<Arista> aristas = new LinkedList<>();
            mst = new GrafoTDA();

            for (Vertice vertice : grafo.obtenerVertices()) {
                makeSet(vertice);
                aristas.addAll(grafo.obtenerAdyacentes(vertice));
            }

            // Ordena las aristas por distancia (peso)
            aristas.sort(Comparator.comparingDouble(Arista::getDistancia));

            for (Arista arista : aristas) {
                Vertice origen = arista.getOrigen();
                Vertice destino = arista.getDestino();
                // Agrega la arista al MST si no forma un ciclo
                if (findSet(origen) != findSet(destino)) {
                    mst.agregarVertice(origen);
                    mst.agregarVertice(destino);

                    mst.agregarArista(origen, destino, arista.getDistancia());
                    union(origen, destino);
                    Thread.sleep(100);
                }
            }
        } catch (InterruptedException e) {
            ejecutando = false;
            System.out.println("El hilo fue interrumpido");
        }
        ejecutando = false;
    }

    private void makeSet(Vertice vertice) {
        List<Vertice> nuevoConjunto = new ArrayList<>();
        nuevoConjunto.add(vertice);
        conjuntos.add(nuevoConjunto);
    }

    private List<Vertice> findSet(Vertice vertice) {
        for (List<Vertice> conjunto : conjuntos) {
            if (conjunto.contains(vertice)) {
                return conjunto;
            }
        }
        return null;
    }

    private void union(Vertice u, Vertice v) {
        List<Vertice> conjuntoU = findSet(u);
        List<Vertice> conjuntoV = findSet(v);

        if (conjuntoU != null && conjuntoV != null && conjuntoU != conjuntoV) {
            conjuntoU.addAll(conjuntoV);
            conjuntos.remove(conjuntoV);
        }
    }

    public GrafoTDA getMst() {
        return mst;
    }

    public boolean isEjecutando() {
        return ejecutando;
    }

}
