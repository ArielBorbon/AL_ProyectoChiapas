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

    private BoruvkaListener listener;

    public void setListener(BoruvkaListener listener) {
        this.listener = listener;
    }

    public Boruvka(GrafoTDA grafo) {
        this.grafo = grafo;
    }

    @Override
    public void run() {
        try {
            Set<Vertice> verticesOriginales = new HashSet<>(grafo.obtenerVertices());

            // Clonar los vértices para el MST
            Map<String, Vertice> verticesMST = new HashMap<>();
            for (Vertice v : verticesOriginales) {
                Vertice clon = new Vertice(v.getNombre());
                mst.agregarVertice(clon);
                verticesMST.put(clon.getNombre(), clon);
            }

            UnionFind uf = new UnionFind(verticesOriginales);
            int componentes = verticesOriginales.size();

            while (componentes > 1 && ejecutando) {
                // Obtener representantes únicos actuales (componentes)
                Set<Vertice> componentesActuales = new HashSet<>();
                for (Vertice v : verticesOriginales) {
                    componentesActuales.add(uf.find(v));
                }

                Map<Vertice, Arista> mejorAristaPorComponente = new HashMap<>();
                Set<String> aristasConsideradas = new HashSet<>();

                // Para cada componente, buscar la arista mínima que conecta con otro componente
                for (Vertice comp : componentesActuales) {
                    double minDistancia = Double.MAX_VALUE;
                    Arista aristaMin = null;

                    for (Vertice v : verticesOriginales) {
                        if (uf.find(v).equals(comp)) {
                            for (Arista arista : grafo.obtenerAdyacentes(v)) {
                                Vertice w = arista.getDestino();
                                if (!uf.find(w).equals(comp)) {
                                    String clave = generarClave(v, w);
                                    if (!aristasConsideradas.contains(clave)) {
                                        if (arista.getDistancia() < minDistancia ||
                                            (arista.getDistancia() == minDistancia &&
                                             arista.getDestino().getNombre().compareTo(
                                                 aristaMin != null ? aristaMin.getDestino().getNombre() : ""
                                             ) < 0)) {
                                            minDistancia = arista.getDistancia();
                                            aristaMin = arista;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (aristaMin != null) {
                        mejorAristaPorComponente.put(comp, aristaMin);
                        aristasConsideradas.add(generarClave(aristaMin.getOrigen(), aristaMin.getDestino()));
                    }
                }

                int uniones = 0;

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

                // Notificar al listener y pausar después de cada iteración
                if (listener != null) {
                    listener.onActualizaMST(mst);
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
