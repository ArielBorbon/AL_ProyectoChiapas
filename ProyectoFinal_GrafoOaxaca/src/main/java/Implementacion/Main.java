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
    }

}
