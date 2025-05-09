package Implementaciones;
import Excepciones.GraphException;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author EQUIPO:
 * Sebastian Borquez Huerta 252115
 * Alberto Jimenez Garcia 252595
 * Ariel Eduardo Borbon Izaguirre 252116
 * @param <T>
 * @param <W>
 */
public class NoDiGraph<T, W extends Comparable<W>> extends Graph<T, W>  {

    public NoDiGraph(int maxVertices) {
        super(maxVertices);
    }
    
    
    

    public LinkedList<T> neighbors(T vertice) throws GraphException {
        LinkedList<T> vecinos = new LinkedList<>();
        
        for (int i = 0; i < getNumberVertices(); i++) {
            T v = getVertex(i);
            if (adjacent(vertice, v) && !v.equals(vertice)) {
                vecinos.add(v);
            }
            
        }
        
        return vecinos;
    }
    
    
    
    
    
    
    
    

    public LinkedList<T> shortestPath(T inicio, T fin) throws GraphException {
        Map<T, W> distancias = new HashMap<>();
        Map<T, T> anteriores = new HashMap<>();
        
        PriorityQueue<Nodo<T, W>> prioridadcola = new PriorityQueue<>();
        
        for (int i = 0; i < getNumberVertices(); i++) {
            
            T vertice = getVertex(i);
            distancias.put(vertice, null);
            anteriores.put(vertice, null);
            
        }
        
        
        
        
        distancias.put(inicio, (W) Integer.valueOf(0)); 
        prioridadcola.add(new Nodo<>(inicio, (W) Integer.valueOf(0)));
        
        
        
        while (!prioridadcola.isEmpty()) {
            
            Nodo<T, W> nodoActual = prioridadcola.poll();
            T verticeActual = nodoActual.vertice;
            
            if (verticeActual.equals(fin)) {
                break; 
            }
            
            
            for (T vecino : neighbors(verticeActual)) {
                W peso = getEdgeWeight(verticeActual, vecino);
                W nuevaDist = (W) Integer.valueOf((Integer) distancias.get(verticeActual) + (Integer) peso);
                
                
                 if (distancias.get(vecino) == null || nuevaDist.compareTo(distancias.get(vecino)) < 0) {
                        distancias.put(vecino, nuevaDist);
                        anteriores.put(vecino, verticeActual);
                        prioridadcola.add(new Nodo<>(vecino, nuevaDist));
                    }
            }
        }
        
        
        LinkedList<T> ruta = new LinkedList<>(); 
        W pesoTotal = (W) Integer.valueOf(0); 
        
        for (T at = fin; at != null; at = anteriores.get(at)) {
            ruta.addFirst(at);
        }
        
        if (ruta.size() > 1) {
            T verticeAnterior = null;
            for (T vertex : ruta) {
                if (verticeAnterior != null) {
                    pesoTotal = (W) Integer.valueOf((Integer) pesoTotal + (Integer) getEdgeWeight(verticeAnterior, vertex));
                }
                verticeAnterior = vertex;
            }
        }
        
        return ruta.size() > 1 ? ruta : new LinkedList<>();
    }

    
    
    
   
/*
    
    
    */
     
    
    
    
    
    public W shortestWEIGHT(T inicio, T fin) throws GraphException {
            Map<T, W> distancias = new HashMap<>();
            Map<T, T> anteriores = new HashMap<>();
            
            PriorityQueue<Nodo<T, W>> prioridadcola = new PriorityQueue<>();
            
            for (int i = 0; i < getNumberVertices(); i++) {
                
                T vertex = getVertex(i);
                distancias.put(vertex, null);
                anteriores.put(vertex, null);
                
            }
            
            distancias.put(inicio, (W) Integer.valueOf(0)); 
        prioridadcola.add(new Nodo<>(inicio, (W) Integer.valueOf(0)));
            
            while (!prioridadcola.isEmpty()) {
                
                Nodo<T, W> currentNode = prioridadcola.poll();
                T verticeActual = currentNode.vertice;
                
                if (verticeActual.equals(fin)) {
                    break; 
                }
                
                
                for (T vecino : neighbors(verticeActual)) {
                    W peso = getEdgeWeight(verticeActual, vecino);
                    W nuevaDist = (W) Integer.valueOf((Integer) distancias.get(verticeActual) + (Integer) peso);
                    
                    
                     if (distancias.get(vecino) == null || nuevaDist.compareTo(distancias.get(vecino)) < 0) {
                            distancias.put(vecino, nuevaDist);
                            anteriores.put(vecino, verticeActual);
                            prioridadcola.add(new Nodo<>(vecino, nuevaDist));
                        }
                }
            }
            
            
            LinkedList<T> ruta = new LinkedList<>(); 
            W pesoTotal = (W) Integer.valueOf(0); 
            
            for (T at = fin; at != null; at = anteriores.get(at)) {
                ruta.addFirst(at);
            }
            
            
            
            
            if (ruta.size() > 1) {
                T verticeAnterior = null;
                
                for (T vertex : ruta) {
                    
                    if (verticeAnterior != null) {
                        pesoTotal = (W) Integer.valueOf((Integer) pesoTotal + (Integer) getEdgeWeight(verticeAnterior, vertex));
                    }
                    
                    verticeAnterior = vertex;
                }
            }
            
            return pesoTotal;
        }
    

 
 

    private static class Nodo<T, W> implements Comparable<Nodo<T, W>> {
        T vertice;
        W peso;

        Nodo(T vertice, W peso) {
            this.vertice = vertice;
            this.peso = peso;
        }

        @Override
        public int compareTo(Nodo<T, W> otro) {
            return ((Comparable<W>) this.peso).compareTo(otro.peso);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
}