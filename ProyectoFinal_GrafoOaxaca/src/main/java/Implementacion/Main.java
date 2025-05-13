package Implementacion;

import GUI.Menu;
import GUI.MenuPrincipal;

/**
 * Clase principal para inicializar el grafo y desplegar el menú.
 *
 * @author Garcia Acosta Alicia Denise 00000252402
 * @author Luna Esquer Pedro 00000252687
 * @author Preciado Guerrero Mario Alejandro 00000252940
 */
public class Main {

    /**
     * Método principal que inicializa el programa.
     *
     * Crea una instancia de Grafo. - Agrega los vértices al grafo,
     * representando 20 ciudades. - Define las aristas entre los vértices con
     * las distancias dadas. - Llama a la clase Menu para interactuar con el
     * grafo.
     *
     */
    public static void main(String[] args) {
        GrafoTDA grafoChiapas = new GrafoTDA();
       // Menu menu = new Menu(grafo);
        MenuPrincipal menu = new MenuPrincipal();
        menu.setLocationRelativeTo(null);
        // Se agregan los vértices al grafo
        for (int i = 1; i < 21; i++) {
            grafoChiapas.agregarVertice(i);
        }

        // Se agregan las aristas al grafo
        grafoChiapas.agregarArista(1, 19, 6.5);
        grafoChiapas.agregarArista(1, 15, 9.1);
        grafoChiapas.agregarArista(1, 4, 8.2);
        grafoChiapas.agregarArista(1, 7, 4.1);
        grafoChiapas.agregarArista(1, 14, 7.3);
        grafoChiapas.agregarArista(1, 2, 217);
        grafoChiapas.agregarArista(2, 10, 36.6);
        grafoChiapas.agregarArista(2, 18, 217);
        grafoChiapas.agregarArista(3, 13, 17.4);
        grafoChiapas.agregarArista(3, 18, 61.1);
        grafoChiapas.agregarArista(3, 8, 27.8);
        grafoChiapas.agregarArista(4, 20, 10.6);
        grafoChiapas.agregarArista(5, 8, 14.9);
        grafoChiapas.agregarArista(5, 16, 146);
        grafoChiapas.agregarArista(6, 19, 165);
        grafoChiapas.agregarArista(6, 17, 120);
        grafoChiapas.agregarArista(7, 8, 246);
        grafoChiapas.agregarArista(8, 13, 38.8);
        grafoChiapas.agregarArista(9, 17, 216);
        grafoChiapas.agregarArista(9, 11, 153);
        grafoChiapas.agregarArista(11, 20, 183);
        grafoChiapas.agregarArista(11, 16, 114);
        grafoChiapas.agregarArista(12, 14, 98.7);
        grafoChiapas.agregarArista(12, 16, 253);
        grafoChiapas.agregarArista(15, 19, 2.4);
        grafoChiapas.agregarArista(17, 19, 158);
    }
}
