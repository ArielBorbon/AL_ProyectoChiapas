package Algoritmos.MST;

import java.util.Collection;

import Implementacion.Arista;
import Implementacion.GrafoTDA;

public interface BoruvkaListener {
    void onSeleccionAristas(Collection<Arista> seleccionadas);
    void onActualizaMST(GrafoTDA mstActual);
}
