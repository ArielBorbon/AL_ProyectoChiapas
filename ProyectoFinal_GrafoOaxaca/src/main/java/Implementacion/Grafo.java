package Implementacion;

import java.util.*;
import java.util.stream.Collectors;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class Grafo {

    private Map<Integer, List<Arista>> grafo;
    private Map<Integer, String> nombresCiudades;
    private Map<Integer, Integer> idToIndex;
    private org.graphstream.graph.Graph graphVisual;
    private Viewer viewer;

    // ----------------------- CONSTRUCTORES -----------------------
    public Grafo() {
        this.grafo = new HashMap<>();
        this.nombresCiudades = new HashMap<>();
        this.idToIndex = new HashMap<>();
    }

    public Grafo(Map<Integer, List<Arista>> grafo) {
        this();
        this.grafo = grafo;
    }
    // ----------------------- SECCIÓN DE VISUALIZACIÓN -----------------------

    /**
     * Genera un mapa con los IDs de nodos y sus nombres correspondientes
     *
     * @return Mapa ID -> Nombre de ciudad
     */
    public Map<Integer, String> generarTablaNodos() {
        return new HashMap<>(nombresCiudades);
    }

    /**
     * Genera lista de aristas con formato legible
     *
     * @return Lista de cadenas con formato "Origen -> Destino (Distancia)"
     */
    public List<String> generarTablaAristas() {
        Set<Arista> aristasUnicas = new HashSet<>();
        for (List<Arista> lista : grafo.values()) {
            for (Arista a : lista) {
                if (!aristasUnicas.contains(a) && !aristasUnicas.contains(a.inversa())) {
                    aristasUnicas.add(a);
                }
            }
        }

        return aristasUnicas.stream()
                .map(a -> String.format("%d -> %d (%.2f)", a.origen, a.destino, a.distancia))
                .collect(Collectors.toList());
    }

    /**
     * Crea/actualiza un nodo en la representación gráfica de GraphStream
     *
     * @param idCiudad ID del nodo a crear o actualizar
     */
    private void convertirANodoGraphStream(int idCiudad) {
        String nodeId = String.valueOf(idCiudad);

        // Crear nodo si no existe
        if (graphVisual.getNode(nodeId) == null) {
            org.graphstream.graph.Node node = graphVisual.addNode(nodeId);
            node.setAttribute("ui.label", obtenerEtiquetaNodo(idCiudad));
            node.setAttribute("ui.style", "fill-color: #999; size: 20px;");
        }
    }

    private String convertirAAristaGraphStream(Arista arista) {
        // Generar ID único para aristas no dirigidas (ej: "1-2" y "2-1" son la misma)
        String edgeId = generarIdUnicoArista(arista.origen, arista.destino);

        if (graphVisual.getEdge(edgeId) == null) {
            Edge edge = graphVisual.addEdge(
                    edgeId,
                    String.valueOf(arista.origen),
                    String.valueOf(arista.destino),
                    true // Indica que es dirigida (pero manejamos bidireccional)
            );

            edge.setAttribute("ui.label", String.format("%.2f", arista.distancia));
            edge.setAttribute("ui.style", "fill-color: #666;");
        }

        return edgeId;
    }

    public void resaltarElementos(List<Integer> nodos, List<Arista> aristas, String color) {
        // Resaltar nodos
        for (int id : nodos) {
            org.graphstream.graph.Node node = graphVisual.getNode(String.valueOf(id));
            if (node != null) {
                node.setAttribute("ui.style", "fill-color: " + color + "; size: 25px;");
            }
        }

        // Resaltar aristas
        for (Arista a : aristas) {
            String edgeId = generarIdUnicoArista(a.origen, a.destino);
            Edge edge = graphVisual.getEdge(edgeId);
            if (edge != null) {
                edge.setAttribute("ui.style", "fill-color: " + color + "; size: 3px;");
            }
        }

        actualizarVistaGrafica();
    }

    private String obtenerEtiquetaNodo(int idCiudad) {
        return nombresCiudades.containsKey(idCiudad)
                ? nombresCiudades.get(idCiudad) + " (" + idCiudad + ")"
                : String.valueOf(idCiudad);
    }

    private String generarIdUnicoArista(int origen, int destino) {
        // Para grafos no dirigidos: ordenar IDs para evitar duplicados
        return origen < destino
                ? origen + "-" + destino
                : destino + "-" + origen;
    }

    /**
     * Inicializa la visualización del grafo con GraphStream
     */
    public void inicializarVisualizacion() {
        System.setProperty("org.graphstream.ui", "swing");
        graphVisual = new SingleGraph("Grafo");
        graphVisual.setAttribute("ui.stylesheet",
                "node { size: 20px; fill-color: #999; text-alignment: above; }"
                + "edge { text-alignment: along; }");

        // Agregar nodos
        for (Integer id : grafo.keySet()) {
            org.graphstream.graph.Node n = graphVisual.addNode(String.valueOf(id));
            n.setAttribute("ui.label", nombresCiudades.getOrDefault(id, String.valueOf(id)));
        }

        // Agregar aristas
        Set<String> aristasAgregadas = new HashSet<>();
        for (Arista a : obtenerAristasUnicas()) {
            String edgeId = a.origen + "-" + a.destino;
            if (!aristasAgregadas.contains(edgeId)) {
                Edge e = graphVisual.addEdge(edgeId, String.valueOf(a.origen), String.valueOf(a.destino));
                e.setAttribute("ui.label", String.format("%.2f", a.distancia));
                aristasAgregadas.add(edgeId);
            }
        }

        viewer = graphVisual.display();
        viewer.disableAutoLayout();
    }

    /**
     * Actualiza la vista gráfica después de cambios
     */
    public void actualizarVistaGrafica() {
        if (viewer != null) {
            viewer.enableAutoLayout();
            try {
                Thread.sleep(500); // Permite que se actualice la visualización
            } catch (InterruptedException e) {
                /* Manejar excepción */ }
            viewer.disableAutoLayout();
        }
    }

    // ----------------------- SECCIÓN DE RECORRIDOS -----------------------
    // ----------------------- BFS -----------------------
    /**
     * Realiza recorrido BFS y retorna nodos por niveles
     *
     * @param semilla ID del nodo inicial
     * @return Lista de niveles con IDs de nodos
     */
    public List<List<Integer>> bfs(int semilla) {
        validarCiudadExiste(semilla);

        List<List<Integer>> niveles = new ArrayList<>();
        Queue<Integer> cola = new LinkedList<>();
        Set<Integer> visitados = new HashSet<>();
        Map<Integer, Integer> padres = new HashMap<>();

        cola.add(semilla);
        visitados.add(semilla);
        padres.put(semilla, null);

        while (!cola.isEmpty()) {
            int nivelSize = cola.size();
            List<Integer> nivelActual = new ArrayList<>();

            for (int i = 0; i < nivelSize; i++) {
                int actual = cola.poll();
                nivelActual.add(actual);

                for (Arista arista : obtenerAdyacentes(actual)) {
                    int vecino = arista.destino;
                    if (!visitados.contains(vecino)) {
                        visitados.add(vecino);
                        padres.put(vecino, actual);
                        cola.add(vecino);
                    }
                }
            }
            niveles.add(nivelActual);
        }
        return niveles;
    }

    /**
     * Obtiene la lista de aristas adyacentes a un vértice (Ya implementado)
     *
     * @param ciudad Identificador del vértice
     * @return Lista de aristas conectadas
     */
    public List<Arista> obtenerAdyacentes(int ciudad) {
        validarCiudadExiste(ciudad); // Añadir validación
        return grafo.getOrDefault(ciudad, Collections.emptyList());
    }

    /**
     * Genera el árbol de expansión BFS
     *
     * @param semilla ID del nodo inicial
     * @return Mapa de adyacencias del árbol
     */
    public Map<Integer, List<Arista>> generarArbolBFS(int semilla) {
        List<List<Integer>> niveles = bfs(semilla);
        Map<Integer, List<Arista>> arbol = new HashMap<>();
        Map<Integer, Integer> padres = new HashMap<>();

        // Construir mapa de padres
        for (List<Integer> nivel : niveles) {
            for (int nodo : nivel) {
                for (Arista arista : obtenerAdyacentes(nodo)) {
                    if (padres.get(arista.destino) != null
                            && padres.get(arista.destino) == nodo) {
                        arbol.computeIfAbsent(nodo, k -> new ArrayList<>()).add(arista);
                    }
                }
            }
        }
        return arbol;
    }

    // ----------------------- SECCIÓN MST -----------------------
    /**
     * Calcula el peso total del MST
     *
     * @param mst Mapa de adyacencias del árbol
     * @return Peso total sumando todas las aristas únicas
     */
    public double calcularPesoTotalMST(Map<Integer, List<Arista>> mst) {
        Set<Arista> aristasUnicas = new HashSet<>();
        double pesoTotal = 0.0;

        for (List<Arista> aristas : mst.values()) {
            for (Arista a : aristas) {
                if (aristasUnicas.add(a) && aristasUnicas.add(a.inversa())) {
                    pesoTotal += a.distancia;
                }
            }
        }
        return pesoTotal;
    }

    /**
     * Muestra las aristas del MST formateadas
     *
     * @param aristas Lista de aristas del MST
     */
    public void mostrarAristasKruskal(List<Arista> aristas) {
        Set<Arista> mostradas = new HashSet<>();
        System.out.println("\nAristas seleccionadas en MST (Kruskal):");
        double pesoTotal = 0.0;

        for (Arista a : aristas) {
            if (!mostradas.contains(a) && !mostradas.contains(a.inversa())) {
                System.out.printf("%d - %d : %.2f%n",
                        a.origen, a.destino, a.distancia);
                mostradas.add(a);
                pesoTotal += a.distancia; // Calcular peso aquí
            }
        }

        System.out.printf("Peso total del MST: %.2f%n", pesoTotal);
    }

    /**
     * Muestra niveles de BFS en consola
     *
     * @param niveles Resultado del método bfs()
     */
    public void mostrarSecuenciaNivelesBFS(List<List<Integer>> niveles) {
        System.out.println("Recorrido BFS por niveles:");
        for (int i = 0; i < niveles.size(); i++) {
            System.out.printf("Nivel %d: %s%n", i, niveles.get(i));
        }
    }

    // ----------------------- DFS -----------------------
    /**
     * Realiza recorrido DFS iterativo
     *
     * @param semilla ID del nodo inicial
     * @return Lista de nodos en orden de descubrimiento
     */
    public List<Integer> dfs(int semilla) {
        validarCiudadExiste(semilla);

        List<Integer> descubiertos = new ArrayList<>();
        Stack<Integer> pila = new Stack<>();
        Set<Integer> visitados = new HashSet<>();
        Map<Integer, Integer> padres = new HashMap<>();

        pila.push(semilla);
        visitados.add(semilla);
        padres.put(semilla, null);

        while (!pila.isEmpty()) {
            int actual = pila.pop();
            descubiertos.add(actual);

            // Para mantener orden natural, invertir adyacentes
            List<Arista> adyacentes = new ArrayList<>(obtenerAdyacentes(actual));
            Collections.reverse(adyacentes);

            for (Arista arista : adyacentes) {
                int vecino = arista.destino;
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    padres.put(vecino, actual);
                    pila.push(vecino);
                }
            }
        }
        return descubiertos;
    }

    /**
     * Genera el árbol de expansión DFS
     *
     * @param semilla ID del nodo inicial
     * @return Mapa de adyacencias del árbol
     */
    public Map<Integer, List<Arista>> generarArbolDFS(int semilla) {
        List<Integer> recorrido = dfs(semilla);
        Map<Integer, List<Arista>> arbol = new HashMap<>();
        Map<Integer, Integer> padres = new HashMap<>();

        // Reconstruir relaciones padre-hijo
        Set<Integer> visitados = new HashSet<>();
        Stack<Integer> pila = new Stack<>();
        pila.push(semilla);
        visitados.add(semilla);

        while (!pila.isEmpty()) {
            int actual = pila.pop();
            for (Arista arista : obtenerAdyacentes(actual)) {
                if (visitados.contains(arista.destino) && padres.get(actual) == arista.destino) {
                    // Es la arista del árbol
                    arbol.computeIfAbsent(actual, k -> new ArrayList<>()).add(arista);
                } else if (!visitados.contains(arista.destino)) {
                    visitados.add(arista.destino);
                    padres.put(arista.destino, actual);
                    pila.push(arista.destino);
                    arbol.computeIfAbsent(actual, k -> new ArrayList<>()).add(arista);
                }
            }
        }
        return arbol;
    }

    // ----------------------- MÉTODOS BÁSICOS MEJORADOS -----------------------
    private void validarCiudadExiste(int idCiudad) {
        if (!grafo.containsKey(idCiudad)) {
            throw new IllegalArgumentException("La ciudad " + idCiudad + " no existe");
        }
    }

    public void agregarVertice(int idCiudad) {
        this.agregarVertice(idCiudad, "");
    }

    public void agregarVertice(int idCiudad, String nombre) {
        grafo.putIfAbsent(idCiudad, new ArrayList<>());
        nombresCiudades.put(idCiudad, nombre);
        generarMapeoIndices();
    }

    public void agregarArista(int origen, int destino, double distancia) {
        validarCiudadExiste(origen);
        validarCiudadExiste(destino);

        grafo.get(origen).add(new Arista(origen, destino, distancia));
        grafo.get(destino).add(new Arista(destino, origen, distancia));
    }

    // ----------------------- KRUSKAL CORREGIDO -----------------------
    public Map<Integer, List<Arista>> arbolEsparcimientoMinimo() {
        if (grafo.isEmpty()) {
            throw new IllegalStateException("Grafo vacío");
        }
        generarMapeoIndices();
        List<Arista> aristas = obtenerAristasUnicas();
        MakeSet conjuntos = new MakeSet(grafo.size());
        Map<Integer, List<Arista>> mst = new HashMap<>();

        aristas.sort(Comparator.comparingDouble(Arista::getDistancia));

        int aristasAgregadas = 0;
        for (Arista arista : aristas) {
            int idxOrigen = idToIndex.get(arista.origen);
            int idxDestino = idToIndex.get(arista.destino);

            if (conjuntos.union(idxOrigen, idxDestino)) {
                agregarAristaAlMST(mst, arista);
                if (++aristasAgregadas == grafo.size() - 1) {
                    break;
                }
            }
        }
        if (aristasAgregadas != grafo.size() - 1) {
            throw new RuntimeException("El grafo no es conexo");
        }
        return mst;
    }

    private void agregarAristaAlMST(Map<Integer, List<Arista>> mst, Arista arista) {
        mst.computeIfAbsent(arista.origen, k -> new ArrayList<>()).add(arista);
        mst.computeIfAbsent(arista.destino, k -> new ArrayList<>())
                .add(new Arista(arista.destino, arista.origen, arista.distancia));
    }
    
    
    
    
    
    // ----------------------- BORUVKA -----------------------
