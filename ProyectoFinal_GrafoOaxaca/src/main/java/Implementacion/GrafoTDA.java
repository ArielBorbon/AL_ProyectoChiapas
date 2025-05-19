package Implementacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Clase que representa un grafo no dirigido, implementado mediante una
 * estructura de mapa de listas de adyacencia. Ofrece métodos para agregar
 * vértices, agregar aristas, obtener adyacencias y calcular el Árbol de
 * Expansión Mínima (MST) utilizando el algoritmo de Kruskal.
 *
 */
public class GrafoTDA {                                                             //T(N) = 5n + 46        O(N)

    private Map<Vertice, List<Arista>> grafo;

    /**
     * Constructor por defecto que inicializa un grafo vacío.
     */
    public GrafoTDA() {
        this.grafo = new HashMap<>();    //2
    }

    /**
     * Constructor que permite inicializar el grafo con un mapa de adyacencias
     * dado.
     *
     * @param grafo Mapa de vértices con sus listas de adyacencias.
     */
    public GrafoTDA(Map<Vertice, List<Arista>> grafo) {
        this.grafo = grafo;  //1
    }

    /**
     * Agrega un vértice al grafo si aún no existe.
     *
     * @param Vertice vertice a agregar.
     */
    public void agregarVertice(Vertice vertice) {
        grafo.putIfAbsent(vertice, new ArrayList<>());   //2
    }

    /**
     * Agrega una arista al grafo no dirigido.
     *
     * @param origen Identificador del vértice de origen.
     * @param destino Identificador del vértice de destino.
     * @param distancia Peso de la arista.
     */
   public void agregarArista(Vertice origen, Vertice destino, double distancia) {          //15
    Vertice realOrigen = buscarVertice(origen.getNombre());         //3
    Vertice realDestino = buscarVertice(destino.getNombre());        //3

    if (realOrigen == null || realDestino == null) {         //2
        throw new IllegalArgumentException("Ambos vértices deben existir en el grafo.");    //1
    }

    grafo.get(realOrigen).add(new Arista(realOrigen, realDestino, distancia));  //3
    grafo.get(realDestino).add(new Arista(realDestino, realOrigen, distancia)); //3
}


    /**
     * Obtiene la lista de aristas adyacentes a un vértice dado.
     *
     * @param vertice Vertice a buscar.
     * @return Lista de aristas adyacentes.
     */
    public List<Arista> obtenerAdyacentes(Vertice vertice) {            
        return grafo.get(vertice);      //1
    }
    private Vertice buscarVertice(String nombre) {          //3N + 3
    for (Vertice v : grafo.keySet()) {      // 2n + 2
        if (v.getNombre().equals(nombre)) {
            return v;   //N
        }
    }
    return null;        //1
}


    /**
     * Obtiene el conjunto de vértices del grafo.
     *
     * @return Conjunto de identificadores de los vértices.
     */
    public Set<Vertice> obtenerVertices() {
        return grafo.keySet();   //1
    }

    public Map<Vertice, List<Arista>> getGrafo() {
        return grafo;        //1
    }

    public void setGrafo(Map<Vertice, List<Arista>> grafo) {
        this.grafo = grafo;      //2
    }
    
    
    
    

}
