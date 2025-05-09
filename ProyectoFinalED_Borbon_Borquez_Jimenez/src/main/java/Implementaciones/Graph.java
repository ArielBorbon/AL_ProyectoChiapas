package Implementaciones;

import Excepciones.GraphException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
/**
 *
 * @author EQUIPO:
 * Sebastian Borquez Huerta 252115
 * Alberto Jimenez Garcia 252595
 * Ariel Eduardo Borbon Izaguirre 252116
 * @param <T>
 * @param <W>
 */
public class Graph<T, W>{
    private LinkedList<T> vertices;
    private W[][] matrizAdyacencia;
    private int maxVertices;
    private int contadorBordes;
    

    public Graph(int maxVertices) {
        this.maxVertices = maxVertices;
        this.vertices = new LinkedList<>();
        this.matrizAdyacencia = (W[][]) new Object[maxVertices][maxVertices];
        this.contadorBordes = 0;
    }

    public void addVertex(T vertice) throws GraphException {
        
        if (getNumberVertices() >= maxVertices) {
            throw new GraphException("No hay espacio suficiente para un vertice nuevo.");
        }
        
        if (indexOf(vertice) != -1) {
            throw new GraphException("El vertice Ya existe.");
        }
        
        vertices.add(vertice);
    }
    
    public void removeVertex(T vertice) throws GraphException {
        int indice = indexOf(vertice);
        if (indice == -1) {
            throw new GraphException("Vertice Inexistente");
        }
        vertices.remove(indice);
        for (int i = 0; i < maxVertices; i++) {
            matrizAdyacencia[indice][i] = null;
            matrizAdyacencia[i][indice] = null;
        }
    }

    public T getVertex(int i) throws GraphException {
        if (i < 0 || i >= getNumberVertices()) {
            throw new GraphException("Indice Invalido");
        }
        return vertices.get(i);
    }

    public int indexOf(T vertice) {
        
        for (int i = 0; i < getNumberVertices(); i++) {
            
            if (vertices.get(i).equals(vertice)) {
                return i;
            }
            
        }
        
        return -1;
    }

    
    public int getNumberVertices() {
        return vertices.size();
    }
    

    
    public LinkedList<T> getVertices() {
        return vertices;
    }
    
    

    public W[][] getAdjacency() {
        return matrizAdyacencia;
    }
    
    

    public boolean empty() {
        return getNumberVertices() == 0;
    }
    

    public void addEdge(T verticeX, T verticeY, W peso) throws GraphException {
        int indiceX = indexOf(verticeX);
        int indiceY = indexOf(verticeY);
        
        if (indiceX == -1 || indiceY == -1) {
            throw new GraphException("1 o mas Vertices No Existen");
        }
        
        if (matrizAdyacencia[indiceX][indiceY] != null) {
            throw new GraphException("La conexion de la arista ya existe.");
        }
        
        matrizAdyacencia[indiceX][indiceY] = peso;
        contadorBordes++;
    }
    

    public void removeEdge(T verticeX, T verticeY) throws GraphException {
        int indiceX = indexOf(verticeX);
        int indiceY = indexOf(verticeY);
        
        if (indiceX == -1 || indiceY == -1 || matrizAdyacencia[indiceX][indiceY] == null) {
            throw new GraphException("Algun vertice o La Arista No Existe.");
        }
        
        matrizAdyacencia[indiceX][indiceY] = null;
        contadorBordes--;
    }

    
    
    public W getEdgeWeight(T verticeX, T verticeY) throws GraphException {
        int indiceX = indexOf(verticeX);
        int indiceY = indexOf(verticeY);
        
        if (indiceX == -1 || indiceY == -1 || matrizAdyacencia[indiceX][indiceY] == null) {
            throw new GraphException("Algun vertice o La Arista No Existe.");
        }
        
        return matrizAdyacencia[indiceX][indiceY];
    }
    

    
    
    public void setEdgeWeight(T verticeX, T verticeY, W peso) throws GraphException {
        int indiceX = indexOf(verticeX);
        int indiceY = indexOf(verticeY);
        
        if (indiceX == -1 || indiceY == -1 || matrizAdyacencia[indiceX][indiceY] == null) {
            throw new GraphException("Algun vertice o La Arista No Existe..");
        }
        
        matrizAdyacencia[indiceX][indiceY] = peso;
        
    }

