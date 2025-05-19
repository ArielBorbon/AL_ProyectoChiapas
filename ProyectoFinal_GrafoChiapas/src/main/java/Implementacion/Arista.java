package Implementacion;

/**
 * Clase que representa una arista del grafo, con sus vértices de origen,
 * destino y peso.
 */
public class Arista {

    private Vertice origen;
    private Vertice destino;
    private double distancia;

    /**
     * Constructor para inicializar una arista.
     *
     * @param origen    vértice de origen.
     * @param destino   vértice de destino.
     * @param distancia Peso de la arista.
     */
    public Arista(Vertice origen, Vertice destino, double distancia) {          // T(N) = 6         O(1)
        this.origen = origen;           //1
        this.destino = destino;         //1
        this.distancia = distancia;     //1
    }

    /**
     * Obtiene el vertice de origen de la arista.
     *
     * @return vertice de origen de la arista.
     */
    public Vertice getOrigen() {
        return origen;       //1
    }

    /**
     * Obtiene el vertice de destino de la arista.
     *
     * @return vertice de destino de la arista.
     */
    public Vertice getDestino() {
        return destino;      //1
    }

    /**
     * Obtiene la distancia (peso) de la arista.
     *
     * @return peso de la arista.
     */
    public double getDistancia() {
        return distancia;        //1
    }

}