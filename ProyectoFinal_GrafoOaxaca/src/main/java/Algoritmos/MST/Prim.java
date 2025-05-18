package Algoritmos.MST;

import java.util.ArrayList;
import java.util.List;

import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class Prim extends Thread {                                                  //T(n) = 100n"2 + 60n + 19              O(n"2)

    private volatile boolean ejecutando = true;
    private GrafoTDA mst = new GrafoTDA();
    private GrafoTDA grafo;
    private Vertice fuente;

    public Prim(GrafoTDA grafo, Vertice fuente) {                                                                                    //2
        this.grafo = grafo;                //1
        this.fuente = fuente;                //1
    }

    @Override
    public void run() {                                                                                                        // 50n"2 + 27n + 8
        ejecutando = true;                //1
        algoritmoPrim();                // 50n"2 + 27n + 6         
        ejecutando = false;                //1
    }

    private void algoritmoPrim() {        //                                                                                      50n"2 + 27n + 6
    try {
        for (Vertice u : grafo.obtenerVertices()) {                //2n + 1
            u.setLlave(Double.MAX_VALUE);                //2n
            u.setAnterior(null);                //n
            if (u.equals(fuente)) {                //n
                u.setLlave(0);                //n
            }
        }

        List<Vertice> vertices = new ArrayList<>(grafo.obtenerVertices());                  //3

        while (!vertices.isEmpty()) {                //n + 1
            Vertice u = obtenerMinimo(vertices);                //6n"2 + 5n
            vertices.remove(u);                //n

            mst.agregarVertice(u);                //n

            if (u.getAnterior() != null) {                //2n
                mst.agregarArista(u.getAnterior(), u, u.getLlave());                //4n
            }

            System.out.println("Agregando vertice: " + u.getNombre());                //2n

            for (Arista arista : grafo.obtenerAdyacentes(u)) {                //3n + 1
                Vertice v = arista.getDestino();                                    //2n*n
                if (vertices.contains(v) && arista.getDistancia() < v.getLlave()) {                //3n*n
                    v.setAnterior(u);                                               //n*n
                    v.setLlave(arista.getDistancia());                              //2n*n
                    System.out.println("Actualizando vertice: " + v.getNombre() + " con llave: " + v.getLlave()                //6n*n
                            + " y anterior: " + u.getNombre());                         //3n*n
                }
            }

            Thread.sleep(1000);                //n
        }
    } catch (InterruptedException e) {
        ejecutando = false;                //1
        System.out.println("El hilo fue interrumpido");                //1
    }
}


    private Vertice obtenerMinimo(List<Vertice> vertices) {                                                                                                                // 6n + 3
        Vertice minimo = vertices.get(0);                //2
        for (Vertice v : vertices) {                //2n +1
            if (v.getLlave() < minimo.getLlave()) {                //3n
                minimo = v;                 //n
            }
        }
        return minimo;
    }

    public GrafoTDA getMST() {
        return mst;                //1
    }

    public boolean isEjecutando() {
        return ejecutando;                //1
    }
}
