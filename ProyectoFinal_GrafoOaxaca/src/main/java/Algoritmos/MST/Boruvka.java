package Algoritmos.MST;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class Boruvka extends Thread {
    private volatile boolean ejecutando = true;
    private GrafoTDA grafo;
    private GrafoTDA mst = new GrafoTDA();

    public Boruvka(GrafoTDA grafo) {
        this.grafo = grafo;
    }

    @Override
    public void run() {
        try {
            Set<Vertice> vertices = new HashSet<>(grafo.obtenerVertices());

            // Clonar los vértices para el MST
            Map<String, Vertice> verticesMST = new HashMap<>();
            for (Vertice v : vertices) {
                Vertice clon = new Vertice(v.getNombre());
                mst.agregarVertice(clon);
                verticesMST.put(clon.getNombre(), clon);
            }

            UnionFind uf = new UnionFind(vertices);
            int componentes = vertices.size();
            int iteracion = 0;

            while (componentes > 1 && ejecutando) {
                iteracion++;

                Map<String, Arista> mejorAristaPorComponente = new HashMap<>();
                Set<String> aristasConsideradas = new HashSet<>();

                // Paso 1: Buscar la mejor arista de cada componente
                for (Vertice v : vertices) {
                    String compV = uf.find(v).getNombre();

                    for (Arista arista : grafo.obtenerAdyacentes(v)) {
                        Vertice w = arista.getDestino();
                        String compW = uf.find(w).getNombre();

                        if (!compV.equals(compW)) {
                            String clave = generarClave(v, w);
                            if (!aristasConsideradas.contains(clave)) {
                                aristasConsideradas.add(clave);

                                Arista actual = mejorAristaPorComponente.get(compV);
                                if (actual == null || 
    arista.getDistancia() < actual.getDistancia() || 
    (arista.getDistancia() == actual.getDistancia() &&
     arista.getDestino().getNombre().compareTo(actual.getDestino().getNombre()) < 0)) {
    mejorAristaPorComponente.put(compV, arista);
}

                            }
                        }
                    }
                }

                int uniones = 0;

                // Paso 2: Agregar las mejores aristas al MST
                for (Arista arista : mejorAristaPorComponente.values()) {
                    Vertice u = arista.getOrigen();
                    Vertice w = arista.getDestino();

                    if (!uf.connected(u, w)) {
                        uf.union(u, w);
                        mst.agregarArista(
                            verticesMST.get(u.getNombre()),
                            verticesMST.get(w.getNombre()),
                            arista.getDistancia()
                        );
                        componentes--;
                        uniones++;
                    }
                }

                // Solo visualizar después de primera iteración y al final
                if (iteracion == 1 || componentes == 1) {
                    Thread.sleep(1000);
                }

                if (uniones == 0) break;
            }

        } catch (InterruptedException e) {
            System.out.println("Algoritmo Boruvka interrumpido");
        }
    }

    public GrafoTDA getMST() {
        return mst;
    }

    public void detener() {
        ejecutando = false;
    }

    private String generarClave(Vertice a, Vertice b) {
        String n1 = a.getNombre();
        String n2 = b.getNombre();
        return (n1.compareTo(n2) < 0) ? n1 + "-" + n2 : n2 + "-" + n1;
    }
}
