package Implementacion;

/**
 * Clase que representa una arista del grafo, con sus vértices de origen,
 * destino y peso.
 */
public class Arista {

    private int origen;
    private int destino;
    private double distancia;

    /**
     * Constructor para inicializar una arista.
     *
     * @param origen    Identificador del vértice de origen.
     * @param destino   Identificador del vértice de destino.
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