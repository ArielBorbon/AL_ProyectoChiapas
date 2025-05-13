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

    private Map<Integer, List<Arista>> grafo;

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
    public GrafoTDA(Map<Integer, List<Arista>> grafo) {
        this.grafo = grafo;
    }

    /**
     * Agrega un vértice al grafo si aún no existe.
     *
     * @param idCiudad Identificador del vértice (ciudad).
     */
    public void agregarVertice(int idCiudad) {
        grafo.putIfAbsent(idCiudad, new ArrayList<>());
    }

    /**
     * Agrega una arista al grafo no dirigido.
     *
     * @param origen Identificador del vértice de origen.
     * @param destino Identificador del vértice de destino.
     * @param distancia Peso de la arista.
     */
    public void agregarArista(int origen, int destino, double distancia) {
        grafo.get(origen).add(new Arista(origen, destino, distancia));
        grafo.get(destino).add(new Arista(destino, origen, distancia)); // Grafo no dirigido
    }

    /**
     * Obtiene la lista de aristas adyacentes a un vértice dado.
     *
     * @param ciudad Identificador del vértice.
     * @return Lista de aristas adyacentes.
     */
    public List<Arista> obtenerAdyacentes(int ciudad) {
        return grafo.get(ciudad);
    }

    /**
     * Obtiene el conjunto de vértices del grafo.
     *
     * @return Conjunto de identificadores de los vértices.
     */
    public Set<Integer> obtenerVertices() {
        return grafo.keySet();
    }
    
    /**
     * Calcula el camino más corto desde un nodo origen a todos los demás nodos
     * utilizando el algoritmo de Dijkstra.
     *
     * @param origen El nodo de origen.
     * @param destino El nodo de destino.
     * @return Un nuevo grafo representando los caminos más cortos desde el
     * origen.
     */
    public Map<Integer, List<Arista>> caminoMasCorto(int origen, int destino) {
        // Inicialización de estructuras
        Map<Integer, Double> distancias = new HashMap<>(); //Crea un mapa donde se guardarán las distancias desde el origen a los demás nodos.
        Map<Integer, Integer> previos = new HashMap<>(); //Crea un mapa donde se guardarán registros del nodo y su nodo previo.
        PriorityQueue<DistanciaNodo> colaDistancias //para manejar las actualizaciones de las distancias entre el nodo origen y los demás
                = new PriorityQueue<>(Comparator.comparingDouble(DistanciaNodo::getDistancia));

        for (Integer nodo : grafo.keySet()) { //Le pone infinito a todas las distancias del mapa
            distancias.put(nodo, Double.POSITIVE_INFINITY);
            previos.put(nodo, null);
        }

        distancias.put(origen, 0.0); //La distancia del nodo de origen será 0
        colaDistancias.add(new DistanciaNodo(origen, 0.0));

        // Procesamiento de Dijkstra
        while (!colaDistancias.isEmpty()) {
            DistanciaNodo actual = colaDistancias.poll(); //Descarta la distancia del nodo actual porque ya se visitó 
            int nodoActual = actual.getNodo(); //Obtiene el nodo actual (id)
            double distanciaActual = actual.getDistancia(); //Obtiene la distancia del origen al nodo actual

            for (Arista arista : grafo.get(nodoActual)) { //Por cada arista del nodo actual
                int vecino = arista.getDestino(); //Define el vecino (destino de la arista)
                double nuevaDistancia = distanciaActual + arista.getDistancia(); //calcula la distancia entre las dos
                //Si la nueva distancia (calculada) es mayor a la distancia que ya tenía
                if (nuevaDistancia < distancias.get(vecino)) { 
                    distancias.put(vecino, nuevaDistancia); //Actualiza la distancia
                    previos.put(vecino, nodoActual); //Actualiza la lista de previos poniendole como previo al destino, el nodo actual
                    colaDistancias.add(new DistanciaNodo(vecino, nuevaDistancia)); //Agrega el vecino para trabajar sobre el.
                }
            }
        }

        // Construcción del grafo de caminos más cortos
        Map<Integer, List<Arista>> grafoCaminosCortos = new HashMap<>();
                
        for (Integer nodo : grafo.keySet()) {
            grafoCaminosCortos.putIfAbsent(nodo, new ArrayList<>());
        }
        
        Integer previo = previos.get(destino); 
        Integer nodo = destino;
        
        while(previo!= null){ //Agrega al nuevo grafo solo las aristas que conectan el nodo de origen al del destino
            double distancia = distancias.get(nodo)- distancias.get(previo);
            grafoCaminosCortos.get(previo).add(new Arista(previo, nodo, distancia));
            grafoCaminosCortos.get(nodo).add(new Arista(nodo, previo, distancia));
            nodo = previos.get(nodo);
            previo = previos.get(previo);
        }

        return grafoCaminosCortos;
    }

    private static class DistanciaNodo {

        private int nodo;
        private double distancia;

        public DistanciaNodo(int nodo, double distancia) {
            this.nodo = nodo;
            this.distancia = distancia;
        }

        public int getNodo() {
            return nodo;
        }

        public double getDistancia() {
            return distancia;
        }
    }

}
