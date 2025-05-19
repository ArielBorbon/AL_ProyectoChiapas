package Algoritmos.Busqueda;

import Implementacion.Arista;

public interface BFSListener {
    /**
     * Se llama cada vez que se descubre una nueva arista del árbol BFS,
     * para permitir actualización visual paso a paso.
     */
    void onNuevaArista(Arista arista);
}
