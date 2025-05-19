package Algoritmos.MST;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Implementacion.Vertice;

public class UnionFind {
    private final Map<Vertice, Vertice> padre = new HashMap<>();

    public UnionFind(Set<Vertice> vertices) {
        for (Vertice v : vertices) {
            padre.put(v, v);
        }
    }

    public Vertice find(Vertice v) {
        Vertice root = v;
        while (!root.equals(padre.get(root))) {
            root = padre.get(root);
        }
        Vertice actual = v;
        while (!actual.equals(root)) {
            Vertice siguiente = padre.get(actual);
            padre.put(actual, root);
            actual = siguiente;
        }
        return root;
    }

    public void union(Vertice a, Vertice b) {
        Vertice raizA = find(a);
        Vertice raizB = find(b);
        if (!raizA.equals(raizB)) {
            padre.put(raizA, raizB);
        }
    }

    public boolean connected(Vertice a, Vertice b) {
        return find(a).equals(find(b));
    }
}
