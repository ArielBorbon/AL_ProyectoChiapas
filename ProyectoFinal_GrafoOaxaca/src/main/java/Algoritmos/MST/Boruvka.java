package Algoritmos.MST;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class Boruvka extends Thread {                                                       // T(n) = 38n"3 + 80n"2 + 22n + 52      O(n"3)
    private volatile boolean ejecutando = true;
    private GrafoTDA grafo;
    private GrafoTDA mst = new GrafoTDA();                                                                                                                                          //1

    private BoruvkaListener listener;

    public void setListener(BoruvkaListener listener) {
        this.listener = listener;                                                                                                                                                    //1
    }

    public Boruvka(GrafoTDA grafo) {
        this.grafo = grafo;                                                                                                                                                          //1
    }

    @Override
    public void run() {                                                                                                                                  //38n"3 + 80n"2 + 22n + 35
        try {
            Set<Vertice> verticesOriginales = new HashSet<>(grafo.obtenerVertices());           //2

            Map<String, Vertice> verticesMST = new HashMap<>();                              //2
            for (Vertice v : verticesOriginales) {                                               //2n+1                                                                
                Vertice clon = new Vertice(v.getNombre());                     //3n
                mst.agregarVertice(clon);                                       //n
                verticesMST.put(clon.getNombre(), clon);                           //2n
            }

            UnionFind uf = new UnionFind(verticesOriginales);                     //2
            int componentes = verticesOriginales.size();                                 //2

            while (componentes > 1 && ejecutando) {                                      //2n + 1
                Set<Vertice> componentesActuales = new HashSet<>();                     //2
                for (Vertice v : verticesOriginales) {                               //(2n +1) *n
                    componentesActuales.add(uf.find(v));                        //2n*n
                }

                Map<Vertice, Arista> mejorAristaPorComponente = new HashMap<>();                     //2n
                Set<String> aristasConsideradas = new HashSet<>();                                   //2n

                for (Vertice comp : componentesActuales) {                                       //(2n+1)*n
                    double minDistancia = Double.MAX_VALUE;                                  //2n*n
                    Arista aristaMin = null;                                                     //n*n

                    for (Vertice v : verticesOriginales) {                                           //(2n+1)*n*n
                        if (uf.find(v).equals(comp)) {                                       //2n*n*n
                            for (Arista arista : grafo.obtenerAdyacentes(v)) {                     //2n*n*n
                                Vertice w = arista.getDestino();                                                 //2n*n*n
                                if (!uf.find(w).equals(comp)) {                                  //2n*n*n
                                    String clave = generarClave(v, w);                               //13n*n*n
                                    if (!aristasConsideradas.contains(clave)) {                     //1n*n*n
                                        if (arista.getDistancia() < minDistancia ||                      //2n*n*n
                                            (arista.getDistancia() == minDistancia &&                        //2n*n*n
                                             arista.getDestino().getNombre().compareTo(                          //7n*n*n
                                                 aristaMin != null ? aristaMin.getDestino().getNombre() : ""
                                             ) < 0)) {
                                            minDistancia = arista.getDistancia();                     //2n*n*n
                                            aristaMin = arista;                          //n*n*n
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (aristaMin != null) {                                                    //n*n
                        mejorAristaPorComponente.put(comp, aristaMin);                                           //n*n
                        aristasConsideradas.add(generarClave(aristaMin.getOrigen(), aristaMin.getDestino()));                    //16n*n
                    }
                }

                int uniones = 0;                     //n

                for (Arista arista : mejorAristaPorComponente.values()) {                     //(2n+1) * n
                    Vertice u = arista.getOrigen();                               //2*n*n
                    Vertice w = arista.getDestino();                              //2*n*n

                    if (!uf.connected(u, w)) {                     //n*n
                        uf.union(u, w);                            //n*n
                        mst.agregarArista(                             //n*n
                            verticesMST.get(u.getNombre()),                     //n*n
                            verticesMST.get(w.getNombre()),                     //n*n
                            arista.getDistancia()                                    //n*n
                        );
                        componentes--;                                                       //n*n
                        uniones++;                                                          //n*n
                    }
                }

                if (listener != null) {                     //2n
                    listener.onActualizaMST(mst);                        //n
                    Thread.sleep(1000);                          //n
                }

                if (uniones == 0) break;                     //2n
            }
        } catch (InterruptedException e) {
            System.out.println("Algoritmo Boruvka interrumpido");                            //1
        }
    }

    public GrafoTDA getMST() {
        return mst;                                                                                                                                                         //1
    }

    public void detener() {
        ejecutando = false;                                                                                                                                                  //1
    }

    private String generarClave(Vertice a, Vertice b) {                                                                                                                     //12
        String n1 = a.getNombre();                     //2
        String n2 = b.getNombre();                     //2
        return (n1.compareTo(n2) < 0) ? n1 + "-" + n2 : n2 + "-" + n1;                     //8
    }
}
