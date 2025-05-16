package Algoritmos.MST;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class Prim extends Thread {

    private volatile boolean ejecutando = true;
    private GrafoTDA mst = new GrafoTDA();
    private GrafoTDA grafo;
    private Vertice fuente;

    public Prim(GrafoTDA grafo, Vertice fuente) {
        this.grafo = grafo;
        this.fuente = fuente;
    }

    @Override
    public void run() {
        ejecutando = true;
        algoritmoPrim();
        ejecutando = false;
    }

    private void algoritmoPrim() {
        try {
            for (Vertice u : grafo.obtenerVertices()) {
                u.setLlave(Double.MAX_VALUE);
                u.setAnterior(null);
                if(u.equals(fuente)) {
                    u.setLlave(0);
                }
            }
            List<Vertice> vertices = new ArrayList<>(grafo.obtenerVertices());
            while (!vertices.isEmpty()) {
                Vertice u = obtenerMinimo(vertices);
                vertices.remove(u);
                mst.agregarVertice(u);
                System.out.println("Agregando vertice: " + u.getNombre());
                for (Arista arista : grafo.obtenerAdyacentes(u)) {
                    Vertice v = arista.getDestino();
                    if (vertices.contains(v) && arista.getDistancia() < v.getLlave()) {
                        v.setAnterior(u);
                        v.setLlave(arista.getDistancia());
                        System.out.println("Actualizando vertice: " + v.getNombre() + " con llave: " + v.getLlave()
                                + " y anterior: " + u.getNombre());
                    }
                }

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            ejecutando = false;
            System.out.println("El hilo fue interrumpido");
        }
    }

    private Vertice obtenerMinimo(List<Vertice> vertices) {
        Vertice minimo = vertices.get(0);
        for (Vertice v : vertices) {
            if (v.getLlave() < minimo.getLlave()) {
                minimo = v;
            }
        }
        return minimo;
    }

    public GrafoTDA getMST() {
        return mst;
    }

    public boolean isEjecutando() {
        return ejecutando;
    }
}
