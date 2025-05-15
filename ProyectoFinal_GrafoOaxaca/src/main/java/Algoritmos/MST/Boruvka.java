package Algoritmos.MST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

                Map<Vertice, Arista> mejorAristaPorComponente = new HashMap<>();
                Set<String> aristasConsideradas = new HashSet<>();

                for (Vertice v : vertices) {
                    Vertice compV = uf.find(v);
                    for (Arista arista : grafo.obtenerAdyacentes(v)) {
                        Vertice w = arista.getDestino();
                        Vertice compW = uf.find(w);

                        if (!compV.equals(compW)) {
                            String clave = generarClave(v, w);
                            if (!aristasConsideradas.contains(clave)) {
                                aristasConsideradas.add(clave);

                                Arista actual = mejorAristaPorComponente.get(compV);
                                if (actual == null || arista.getDistancia() < actual.getDistancia()) {
                                    mejorAristaPorComponente.put(compV, arista);
                                }
                            }
                        }
                    }
                }

                List<Arista> seleccionadas = new ArrayList<>(mejorAristaPorComponente.values());

                int unionesEstaIteracion = 0;

                for (Arista arista : seleccionadas) {
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
                        unionesEstaIteracion++;
                    }
                }

                // 🔴 Solo se duerme y pinta dos veces: después de la 1.ª y después de la última unión
                if (iteracion == 1 || componentes == 1) {
                    Thread.sleep(1000);
                }

                if (unionesEstaIteracion == 0) break;
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