    public boolean adjacent(T verticeX, T verticeY) throws GraphException {
        int indiceX = indexOf(verticeX);
        int indiceY = indexOf(verticeY);
        
        if (indiceX == -1 || indiceY == -1) {
            throw new GraphException("Algun o Ambos Vertices No Existen.");
        }
        
        
        return matrizAdyacencia[indiceX][indiceY] != null;
    }

    
    public void clear() {
        vertices.clear();
        matrizAdyacencia = (W[][]) new Object[maxVertices][maxVertices];
        contadorBordes = 0;
    }
    

    public int getNumberEdges() {
        return contadorBordes;
    }
    
    
public StringBuilder printAdjacencyMatrix() throws GraphException {
    final int ANCHURA_TABLA = 40; 
    StringBuilder salida = new StringBuilder();

    salida.append(" ".repeat(ANCHURA_TABLA)); 
    for (int i = 0; i < getNumberVertices(); i++) {
        String nombreVertice = getVertex(i).toString();
        salida.append(String.format("%-" + ANCHURA_TABLA + "s", nombreVertice));
    }
    salida.append("\n");
    salida.append("-".repeat(ANCHURA_TABLA * (getNumberVertices() + 1))).append("\n");

    for (int i = 0; i < getNumberVertices(); i++) {
        String nombreVertice = getVertex(i).toString();
        salida.append(String.format("%-" + ANCHURA_TABLA + "s", nombreVertice));
        
        for (int j = 0; j < getNumberVertices(); j++) {
            
            if (matrizAdyacencia[i][j] != null) {
                salida.append(String.format("%-" + ANCHURA_TABLA + "s", matrizAdyacencia[i][j])); 
                
            } else {
                
                salida.append(String.format("%-" + ANCHURA_TABLA + "s", "0")); 
                
            }
        }
        
        salida.append("\n");
    }
    
    return salida;
}
 
public StringBuilder primMST() throws GraphException {
   
    if (empty()) {
        throw new GraphException("El Grafo esta Vacio");
    }
    
    
    boolean[] inMST = new boolean[maxVertices]; 
    W[] aristaMin = (W[]) new Object[maxVertices]; 
    int[] padre = new int[maxVertices]; 
    
    for (int i = 0; i < maxVertices; i++) {
        aristaMin[i] = null;
        padre[i] = -1;
    }


    aristaMin[0] = (W) new Integer(0); 
    PriorityQueue<int[]> prioridad = new PriorityQueue<>(Comparator.comparingInt(a -> (Integer) aristaMin[a[0]]));
    prioridad.add(new int[]{0}); 
    

    while (!prioridad.isEmpty()) {
        int u = prioridad.poll()[0]; 
        
        if (inMST[u]) continue; 
        inMST[u] = true;

      
        for (int v = 0; v < getNumberVertices(); v++) {
            
            if (matrizAdyacencia[u][v] != null && !inMST[v]) {
                
                if (aristaMin[v] == null || (Integer) matrizAdyacencia[u][v] < (Integer) aristaMin[v]) {
                    aristaMin[v] = matrizAdyacencia[u][v]; 
                    padre[v] = u; 
                    prioridad.add(new int[]{v});
                    
                }
            }
        }
    }
    
    
    StringBuilder salida = new StringBuilder();
    salida.append("Aristas en el Árbol de Esparcimiento Mínimo: \n");
    salida.append(String.format("%-40s %-40s %-40s%n", "Inicio", "Destino", "Peso"));
    salida.append("---------------------------------------------------------------------------------------\n");
    
    
    for (int i = 1; i < getNumberVertices(); i++) {
        
        if (padre[i] != -1) {
            salida.append(String.format("%-40s %-40s %-40s%n", 
                getVertex(padre[i]), 
                getVertex(i), 
                matrizAdyacencia[padre[i]][i]));
        }
    }
    salida.append("---------------------------------------------------------------------------------------");
    return salida;
}
}