private List<List<Arista>> fasesBoruvka; // Nuevo miembro de clase

public Map<Integer, List<Arista>> boruvkaMST() {
    validarGrafoNoVacio();
    generarMapeoIndices();
    
    MakeSet conjuntos = new MakeSet(grafo.size());
    Map<Integer, List<Arista>> mst = new HashMap<>();
    fasesBoruvka = new ArrayList<>();
    int numComponentes = grafo.size();

    while (numComponentes > 1) {
        Map<Integer, Arista> minAristas = new HashMap<>(); // <Componente, MejorArista>
        List<Arista> faseActual = new ArrayList<>();

        // Fase 1: Encontrar la mejor arista para cada componente
        for (Arista arista : obtenerAristasUnicas()) {
            int u = idToIndex.get(arista.origen);
            int v = idToIndex.get(arista.destino);
            
            int raizU = conjuntos.find(u);
            int raizV = conjuntos.find(v);
            
            if (raizU != raizV) {
                // Actualizar mejor arista para ambos componentes
                if (!minAristas.containsKey(raizU) || arista.distancia < minAristas.get(raizU).distancia) {
                    minAristas.put(raizU, arista);
                }
                if (!minAristas.containsKey(raizV) || arista.distancia < minAristas.get(raizV).distancia) {
                    minAristas.put(raizV, arista);
                }
            }
        }

        // Fase 2: Unir componentes y construir MST
        Set<Arista> aristasAgregadas = new HashSet<>();
        for (Arista arista : minAristas.values()) {
            if (aristasAgregadas.contains(arista) || aristasAgregadas.contains(arista.inversa())) {
                continue;
            }
            
            int u = idToIndex.get(arista.origen);
            int v = idToIndex.get(arista.destino);
            
            if (conjuntos.union(u, v)) {
                agregarAristaAlMST(mst, arista);
                faseActual.add(arista);
                aristasAgregadas.add(arista);
                numComponentes--;
            }
        }

        if (faseActual.isEmpty()) {
            throw new RuntimeException("Grafo no conexo");
        }

        fasesBoruvka.add(faseActual);
    }

    return mst;
}

