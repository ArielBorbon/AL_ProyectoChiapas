package Algoritmos.MST;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class Kruskal extends Thread {                                   //                  T(n) = 8n"2 + 38n + 37

    private List<List<Vertice>> conjuntos;
    private volatile boolean ejecutando = true;
    private volatile GrafoTDA mst = new GrafoTDA();
    private GrafoTDA grafo;

    public Kruskal(GrafoTDA grafo) {                                    //                                                                                              2
        this.grafo = grafo;                     //1
        conjuntos = new ArrayList<>();          //1
    }

       /**
     * Calcula el Árbol de Expansión Mínima (MST) del grafo utilizando el
     * algoritmo de Kruskal.
     *
     */
    @Override
    public void run() {       //                                                                                                                                 8n"2 + 34n + 11
        try {
            List<Arista> aristas = new LinkedList<>();                  //1
            mst = new GrafoTDA();                //2

            for (Vertice vertice : grafo.obtenerVertices()) {                           //2n + 1
                makeSet(vertice);                                                        //4n
                aristas.addAll(grafo.obtenerAdyacentes(vertice));                       //3n
            }

            // Ordena las aristas por distancia (peso)
            aristas.sort(Comparator.comparingDouble(Arista::getDistancia));                //3

            for (Arista arista : aristas) {                                         //2n+1
                Vertice origen = arista.getOrigen();                                        //2n
                Vertice destino = arista.getDestino();                                  //2n
                // Agrega la arista al MST si no forma un ciclo
                if (findSet(origen) != findSet(destino)) {                          //(8n+12)*n
                    mst.agregarVertice(origen);                                             //n
                    mst.agregarVertice(destino);                                                //n

                    mst.agregarArista(origen, destino, arista.getDistancia());                          //2n
                    union(origen, destino);                                                     //9n
                    Thread.sleep(1000);                                                         //n
                    System.out.println("Agregando arista: " + origen.getNombre() + " - " + destino.getNombre());                //5n
                }
            }
        } catch (InterruptedException e) {
            ejecutando = false;                //1
            System.out.println("El hilo fue interrumpido");                //1
        }
        ejecutando = false;                //1
    }

    private void makeSet(Vertice vertice) {                             //                                                                                  4
        List<Vertice> nuevoConjunto = new ArrayList<>();                        //2
        nuevoConjunto.add(vertice);                                             //1
        conjuntos.add(nuevoConjunto);                                           //1
    }

    private List<Vertice> findSet(Vertice vertice) {   //                                                                                                 4n + 6
        for (List<Vertice> conjunto : conjuntos) {                //2n+1
            if (conjunto.contains(vertice)) {                //n
                return conjunto;                //n
            }
        }
        return null;                            //1
    }

    private void union(Vertice u, Vertice v) { //                                                                                                       9
        List<Vertice> conjuntoU = findSet(u);                //2
        List<Vertice> conjuntoV = findSet(v);                //2

        if (conjuntoU != null && conjuntoV != null && conjuntoU != conjuntoV) {                //3
            conjuntoU.addAll(conjuntoV);                //1
            conjuntos.remove(conjuntoV);                //1
        }
    }

    public GrafoTDA getMst() {
        return mst;                //1
    }

    public boolean isEjecutando() {
        return ejecutando;                      //1
    }

}
