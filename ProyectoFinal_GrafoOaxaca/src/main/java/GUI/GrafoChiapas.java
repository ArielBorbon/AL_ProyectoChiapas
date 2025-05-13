package GUI;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class GrafoChiapas {
    
    private final List<Vertice> ciudades = new LinkedList<>();

    private final int[][] coordenadas = { // Coordenadas de las ciudades para visualización
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
        inicializarCiudades();
        inicializarGrafo();
    }

    private void inicializarCiudades() {
        ciudades.add(new Vertice("Tuxtla Gutiérrez"));
        ciudades.add(new Vertice("Tapachula de Córdova y Ordóñez"));
        ciudades.add(new Vertice("San Cristóbal de Las Casas"));
        ciudades.add(new Vertice("Comitán de Domínguez"));
        ciudades.add(new Vertice("Heroica Chiapa de Corzo"));
        ciudades.add(new Vertice("Palenque"));
        ciudades.add(new Vertice("Cintalapa de Figueroa"));
        ciudades.add(new Vertice("Ocosingo"));
        ciudades.add(new Vertice("Ocozocoautla de Espinosa"));
        ciudades.add(new Vertice("Tonalá"));
        ciudades.add(new Vertice("Villaflores"));
        ciudades.add(new Vertice("Berriozábal"));
        ciudades.add(new Vertice("Huixtla"));
        ciudades.add(new Vertice("Reforma"));
        ciudades.add(new Vertice("Motozintla de Mendoza"));
        ciudades.add(new Vertice("Arriaga"));
        ciudades.add(new Vertice("Las Margaritas"));
        ciudades.add(new Vertice("Frontera Comalapa"));
        ciudades.add(new Vertice("Las Rosas"));
        ciudades.add(new Vertice("Teopisca"));
    }

    private void inicializarGrafo() {
        for (Vertice ciudad : ciudades) {
            grafo.agregarVertice(ciudad);
        }
        Vertice tuxtla = ciudades.get(ciudades.indexOf((new Vertice("Tuxtla Gutiérrez"))));
        grafo.agregarVertice(tuxtla);
        grafo.agregarArista(tuxtla, new Vertice("San Cristóbal de las Casas"), 59);
        grafo.agregarArista(tuxtla, new Vertice("Ocozocoautla de Espinosa"), 34.7);
        grafo.agregarArista(tuxtla, new Vertice("Berriozábal"), 24.4);
        grafo.agregarArista(tuxtla, new Vertice("Villaflores"), 90.7);
        grafo.agregarArista(tuxtla, new Vertice("Tapachula de Córdova y Ordóñez"), 120);
        grafo.agregarArista(tuxtla, new Vertice("Tonalá"), 150);
        grafo.agregarArista(tuxtla, new Vertice("Arriaga"), 130);
        grafo.agregarArista(tuxtla, new Vertice("Palenque"), 385);
        grafo.agregarArista(tuxtla, new Vertice("Palenque"), 385);
        grafo.agregarArista(tuxtla, new Vertice("Frontera Comalapa"), 250);
        grafo.agregarArista(tuxtla, new Vertice("Motozintla de Mendoza"), 388);
        grafo.agregarArista(tuxtla, new Vertice("Reforma"), 213);
        grafo.agregarArista(tuxtla, new Vertice("Las Rosas"), 124);
        grafo.agregarArista(tuxtla, new Vertice("Heroica Chiapa de Corzo"), 15.1);
        Vertice sanCristobal = ciudades.get(ciudades.indexOf((new Vertice("San Cristóbal de las Casas"))));
        grafo.agregarArista(sanCristobal, new Vertice("Teopisca"), 33.2);
        grafo.agregarArista(sanCristobal, new Vertice("Ocosingo"), 95.6);
        //Tapachula
        grafo.agregarArista(new Vertice("Tapachula de Córdova y Ordóñez"), new Vertice("Huixtla"), 41.9);
        Vertice comitan = ciudades.get(ciudades.indexOf((new Vertice("Comitán de Domínguez"))));
        grafo.agregarArista(comitan, new Vertice("Teopisca"), 57.8);
        grafo.agregarArista(comitan, new Vertice("Las Rosas"), 37.6);
        grafo.agregarArista(comitan, new Vertice("Las Margaritas"), 20.5);
        grafo.agregarArista(comitan, new Vertice("Frontera Comalapa"), 96.1);
        grafo.agregarArista(comitan, new Vertice("Ocosingo"), 103);
        Vertice chiapa = ciudades.get(ciudades.indexOf((new Vertice("Heroica Chiapa de Corzo"))));
        grafo.agregarArista(chiapa, new Vertice("San Cristóbal de las Casas"), 53.1);
        Vertice palenque = ciudades.get(ciudades.indexOf((new Vertice("Palenque"))));
        grafo.agregarArista(palenque, new Vertice("Ocosingo"), 118);
        grafo.agregarArista(palenque, new Vertice("Reforma"), 187);
        Vertice cintalapa = ciudades.get(ciudades.indexOf((new Vertice("Cintalapa de Figueroa"))));
        grafo.agregarArista(cintalapa, new Vertice("Arriaga"), 68.9);
        grafo.agregarArista(cintalapa, new Vertice("Ocozocautla de Espinosa"), 47.5);
        Vertice ocozocautla = ciudades.get(ciudades.indexOf((new Vertice("Ocozocoautla de Espinosa"))));
        grafo.agregarArista(ocozocautla, new Vertice("Berriozábal"), 14.5);
        Vertice ocosingo = ciudades.get(ciudades.indexOf((new Vertice("Ocosingo"))));
        grafo.agregarArista(ocosingo, new Vertice("Palenque"), 119);
        grafo.agregarArista(ocosingo, new Vertice("Las Margaritas"), 93.6);
        Vertice tonala = ciudades.get(ciudades.indexOf((new Vertice("Tonalá"))));
        grafo.agregarArista(tonala, new Vertice("Arriaga"), 27.4);
        Vertice villaflores = ciudades.get(ciudades.indexOf((new Vertice("Villaflores"))));
        grafo.agregarArista(villaflores, new Vertice("Berriozábal"), 90.3);
        grafo.agregarArista(villaflores, tonala, 133);
    }

    public List<Vertice> getCiudades() {
        return ciudades;
    }
    public int[][] getCoordenadas() {
        return coordenadas;
    }

    public GrafoTDA getGrafo() {
        return grafo;
    }
}