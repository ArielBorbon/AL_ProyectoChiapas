package Implementacion;

import java.util.*;

/**
 * Clase que representa un grafo no dirigido, implementado mediante una
 * estructura de mapa de listas de adyacencia. Ofrece métodos para agregar
 * vértices, agregar aristas, obtener adyacencias y calcular el Árbol de
 * Expansión Mínima (MST) utilizando el algoritmo de Kruskal.
 *
 * @author Garcia Acosta Alicia Denise 00000252402
 * @author Luna Esquer Pedro 00000252687
 * @author Preciado Guerrero Mario Alejandro 00000252940
 */
public class GrafoTDA {

    private Map<Vertice, List<Arista>> grafo;

    /**
     * Constructor por defecto que inicializa un grafo vacío.
     */
    public GrafoTDA() {
        this.grafo = new HashMap<>();
    }

    /**
     * Constructor que permite inicializar el grafo con un mapa de adyacencias
     * dado.
     *
     * @param grafo Mapa de vértices con sus listas de adyacencias.
     */
    public GrafoTDA(Map<Vertice, List<Arista>> grafo) {
        this.grafo = grafo;
    }

    /**
     * Agrega un vértice al grafo si aún no existe.
     *
     * @param Vertice vertice a agregar.
     */
    public void agregarVertice(Vertice vertice) {
        grafo.putIfAbsent(vertice, new ArrayList<>());
    }

    /**
     * Agrega una arista al grafo no dirigido.
     *
     * @param origen Identificador del vértice de origen.
     * @param destino Identificador del vértice de destino.
     * @param distancia Peso de la arista.
     */
    public void agregarArista(Vertice origen, Vertice destino, double distancia) {
        if (!grafo.containsKey(origen) || !grafo.containsKey(destino)) {
            throw new IllegalArgumentException("Ambos vértices deben existir en el grafo.");
        }
        grafo.get(origen).add(new Arista(origen, destino, distancia));
        grafo.get(destino).add(new Arista(destino, origen, distancia)); 
    }

    /**
     * Obtiene la lista de aristas adyacentes a un vértice dado.
     *
     * @param vertice Vertice a buscar.
     * @return Lista de aristas adyacentes.
     */
    public List<Arista> obtenerAdyacentes(Vertice vertice) {
        return grafo.get(vertice);
    }

    /**
     * Obtiene el conjunto de vértices del grafo.
     *
     * @return Conjunto de identificadores de los vértices.
     */
    public Set<Vertice> obtenerVertices() {
        return grafo.keySet();
    }

    public Map<Vertice, List<Arista>> getGrafo() {
        return grafo;
    }

    public void setGrafo(Map<Vertice, List<Arista>> grafo) {
        this.grafo = grafo;
    }
    
    
    
    

}
