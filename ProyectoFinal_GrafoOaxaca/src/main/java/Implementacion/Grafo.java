package Implementacion;

import java.util.*;
import java.util.stream.Collectors;

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
public class Grafo {

    private Map<Integer, List<Arista>> grafo;

    /**
     * Constructor por defecto que inicializa un grafo vacío.
     */
    public Grafo() {
        this.grafo = new HashMap<>();
    }

    /**
     * Constructor que permite inicializar el grafo con un mapa de adyacencias
     * dado.
     *
     * @param grafo Mapa de vértices con sus listas de adyacencias.
     */
    public Grafo(Map<Integer, List<Arista>> grafo) {
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
     * Calcula el Árbol de Expansión Mínima (MST) del grafo utilizando el
     * algoritmo de Kruskal.
     *
     * @return Mapa que representa el MST como listas de adyacencia.
     */
    public Map<Integer, List<Arista>> arbolEsparcimientoMinimo() {
        Map<Integer, List<Arista>> mst = new HashMap<>();
        List<Arista> aristas = new ArrayList<>();

        // Recopila todas las aristas del grafo
        for (List<Arista> aristasNodo : grafo.values()) {
            aristas.addAll(aristasNodo);
        }

        // Ordena las aristas por distancia (peso)
        aristas = aristas.stream()
                .sorted(Comparator.comparingDouble(Arista::getDistancia))
                .collect(Collectors.toList());

        // Inicializa la estructura de conjuntos disjuntos
        MakeSet conjuntos = new MakeSet(grafo.size());

        int numArista = 0;
        for (Arista arista : aristas) {
            // Agrega la arista al MST si no forma un ciclo
            if (conjuntos.union(arista.origen - 1, arista.destino - 1)) {
                mst.putIfAbsent(arista.origen, new ArrayList<>());
                mst.putIfAbsent(arista.destino, new ArrayList<>());

                mst.get(arista.origen).add(arista);
                mst.get(arista.destino).add(new Arista(arista.destino, arista.origen, arista.distancia));
                numArista++;
            }
            // Finaliza cuando se han agregado suficientes aristas
            if (numArista == grafo.size() - 1) {
                break;
            }
        }
        return mst;
    }

    /**
     * Clase auxiliar que implementa la estructura de conjuntos disjuntos con
     * compresión de caminos y unión por rango.
     */
    public static class MakeSet {

        private int[] padre;
        private int[] rango;

        /**
         * Constructor que inicializa los conjuntos disjuntos.
         *
         * @param n Número de elementos.
         */
        public MakeSet(int n) {
            padre = new int[n];
            rango = new int[n];
            for (int i = 0; i < n; i++) {
                padre[i] = i; // Inicialmente cada nodo es su propio conjunto
            }
        }

        /**
         * Encuentra el representante del conjunto al que pertenece un elemento.
         *
         * @param x Elemento cuyo conjunto se quiere encontrar.
         * @return Representante del conjunto.
         */
        public int find(int x) {
            if (padre[x] != x) {
                padre[x] = find(padre[x]); // Compresión de caminos
            }
            return padre[x];
        }

        /**
         * Une dos conjuntos si no están ya unidos.
         *
         * @param x Primer elemento.
         * @param y Segundo elemento.
         * @return True si los conjuntos fueron unidos, false si ya estaban en
         * el mismo conjunto.
         */
        public boolean union(int x, int y) {
            int raizX = find(x);
            int raizY = find(y);

            if (raizX == raizY) {
                return false;
            }

            // Unión por rango
            if (rango[raizX] < rango[raizY]) {
                padre[raizX] = raizY;
            } else if (rango[raizX] > rango[raizY]) {
                padre[raizY] = raizX;
            } else {
                padre[raizY] = raizX;
                rango[raizX]++;
            }

            return true;
        }
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

    /**
     * Clase que representa una arista del grafo, con sus vértices de origen,
     * destino y peso.
     */
    public static class Arista {

        private int origen;
        private int destino;
        private double distancia;

        /**
         * Constructor para inicializar una arista.
         *
         * @param origen Identificador del vértice de origen.
         * @param destino Identificador del vértice de destino.
         * @param distancia Peso de la arista.
         */
        public Arista(int origen, int destino, double distancia) {
            this.origen = origen;
            this.destino = destino;
            this.distancia = distancia;
        }

        /**
         * Obtiene el vertice de origen de la arista.
         *
         * @return vertice de origen de la arista.
         */
        public int getOrigen() {
            return origen;
        }

        /**
         * Obtiene el vertice de destino de la arista.
         *
         * @return vertice de destino de la arista.
         */
        public int getDestino() {
            return destino;
        }

        /**
         * Obtiene la distancia (peso) de la arista.
         *
         * @return peso de la arista.
         */
        public double getDistancia() {
            return distancia;
        }

    }

}
