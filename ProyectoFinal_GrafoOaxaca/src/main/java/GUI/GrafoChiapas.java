package GUI;

import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class GrafoChiapas {
    
    private final String[] ciudades = { // Lista de nombres de las ciudades representadas como vértices
        "Tuxtla Gutiérrez", "Tapachula de Córdova y Ordóñez",
        "San Cristóbal de Las Casas", "Comitán de Domínguez",
        "Heroica Chiapa de Corzo", "Palenque",
        "Cintalapa de Figueroa", "Ocosingo",
        "Ocozocoautla de Espinosa", "Tonalá",
        "Villaflores", "Berriozábal", "Huixtla",
        "Reforma", "Motozintla de Mendoza", "Arriaga", 
        "Las Margaritas", "Frontera Comalapa", "Las Rosas",
        "Teopisca"
    };

    private final int[][] vertices = { // Coordenadas de las ciudades para visualización
        {15, 20}, // Tuxtla Gutiérrez
        {5, 35},  // Tapachula de Córdova y Ordóñez
        {20, 25}, // San Cristóbal de Las Casas
        {25, 30}, // Comitán de Domínguez
        {17, 22}, // Heroica Chiapa de Corzo
        {35, 10}, // Palenque
        {10, 18}, // Cintalapa de Figueroa
        {30, 20}, // Ocosingo
        {12, 20}, // Ocozocoautla de Espinosa
        {8, 28},  // Tonalá
        {14, 22}, // Villaflores
        {13, 19}, // Berriozábal
        {6, 32},  // Huixtla
        {5, 10},  // Reforma
        {7, 30},  // Motozintla de Mendoza
        {9, 26},  // Arriaga
        {28, 28}, // Las Margaritas
        {24, 32}, // Frontera Comalapa
        {22, 26}, // Las Rosas
        {21, 24}  // Teopisca
    };

    private final GrafoTDA grafo = new GrafoTDA();

    public GrafoChiapas() {
        inicializarGrafo();
    }

    private void inicializarGrafo() {
        for(String ciudad : ciudades ){
            grafo.agregarVertice(new Vertice(ciudad));
        }
        grafo.agregarArista(new Vertice("Tuxtla Gutiérrez"), new Vertice("Berriozábal"), 24);
        grafo.agregarArista(new Vertice("Tuxtla Gutiérrez"), new Vertice("San Cristóbal de las Casas"), 59);
        grafo.agregarArista(new Vertice("Tuxtla Gutiérrez"), new Vertice("Ocozocoautla de Espinosa"), 34.7);
        grafo.agregarArista(new Vertice("Berriozábal"), new Vertice("Ocozocoautla de Espinosa"), 15.1);
        grafo.agregarArista(new Vertice("San Cristóbal de las Casas"), new Vertice("Teopisca"), 33.7);

    }
}