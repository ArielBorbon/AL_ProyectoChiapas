package GUI;

import javax.swing.JPanel;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;

import Implementacion.Arista;
import Implementacion.ColorVertice;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;
import java.awt.Dimension;
import java.util.Map;

public class PanelGrafo {                           //                                  T(n) =  119n"2 + 94n + 95         O(n"2)

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
    public static JPanel obtenerPanelGrafo() {                                                  //                                  19n"2 + 18n + 18
        Graph graph = crearGrafoChiapas();  // 19n"2    +   18n   + 10
        return createPanelGrafo(graph);         //8
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
    public static JPanel createPanelGrafo(Graph grafo) {                                                                                                                //7
        SwingViewer viewer = new SwingViewer(grafo, SwingViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);      //3
        viewer.disableAutoLayout();                                         //1
        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);         //2
        return viewPanel;                  //1
    }

    static Graph crearGrafoChiapas() {          //                                                                                                                19n"2    +   18n   + 9
        System.setProperty("org.graphstream.ui", "swing");                  //1
        Graph grafo = new SingleGraph("Grafo Chiapas");                  //2
        int[][] coordenadas = grafoChiapas.getCoordenadas();                  //2
        for (Vertice ciudad : grafoChiapas.getGrafo().obtenerVertices()) {                  //3n +1
            Node vertice = grafo.addNode(ciudad.getNombre());                  //3n
            vertice.setAttribute("ui.label", ciudad.getNombre());                  //2n
            int indice = grafoChiapas.getCiudades().indexOf(ciudad);                  //3n
            vertice.setAttribute("xy", coordenadas[indice][0], coordenadas[indice][1]);                  //3n
        }

        for (Vertice ciudad : grafoChiapas.getGrafo().obtenerVertices()) {                  //3n+1
            for (Arista arista : grafoChiapas.getGrafo().obtenerAdyacentes(ciudad)) {                  //(3n+1)*n
                String origen = arista.getOrigen().getNombre();                  //3n*n
                String destino = arista.getDestino().getNombre();                  //3n*n
                if (grafo.getEdge(origen + "-" + destino) == null && grafo.getEdge(destino + "-" + origen) == null) {                  //6n*n
                    Edge edge = grafo.addEdge(origen + "-" + destino, origen, destino);                  //4n*n
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
    """);//1

        return grafo;                  //1
    }

    public static JPanel createPanelGrafo(Graph grafo, boolean autoLayout) {                                                                               //13

        SwingViewer viewer = new SwingViewer(grafo,
                SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD                  //3
        );                                  
        if (autoLayout) {                  //1
            viewer.enableAutoLayout();                  //1
        } else {
            viewer.disableAutoLayout();                  //1
        }

        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);                  //2

        viewPanel.setPreferredSize(new Dimension(800, 600));                  //2
        viewPanel.setMinimumSize(new Dimension(400, 300));                  //2

        return viewPanel;                  //1
    }

    public static JPanel obtenerGrafoPintado(GrafoTDA subGrafo) {                                                                               // 41n"2 + 27n + 25
        Graph g = crearGrafoChiapas();      // 19n"2    +   18n   + 9   

        for (Vertice u : subGrafo.obtenerVertices()) {                                        //2n +1 
            for (Arista a : subGrafo.obtenerAdyacentes(u)) {                                      //(2n +1 )*n
                String id1 = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();                  //7n*n
                Edge e = g.getEdge(id1);                //2n*n
                if (e == null) {                                //n*n
                    e = g.getEdge(a.getDestino().getNombre() + "-" + a.getOrigen().getNombre());                  //8n*n
                }
                if (e != null) {                  //n*n
                    e.setAttribute("ui.class", "highlighted");                  //n*n
                }
            }
        }

        for (Vertice v : subGrafo.obtenerVertices()) {                  //2n +1
            Node n = g.getNode(v.getNombre());                  //3n
            n.setAttribute("ui.class", "gris");                   //n
        }

        return createPanelGrafo(g, false);                  //14
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
    public static JPanel obtenerGrafoPintadoRuta(GrafoTDA subGrafoRuta, //                          41n"2 + 40n + 24                                                         
            Map<Vertice, Double> distAcumulada) {
        Graph g = crearGrafoChiapas();      // 19n"2    +   18n   + 9

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
        """);                  //1

        for (Vertice u : subGrafoRuta.obtenerVertices()) {                  //2n +1 
            for (Arista a : subGrafoRuta.obtenerAdyacentes(u)) {                  //(2n +1)*n
                String id1 = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();                  //7n*n
                Edge e = g.getEdge(id1);                  //2n*n
                if (e == null) {                  //1n*n
                    e = g.getEdge(a.getDestino().getNombre() + "-" + a.getOrigen().getNombre());                  //8n*n
                }
                if (e != null) {                  //1n*n
                    e.setAttribute("ui.class", "highlighted");                  //n*n
                }
            }
        }

        for (Vertice v : subGrafoRuta.obtenerVertices()) {                            //2n +1
            Node n = g.getNode(v.getNombre());                  //3n
            if (n != null) {                                    //n
                Double d = distAcumulada.get(v);                  //2n
                String etiqueta = v.getNombre() + (d != null ? " (" + d.intValue() + ")" : "");                  //9n
                n.setAttribute("ui.label", etiqueta);                   //n
                n.setAttribute("ui.class", "path");                     //n
            }
        }

        SwingViewer viewer = new SwingViewer(g, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);                      //3
        viewer.disableAutoLayout();                                                                                   //1
        ViewPanel vp = (ViewPanel) viewer.addDefaultView(false);                  //3
        vp.setPreferredSize(new Dimension(800, 600));                  //2
        vp.setMinimumSize(new Dimension(400, 300));                  //2
        return vp;                  //1
    }

    
        /**
     * Pinta el grafo base de Chiapas: colorea cada nodo según el mapa de color,
     * y resalta las aristas que estén en subGrafoRecorrido (árbol de BFS/DFS).
     *
     * @param colorMap        Mapa de cada vértice a su ColorVertice (BLANCO/GRIS/NEGRO)
     * @param subGrafoRecorrido GrafoTDA con las aristas del árbol de recorrido
     */
    public static JPanel obtenerGrafoPintadoRecorrido(                                                               //                                    40n"2 + 31n + 23
            Map<Vertice, ColorVertice> colorMap,
            GrafoTDA subGrafoRecorrido) {

        Graph g = crearGrafoChiapas();      // 19n"2    +   18n   + 9

        g.setAttribute("ui.stylesheet", """
            node {
              fill-color: rgb(134,192,160);
              size: 30px;
              text-alignment: center;
              text-size: 12px;
            }
            node.blanco { fill-color: rgb(134,192,160); }
            node.gris   { fill-color: orange; }
            node.negro  { fill-color: gray; }
            edge { fill-color: rgb(130,130,130); size: 2px; }
            edge.highlighted { fill-color: red; size: 3px; }
        """);                  //1

        for (Vertice u : subGrafoRecorrido.obtenerVertices()) {                  //2n+1
            for (Arista a : subGrafoRecorrido.obtenerAdyacentes(u)) {                  //(2n+1)*n
                String id1 = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();                  //7n*n
                Edge e = g.getEdge(id1);                         //2n*n
                if (e == null) {                  //n*n
                    e = g.getEdge(a.getDestino().getNombre() + "-" + a.getOrigen().getNombre());                  //7n*n
                }
                if (e != null) {                  //n*n
                    e.setAttribute("ui.class", "highlighted");                  //n*n
                }
            }
        }

        for (Map.Entry<Vertice, ColorVertice> entry : colorMap.entrySet()) {                      //2n+1
            Vertice v = entry.getKey();                  //2n
            ColorVertice cv = entry.getValue();                  //2n
            Node n = g.getNode(v.getNombre());                  //3n
            if (n != null) {                  //n
                // asignamos blanco/gris/negro
                n.setAttribute("ui.class", cv.name().toLowerCase());                  //3n
            }
        }

        SwingViewer viewer = new SwingViewer(g,
            SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);                  //2
        viewer.disableAutoLayout();                  //1
        ViewPanel vp = (ViewPanel) viewer.addDefaultView(false);                  //3
        vp.setPreferredSize(new Dimension(800, 600));                  //2
        vp.setMinimumSize(new Dimension(400, 300));                  //2
        return vp;                  //1
    }
    
    
}
