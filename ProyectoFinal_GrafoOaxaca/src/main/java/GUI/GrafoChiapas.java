package GUI;

import java.util.LinkedList;
import java.util.List;

import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class GrafoChiapas {                              //                             T(N) 6n  + 235      O(n)

    private final List<Vertice> ciudades = new LinkedList<>();          //2

    private final int[][] coordenadas = {       //2
    {380, 300}, // Tuxtla Gutiérrez
    {600, 50},   // Tapachula de Córdova y Ordóñez (Ajustada para origen inferior izquierda, Y arriba)
    {480, 320}, // San Cristóbal de Las Casas
    {650, 220}, // Comitán de Domínguez (Ajustada para origen inferior izquierda, Y arriba)
    {420, 280}, // Heroica Chiapa de Corzo (Ajustada para origen inferior izquierda, Y arriba)
    {750, 450}, // Palenque (Ajustada para origen inferior izquierda, Y arriba)
    {150, 280}, // Cintalapa de Figueroa (Ajustada para origen inferior izquierda, Y arriba)
    {700, 320}, // Ocosingo (Ajustada para origen inferior izquierda, Y arriba)
    {280, 280}, // Ocozocoautla de Espinosa (Ajustada para origen inferior izquierda, Y arriba)
    {200, 120}, // Tonalá (Ajustada para origen inferior izquierda, Y arriba)
    {350, 200}, // Villaflores (Ajustada para origen inferior izquierda, Y arriba)
    {350, 380}, // Berriozábal (Ajustada para origen inferior izquierda, Y arriba)
    {550, 80},  // Huixtla (Ajustada para origen inferior izquierda, Y arriba)
    {380, 520}, // Reforma (Ajustada para origen inferior izquierda, Y arriba)
    {600, 120}, // Motozintla de Mendoza (Ajustada para origen inferior izquierda, Y arriba)
    {150, 200}, // Arriaga (Ajustada para origen inferior izquierda, Y arriba)
    {750, 250}, // Las Margaritas (Ajustada para origen inferior izquierda, Y arriba)
    {700, 150}, // Frontera Comalapa (Ajustada para origen inferior izquierda, Y arriba)
    {550, 250}, // Las Rosas (Ajustada para origen inferior izquierda, Y arriba)
    {520, 280}  // Teopisca (Ajustada para origen inferior izquierda, Y arriba)
};

    private final GrafoTDA grafo = new GrafoTDA();

    public GrafoChiapas() {                                 //3n + 156
        inicializarCiudades();      //40
        inicializarGrafo();         //3n+116
    }

    private void inicializarCiudades() {
        ciudades.add(new Vertice("Tuxtla Gutiérrez"));                      //2
        ciudades.add(new Vertice("Tapachula de Córdova y Ordóñez"));        //2
        ciudades.add(new Vertice("San Cristóbal de Las Casas"));            //2
        ciudades.add(new Vertice("Comitán de Domínguez"));                      //2
        ciudades.add(new Vertice("Heroica Chiapa de Corzo"));               //2
        ciudades.add(new Vertice("Palenque"));                              //2
        ciudades.add(new Vertice("Cintalapa de Figueroa"));                 //2
        ciudades.add(new Vertice("Ocosingo"));                              //2
        ciudades.add(new Vertice("Ocozocoautla de Espinosa"));                              //2
        ciudades.add(new Vertice("Tonalá"));                              //2
        ciudades.add(new Vertice("Villaflores"));                              //2
        ciudades.add(new Vertice("Berriozábal"));                              //2
        ciudades.add(new Vertice("Huixtla"));                              //2
        ciudades.add(new Vertice("Reforma"));                              //2
        ciudades.add(new Vertice("Motozintla de Mendoza"));                              //2
        ciudades.add(new Vertice("Arriaga"));                              //2
        ciudades.add(new Vertice("Las Margaritas"));                              //2
        ciudades.add(new Vertice("Frontera Comalapa"));                              //2
        ciudades.add(new Vertice("Las Rosas"));                              //2
        ciudades.add(new Vertice("Teopisca"));                              //2                 
    }

    private void inicializarGrafo() {      //3n + 116
        for (Vertice ciudad : ciudades) {                   //  2n + 2                 
            grafo.agregarVertice(ciudad);       // 1 * n
        }
        Vertice tuxtla = ciudades.get(ciudades.indexOf((new Vertice("Tuxtla Gutiérrez"))));                                               //4          
        Vertice sanCristobal = ciudades.get(ciudades.indexOf((new Vertice("San Cristóbal de Las Casas"))));                               //4
        Vertice ocozocautla = ciudades.get(ciudades.indexOf((new Vertice("Ocozocoautla de Espinosa"))));                               //4
        Vertice chiapa = ciudades.get(ciudades.indexOf((new Vertice("Heroica Chiapa de Corzo"))));                               //4
        Vertice tapachula = ciudades.get(ciudades.indexOf((new Vertice("Tapachula de Córdova y Ordóñez"))));                               //4
        Vertice comitan = ciudades.get(ciudades.indexOf((new Vertice("Comitán de Domínguez"))));                               //4
        Vertice cintalapa = ciudades.get(ciudades.indexOf((new Vertice("Cintalapa de Figueroa"))));                               //4
        Vertice ocosingo = ciudades.get(ciudades.indexOf((new Vertice("Ocosingo"))));                               //4
        Vertice tonala = ciudades.get(ciudades.indexOf((new Vertice("Tonalá"))));                               //4
        Vertice villaflores = ciudades.get(ciudades.indexOf((new Vertice("Villaflores"))));                               //4
        Vertice berriozabal = ciudades.get(ciudades.indexOf((new Vertice("Berriozábal"))));                               //4 
        Vertice huixtla = ciudades.get(ciudades.indexOf((new Vertice("Huixtla"))));                               //4
        Vertice reforma = ciudades.get(ciudades.indexOf((new Vertice("Reforma"))));                               //4
        Vertice motozintla = ciudades.get(ciudades.indexOf((new Vertice("Motozintla de Mendoza"))));                               //4
        Vertice arriaga = ciudades.get(ciudades.indexOf((new Vertice("Arriaga"))));                               //4
        Vertice lasMargaritas = ciudades.get(ciudades.indexOf((new Vertice("Las Margaritas"))));                               //4
        Vertice fronteraComalapa = ciudades.get(ciudades.indexOf((new Vertice("Frontera Comalapa"))));                               //4
        Vertice lasRosas = ciudades.get(ciudades.indexOf((new Vertice("Las Rosas"))));                               //4
        Vertice teopisca = ciudades.get(ciudades.indexOf((new Vertice("Teopisca"))));                               //4
        Vertice palenque = ciudades.get(ciudades.indexOf((new Vertice("Palenque"))));                               //4

        // Tuxtla
        grafo.agregarArista(tuxtla, sanCristobal, 59);                                 //1      
        grafo.agregarArista(tuxtla, ocozocautla, 34.7);                                 //1
        grafo.agregarArista(tuxtla, berriozabal, 24.4);                                 //1
        grafo.agregarArista(tuxtla, villaflores, 90.7);                                 //1
        grafo.agregarArista(tuxtla, tonala, 150);                                 //1
        grafo.agregarArista(tuxtla, arriaga, 130);                                 //1
        grafo.agregarArista(tuxtla, palenque, 385);                                 //1
        grafo.agregarArista(tuxtla, fronteraComalapa, 250);                                 //1
        grafo.agregarArista(tuxtla, motozintla, 388);                                 //1
        grafo.agregarArista(tuxtla, reforma, 213);                                 //1
        grafo.agregarArista(tuxtla, lasRosas, 124);                                 //1
        grafo.agregarArista(tuxtla, chiapa, 15.1);                                 //1

        // San Cristóbal
        grafo.agregarArista(sanCristobal, teopisca, 33.2);                                 //1
        grafo.agregarArista(sanCristobal, ocosingo, 95.6);                                 //1

        // Tapachula
        grafo.agregarArista(tapachula, huixtla, 41.9);                                 //1

        // Comitan
        grafo.agregarArista(comitan, teopisca, 57.8);                                 //1
        grafo.agregarArista(comitan, lasRosas, 37.6);                                 //1
        grafo.agregarArista(comitan, lasMargaritas, 20.5);                                 //1
        grafo.agregarArista(comitan, fronteraComalapa, 96.1);                                 //1
        grafo.agregarArista(comitan, ocosingo, 103);                                 //1

        // Las Rosas
        grafo.agregarArista(lasRosas, teopisca, 27.8);                                 //1

        // Heroica Chiapa de Corzo
        grafo.agregarArista(chiapa, sanCristobal, 53.1);                                 //1

        // Palenque
        grafo.agregarArista(palenque, ocosingo, 118);                                 //1
        grafo.agregarArista(palenque, reforma, 187);                                 //1

        // Cintalapa
        grafo.agregarArista(cintalapa, arriaga, 68.9);                                 //1
        grafo.agregarArista(cintalapa, ocozocautla, 47.5);                                 //1

        // Ocozocautla
        grafo.agregarArista(ocozocautla, berriozabal, 14.5);                                 //1

        // Ocosingo
        grafo.agregarArista(ocosingo, palenque, 119);                                 //1
        grafo.agregarArista(ocosingo, lasMargaritas, 93.6);                                 //1

        // Tonalá
        grafo.agregarArista(tonala, arriaga, 27.4);                                 //1

        // Huixtla
        grafo.agregarArista(huixtla, motozintla, 58.8);                                 //1
        grafo.agregarArista(huixtla, tonala, 183);                                 //1

        // Frontera Comalapa
        grafo.agregarArista(fronteraComalapa, motozintla, 50.2);                                 //1
    }

    public List<Vertice> getCiudades() {
        return ciudades;                                 //1
    }

    public int[][] getCoordenadas() {
        return coordenadas;                                 //1
    }

    public GrafoTDA getGrafo() {
        return grafo;                                 //1
    }
}