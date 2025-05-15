package GUI;

import javax.swing.JPanel;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;

import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class PanelGrafo {

    private static final GrafoChiapas grafoChiapas = new GrafoChiapas(); // Instancia del grafo principal

    /**
     * Método para mostrar el grafo en un panel.
     *
     * Utiliza la librería GraphStream para crear una representación visual del
     * grafo. Si se ha generado un MST, las aristas del árbol se resaltan con un
     * estilo diferente.
     *
     * @return Un JPanel que contiene la visualización del grafo.
     */
    public static JPanel obtenerPanelGrafo() {
        Graph graph = crearGrafoChiapas();
        return createPanelGrafo(graph);
    }

    public static JPanel obtenerGrafoPintado(GrafoTDA grafoPintado) {
        Graph grafoChiapasVisual = crearGrafoChiapas();

        // Agregar aristas al grafo visual
        for (Vertice ciudad : grafoPintado.obtenerVertices()) {
            for (Arista arista : grafoPintado.obtenerAdyacentes(ciudad)) {
                String origen = arista.getOrigen().getNombre();
                String destino = arista.getDestino().getNombre();
                Edge carretera = grafoChiapasVisual.getEdge(origen + "-" + destino);
                if (carretera != null) {
                    carretera.setAttribute("ui.class", "highlighted");
                } else {
                    Edge altCarretera = grafoChiapasVisual.getEdge(destino + "-" + origen);
                    if (altCarretera != null) {
                        altCarretera.setAttribute("ui.class", "highlighted");
                    }
                }

            }
        }

        return createPanelGrafo(grafoChiapasVisual);
    }
    

    public static JPanel createPanelGrafo(Graph grafo) {
        // Crear y devolver el panel de visualizació
        SwingViewer viewer = new SwingViewer(grafo, SwingViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.disableAutoLayout();
        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);
        return viewPanel;
    }

    private static Graph crearGrafoChiapas() {
        System.setProperty("org.graphstream.ui", "swing");
        Graph grafo = new SingleGraph("Grafo Chiapas");
        int[][] coordenadas = grafoChiapas.getCoordenadas(); // Coordenadas de las ciudades
        // Agregar nodos (vértices) al grafo visual
        for (Vertice ciudad : grafoChiapas.getGrafo().obtenerVertices()) {
            Node vertice = grafo.addNode(ciudad.getNombre());
            vertice.setAttribute("ui.label", ciudad.getNombre());
            int indice = grafoChiapas.getCiudades().indexOf(ciudad);
            vertice.setAttribute("xy", coordenadas[indice][0], coordenadas[indice][1]);
        }

        // Agregar aristas al grafo visual
        for (Vertice ciudad : grafoChiapas.getGrafo().obtenerVertices()) {
            for (Arista arista : grafoChiapas.getGrafo().obtenerAdyacentes(ciudad)) {
                String origen = arista.getOrigen().getNombre();
                String destino = arista.getDestino().getNombre();
                if (grafo.getEdge(origen + "-" + destino) == null && grafo.getEdge(destino + "-" + origen) == null) {
                    Edge edge = grafo.addEdge(origen + "-" + destino, origen, destino);
                    edge.setAttribute("ui.label", arista.getDistancia());
                }
            }
        }

        // Aplicar estilos al grafo
        grafo.setAttribute("ui.stylesheet", """
                node {
                    text-size: 10px;
                    fill-color: yellow;
                    size: 40px;
                    text-alignment: center;
                }
                edge {
                    text-size: 12px;
                    fill-color: black;
                }
                edge.highlighted {
                    fill-color: red;
                }
                """);
        return grafo;
    }

}