public List<List<Arista>> obtenerFasesBoruvka() {
    if (fasesBoruvka == null) {
        throw new IllegalStateException("Ejecutar boruvkaMST() primero");
    }
    return new ArrayList<>(fasesBoruvka);
}

// ----------------------- MÉTODOS AUXILIARES -----------------------
private void validarGrafoNoVacio() {
    if (grafo.isEmpty()) {
        throw new IllegalStateException("El grafo está vacío");
    }
}

    // ----------------------- DIJKSTRA MEJORADO -----------------------
    public ResultadoDijkstra caminoMasCorto(int origen, int destino) {
        validarCiudadExiste(origen);
        validarCiudadExiste(destino);

        Map<Integer, Double> distancias = new HashMap<>();
        Map<Integer, Integer> previos = new HashMap<>();
        PriorityQueue<DistanciaNodo> cola = new PriorityQueue<>(Comparator.comparingDouble(DistanciaNodo::getDistancia));

        inicializarEstructurasDijkstra(distancias, previos, origen);
        cola.add(new DistanciaNodo(origen, 0.0));

        while (!cola.isEmpty()) {
            DistanciaNodo actual = cola.poll();
            if (actual.nodo == destino) {
                break;
            }

            for (Arista arista : grafo.get(actual.nodo)) {
                actualizarDistancias(actual, arista, distancias, previos, cola);
            }
        }
        if (distancias.get(destino) == Double.POSITIVE_INFINITY) {
            return new ResultadoDijkstra(Collections.emptyList(), Double.POSITIVE_INFINITY);
        }

        return new ResultadoDijkstra(reconstruirCamino(previos, destino), distancias.get(destino));
    }

    // ----------------------- CLASES AUXILIARES ACTUALIZADAS -----------------------
    public static class MakeSet {

        private int[] padre;
        private int[] rango;

        public MakeSet(int n) {
            padre = new int[n];
            rango = new int[n];
            Arrays.setAll(padre, i -> i);
        }

        public int find(int x) {
            return padre[x] = (padre[x] == x) ? x : find(padre[x]);
        }

        public boolean union(int x, int y) {
            int raizX = find(x), raizY = find(y);
            if (raizX == raizY) {
                return false;
            }

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

    public static class Arista {

        public final int origen;
        public final int destino;
        public final double distancia;

        public Arista(int origen, int destino, double distancia) {
            this.origen = origen;
            this.destino = destino;
            this.distancia = distancia;

        }

        public int getOrigen() {
            return origen;
        }

        public int getDestino() {
            return destino;
        }

        public Arista inversa() {
            return new Arista(destino, origen, distancia);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            Arista otra = (Arista) o;
            return (origen == otra.origen && destino == otra.destino)
                    || (origen == otra.destino && destino == otra.origen);
        }

        @Override
        public int hashCode() {
            // Antes: return origen + destino;
            return Objects.hash(Math.min(origen, destino), Math.max(origen, destino));
        }

        public double getDistancia() {
            return this.distancia;
        }
    }

    // ----------------------- MÉTODOS AUXILIARES PRIVADOS -----------------------
    private void generarMapeoIndices() {
        idToIndex.clear();
        int index = 0;
        for (Integer id : grafo.keySet()) {
            idToIndex.put(id, index++);
        }
    }

    private List<Arista> obtenerAristasUnicas() {
        Set<Arista> aristasUnicas = new HashSet<>();
        grafo.values().forEach(lista -> lista.forEach(arista -> {
            if (!aristasUnicas.contains(new Arista(arista.destino, arista.origen, arista.distancia))) {
                aristasUnicas.add(arista);
            }
        }));
        return new ArrayList<>(aristasUnicas);
    }

    private void inicializarEstructurasDijkstra(Map<Integer, Double> distancias,
            Map<Integer, Integer> previos,
            int origen) {
        grafo.keySet().forEach(nodo -> {
            distancias.put(nodo, Double.POSITIVE_INFINITY);
            previos.put(nodo, null);
        });
        distancias.put(origen, 0.0);
    }

    private void actualizarDistancias(DistanciaNodo actual, Arista arista,
            Map<Integer, Double> distancias,
            Map<Integer, Integer> previos,
            PriorityQueue<DistanciaNodo> cola) {
        double nuevaDist = actual.distancia + arista.distancia;
        if (nuevaDist < distancias.get(arista.destino)) {
            distancias.put(arista.destino, nuevaDist);
            previos.put(arista.destino, actual.nodo);
            cola.add(new DistanciaNodo(arista.destino, nuevaDist));
        }
    }

    private List<Integer> reconstruirCamino(Map<Integer, Integer> previos, int destino) {
        LinkedList<Integer> camino = new LinkedList<>();
        Integer actual = destino;
        while (actual != null) {
            camino.addFirst(actual);
            actual = previos.get(actual);
        }
        return camino.isEmpty() || camino.getFirst() != previos.get(destino)
                ? Collections.emptyList() : camino;
    }

    // ----------------------- CLASES DE RESULTADO -----------------------
    public static class ResultadoDijkstra {

        public final List<Integer> camino;
        public final double distanciaTotal;

        public ResultadoDijkstra(List<Integer> camino, double distanciaTotal) {
            this.camino = Collections.unmodifiableList(camino);
            this.distanciaTotal = distanciaTotal;
        }
    }
    
    
    
    
    

    public Map<Integer, List<Arista>> primMST(int semilla) {
        validarCiudadExiste(semilla);

        Map<Integer, List<Arista>> mst = new HashMap<>();
        PriorityQueue<Arista> cola = new PriorityQueue<>(Comparator.comparingDouble(Arista::getDistancia));
        Set<Integer> visitados = new HashSet<>();
        List<Arista> aristasSeleccionadas = new ArrayList<>(); // Para el progreso

        // Inicialización
        visitados.add(semilla);
        agregarAristasVecinas(cola, semilla, visitados);

        while (!cola.isEmpty() && visitados.size() < grafo.size()) {
            Arista aristaMin = cola.poll();

            // Solo procesar si un extremo no está visitado
            if (!visitados.contains(aristaMin.destino)) {
                // Agregar al MST
                agregarAristaMST(mst, aristaMin);
                aristasSeleccionadas.add(aristaMin);

                // Marcar como visitado
                visitados.add(aristaMin.destino);

                // Agregar nuevas aristas a la cola
                agregarAristasVecinas(cola, aristaMin.destino, visitados);
            }
        }

        // Validar grafo conexo
        if (visitados.size() != grafo.size()) {
            throw new RuntimeException("El grafo no es conexo");
        }

        mostrarProgresoPrim(aristasSeleccionadas);
        return mst;
    }

    // ----------------------- MÉTODOS AUXILIARES PRIM -----------------------
    private void agregarAristasVecinas(PriorityQueue<Arista> cola, int nodo, Set<Integer> visitados) {
        for (Arista arista : obtenerAdyacentes(nodo)) {
            if (!visitados.contains(arista.destino)) {
                cola.add(arista);
            }
        }
    }

    private void agregarAristaMST(Map<Integer, List<Arista>> mst, Arista arista) {
        mst.computeIfAbsent(arista.origen, k -> new ArrayList<>()).add(arista);
        mst.computeIfAbsent(arista.destino, k -> new ArrayList<>())
                .add(new Arista(arista.destino, arista.origen, arista.distancia));
    }

    public void mostrarProgresoPrim(List<Arista> aristasSeleccionadas) {
        System.out.println("\nProgreso del algoritmo Prim:");
        double pesoAcumulado = 0.0;

        for (int i = 0; i < aristasSeleccionadas.size(); i++) {
            Arista a = aristasSeleccionadas.get(i);
            pesoAcumulado += a.distancia;

            System.out.printf("Iteración %d: %d - %d (%.2f) | Peso acumulado: %.2f%n",
                    i + 1,
                    a.origen,
                    a.destino,
                    a.distancia,
                    pesoAcumulado
            );
        }

        System.out.printf("Peso total final: %.2f%n", pesoAcumulado);
    }

    public void generarVisualizacion() {
        /* Integrar con GraphStream */ }

    private static class DistanciaNodo implements Comparable<DistanciaNodo> {

        private final int nodo;
        private final double distancia;

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

        // Orden natural por distancia (para la cola de prioridad)
        @Override
        public int compareTo(DistanciaNodo otro) {
            return Double.compare(this.distancia, otro.distancia);
        }
    }

}
