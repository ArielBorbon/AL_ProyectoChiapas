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
import java.awt.Dimension;
import java.util.Map;

public class PanelGrafo {

    private static final GrafoChiapas grafoChiapas = new GrafoChiapas(); // Instancia del grafo principal
    public static final String LA_HOJA_DE_ESTILOS = """
    node {
      fill-color: rgb(134,192,160); /* color base */
      size: 30px;
      text-alignment: center;
      text-size: 12px;
    }
    node.gris {
      fill-color: orange;
    }
    node.negro {
      fill-color: gray;
    }
    edge {
      fill-color: rgb(130,130,130);
      text-size: 10px;
    }
    edge.highlighted {
      fill-color: red;
      size: 3px;
    }
""";

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

    /* Metodo Antiguo
    public static JPanel obtenerGrafoPintado2(GrafoTDA grafoPintado) {
        Graph grafoChiapasVisual = crearGrafoChiapas();

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
        // Pintar aristas marcadas por el atributo 'anterior' de cada vértice
        for (Vertice v : grafoPintado.obtenerVertices()) {
            Vertice anterior = v.getAnterior();
            if (anterior != null) {
                String origen = v.getNombre();
                String destino = anterior.getNombre();
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
     */
    public static JPanel createPanelGrafo(Graph grafo) {
        SwingViewer viewer = new SwingViewer(grafo, SwingViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.disableAutoLayout();
        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);
        return viewPanel;
    }

    static Graph crearGrafoChiapas() {
        System.setProperty("org.graphstream.ui", "swing");
        Graph grafo = new SingleGraph("Grafo Chiapas");
        int[][] coordenadas = grafoChiapas.getCoordenadas();
        for (Vertice ciudad : grafoChiapas.getGrafo().obtenerVertices()) {
            Node vertice = grafo.addNode(ciudad.getNombre());
            vertice.setAttribute("ui.label", ciudad.getNombre());
            int indice = grafoChiapas.getCiudades().indexOf(ciudad);
            vertice.setAttribute("xy", coordenadas[indice][0], coordenadas[indice][1]);
        }

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

        grafo.setAttribute("ui.stylesheet", """
        node {
          fill-color: rgb(134,192,160); /* color base */
          size: 30px;
          text-alignment: center;
          text-size: 12px;
        }
        node.gris {
          fill-color: orange;   /* descubierto, aún no procesado */
        }
        node.negro {
          fill-color: gray;     /* procesado */
        }
        edge {
          fill-color: rgb(130,130,130);
          text-size: 10px;
        }
        edge.highlighted {
          fill-color: red;
          size: 3px;
        }
    """);

        return grafo;
    }

    public static JPanel createPanelGrafo(Graph grafo, boolean autoLayout) {

        SwingViewer viewer = new SwingViewer(grafo,
                SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD
        );
        if (autoLayout) {
            viewer.enableAutoLayout();
        } else {
            viewer.disableAutoLayout();
        }

        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);

        viewPanel.setPreferredSize(new Dimension(800, 600));
        viewPanel.setMinimumSize(new Dimension(400, 300));

        return viewPanel;
    }

    public static JPanel obtenerGrafoPintado(GrafoTDA subGrafo) {
        Graph g = crearGrafoChiapas();

        for (Vertice u : subGrafo.obtenerVertices()) {
            for (Arista a : subGrafo.obtenerAdyacentes(u)) {
                String id1 = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();
                Edge e = g.getEdge(id1);
                if (e == null) {
                    e = g.getEdge(a.getDestino().getNombre() + "-" + a.getOrigen().getNombre());
                }
                if (e != null) {
                    e.setAttribute("ui.class", "highlighted");
                }
            }
        }

        for (Vertice v : subGrafo.obtenerVertices()) {
            Node n = g.getNode(v.getNombre());
            n.setAttribute("ui.class", "gris");
        }

        return createPanelGrafo(g, false);
    }

    /**
     * Pinta un grafo base de Chiapas y resalta sólo las aristas y vértices que
     * aparecen en subGrafoRuta. Además, renombra cada nodo con su peso
     * acumulado indicado en distAcumulada.
     *
     * @param subGrafoRuta GrafoTDA que contiene únicamente las aristas del
     * camino.
     * @param distAcumulada Mapa de cada vértice al peso acumulado desde el
     * origen.
     */
    public static JPanel obtenerGrafoPintadoRuta(GrafoTDA subGrafoRuta,
            Map<Vertice, Double> distAcumulada) {
        Graph g = crearGrafoChiapas();

        g.setAttribute("ui.stylesheet", """
            node {
              fill-color: rgb(134,192,160);
              size: 30px;
              text-alignment: center;
              text-size: 12px;
            }
            node.path {
              fill-color: orange;      /* nodo en el camino */
            }
            edge {
              fill-color: rgb(130,130,130);
              text-size: 10px;
            }
            edge.highlighted {
              fill-color: red;         /* arista en el camino */
              size: 3px;
            }
        """);

        for (Vertice u : subGrafoRuta.obtenerVertices()) {
            for (Arista a : subGrafoRuta.obtenerAdyacentes(u)) {
                String id1 = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();
                Edge e = g.getEdge(id1);
                if (e == null) {
                    // capaz y la arista está al reves
                    e = g.getEdge(a.getDestino().getNombre() + "-" + a.getOrigen().getNombre());
                }
                if (e != null) {
                    e.setAttribute("ui.class", "highlighted");
                }
            }
        }

        for (Vertice v : subGrafoRuta.obtenerVertices()) {
            Node n = g.getNode(v.getNombre());
            if (n != null) {
                Double d = distAcumulada.get(v);
                String etiqueta = v.getNombre() + (d != null ? " (" + d.intValue() + ")" : "");
                n.setAttribute("ui.label", etiqueta);
                n.setAttribute("ui.class", "path");
            }
        }

        SwingViewer viewer = new SwingViewer(g, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.disableAutoLayout();
        ViewPanel vp = (ViewPanel) viewer.addDefaultView(false);
        vp.setPreferredSize(new Dimension(800, 600));
        vp.setMinimumSize(new Dimension(400, 300));
        return vp;
    }

}
