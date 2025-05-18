package Algoritmos.MST;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Implementacion.Vertice;

public class UnionFind {                                                                    // T(n) = 12n + 15          O(n)
    private final Map<Vertice, Vertice> padre = new HashMap<>();                

    public UnionFind(Set<Vertice> vertices) {                   //3n +1
        for (Vertice v : vertices) {            //2n + 1
            padre.put(v, v);            //n
        }
    }

    public Vertice find(Vertice v) {
        Vertice root = v;           //1
        while (!root.equals(padre.get(root))) {     //2n + 1
            root = padre.get(root);     //2n
        }
        Vertice actual = v;                 //1
        while (!actual.equals(root)) {         //n + 1
            Vertice siguiente = padre.get(actual);     // 2n
            padre.put(actual, root);            //n
            actual = siguiente;         //n
        }
        return root;            //1
    }

    public void union(Vertice a, Vertice b) {       
        Vertice raizA = find(a);            //1
        Vertice raizB = find(b);            //1
        if (!raizA.equals(raizB)) {         //2
            padre.put(raizA, raizB);        //1
        }
    }

    public boolean connected(Vertice a, Vertice b) {
        return find(a).equals(find(b));     //4
    }
}
