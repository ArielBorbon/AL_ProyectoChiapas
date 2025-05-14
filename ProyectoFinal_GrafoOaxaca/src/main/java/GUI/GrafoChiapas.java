package GUI;

import java.util.LinkedList;
import java.util.List;

import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class GrafoChiapas {

    private final List<Vertice> ciudades = new LinkedList<>();

    private final int[][] coordenadas = {
    {120, 180},  // Tuxtla Gutiérrez
    {30, 285},   // Tapachula de Córdova y Ordóñez
    {150, 150},  // San Cristóbal de Las Casas
    {180, 135},  // Comitán de Domínguez
    {126, 174},  // Heroica Chiapa de Corzo
    {270, 60},   // Palenque
    {75, 195},   // Cintalapa de Figueroa
    {240, 144},  // Ocosingo
    {90, 180},   // Ocozocoautla de Espinosa
    {54, 240},   // Tonalá
    {105, 198},  // Villaflores
    {99, 186},   // Berriozábal
    {39, 264},   // Huixtla
    {30, 90},    // Reforma
    {36, 228},   // Motozintla de Mendoza
    {45, 246},   // Arriaga
    {210, 126},  // Las Margaritas
    {195, 120},  // Frontera Comalapa
    {180, 141},  // Las Rosas
    {174, 147}   // Teopisca
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
        Vertice sanCristobal = ciudades.get(ciudades.indexOf((new Vertice("San Cristóbal de Las Casas"))));
        Vertice ocozocautla = ciudades.get(ciudades.indexOf((new Vertice("Ocozocoautla de Espinosa"))));
        Vertice chiapa = ciudades.get(ciudades.indexOf((new Vertice("Heroica Chiapa de Corzo"))));
        Vertice tapachula = ciudades.get(ciudades.indexOf((new Vertice("Tapachula de Córdova y Ordóñez"))));
        Vertice comitan = ciudades.get(ciudades.indexOf((new Vertice("Comitán de Domínguez"))));
        Vertice cintalapa = ciudades.get(ciudades.indexOf((new Vertice("Cintalapa de Figueroa"))));
        Vertice ocosingo = ciudades.get(ciudades.indexOf((new Vertice("Ocosingo"))));
        Vertice tonala = ciudades.get(ciudades.indexOf((new Vertice("Tonalá"))));
        Vertice villaflores = ciudades.get(ciudades.indexOf((new Vertice("Villaflores"))));
        Vertice berriozabal = ciudades.get(ciudades.indexOf((new Vertice("Berriozábal"))));
        Vertice huixtla = ciudades.get(ciudades.indexOf((new Vertice("Huixtla"))));
        Vertice reforma = ciudades.get(ciudades.indexOf((new Vertice("Reforma"))));
        Vertice motozintla = ciudades.get(ciudades.indexOf((new Vertice("Motozintla de Mendoza"))));
        Vertice arriaga = ciudades.get(ciudades.indexOf((new Vertice("Arriaga"))));
        Vertice lasMargaritas = ciudades.get(ciudades.indexOf((new Vertice("Las Margaritas"))));
        Vertice fronteraComalapa = ciudades.get(ciudades.indexOf((new Vertice("Frontera Comalapa"))));
        Vertice lasRosas = ciudades.get(ciudades.indexOf((new Vertice("Las Rosas"))));
        Vertice teopisca = ciudades.get(ciudades.indexOf((new Vertice("Teopisca"))));
        Vertice palenque = ciudades.get(ciudades.indexOf((new Vertice("Palenque"))));

        // Tuxtla
        grafo.agregarArista(tuxtla, sanCristobal, 59);
        grafo.agregarArista(tuxtla, ocozocautla, 34.7);
        grafo.agregarArista(tuxtla, berriozabal, 24.4);
        grafo.agregarArista(tuxtla, villaflores, 90.7);
        grafo.agregarArista(tuxtla, tapachula, 120);
        grafo.agregarArista(tuxtla, tonala, 150);
        grafo.agregarArista(tuxtla, arriaga, 130);
        grafo.agregarArista(tuxtla, palenque, 385);
        grafo.agregarArista(tuxtla, fronteraComalapa, 250);
        grafo.agregarArista(tuxtla, motozintla, 388);
        grafo.agregarArista(tuxtla, reforma, 213);
        grafo.agregarArista(tuxtla, lasRosas, 124);
        grafo.agregarArista(tuxtla, chiapa, 15.1);

        // San Cristóbal
        grafo.agregarArista(sanCristobal, teopisca, 33.2);
        grafo.agregarArista(sanCristobal, ocosingo, 95.6);

        // Tapachula
        grafo.agregarArista(tapachula, huixtla, 41.9);

        // Comitan
        grafo.agregarArista(comitan, teopisca, 57.8);
        grafo.agregarArista(comitan, lasRosas, 37.6);
        grafo.agregarArista(comitan, lasMargaritas, 20.5);
        grafo.agregarArista(comitan, fronteraComalapa, 96.1);
        grafo.agregarArista(comitan, ocosingo, 103);

        // Heroica Chiapa de Corzo
        grafo.agregarArista(chiapa, sanCristobal, 53.1);

        // Palenque
        grafo.agregarArista(palenque, ocosingo, 118);
        grafo.agregarArista(palenque, reforma, 187);

        // Cintalapa
        grafo.agregarArista(cintalapa, arriaga, 68.9);
        grafo.agregarArista(cintalapa, ocozocautla, 47.5);

        // Ocozocautla
        grafo.agregarArista(ocozocautla, berriozabal, 14.5);

        // Ocosingo
        grafo.agregarArista(ocosingo, palenque, 119);
        grafo.agregarArista(ocosingo, lasMargaritas, 93.6);

        // Tonalá
        grafo.agregarArista(tonala, arriaga, 27.4);

        // Villaflores
        grafo.agregarArista(villaflores, berriozabal, 90.3);
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