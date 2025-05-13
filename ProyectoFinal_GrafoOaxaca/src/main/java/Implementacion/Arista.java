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
    public Arista(Vertice origen, Vertice destino, double distancia) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }

    /**
     * Obtiene el vertice de origen de la arista.
     *
     * @return vertice de origen de la arista.
     */
    public Vertice getOrigen() {
        return origen;
    }

    /**
     * Obtiene el vertice de destino de la arista.
     *
     * @return vertice de destino de la arista.
     */
    public Vertice getDestino() {
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