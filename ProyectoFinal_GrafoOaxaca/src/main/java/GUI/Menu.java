package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import Implementacion.GrafoTDA;

/**
 *
 * Clase que representa una interfaz gráfica para interactuar con un grafo de
 * ciudades del estado de Oaxaca. Permite visualizar el grafo, generar el árbol
 * de expansión mínima (MST) y encontrar rutas más cortas entre dos ciudades.
 *
 * Funciona en conjunto con la clase Grafo, que contiene la lógica de
 * representación y manipulación del grafo. Utiliza la librería GraphStream para
 * la visualización del grafo.
 *
 * @author Garcia Acosta Alicia Denise 00000252402
 * @author
 * @author
 * 
 */
public class Menu extends JFrame {

    private final GrafoTDA grafo; // Instancia del grafo principal
    private GrafoTDA mst; // Grafo del árbol de expansión mínima (MST)
    private GrafoTDA dijkstra; //Grafo del camino más corto
    private final String[] ciudades = { // Lista de nombres de las ciudades representadas como vértices
        "Oaxaca de Juárez", "San Juan Bautista Tuxtepec", "Juchitán de Zaragoza",
        "Santa Cruz Xoxocotlán", "Salina Cruz", "Huajuapan de León",
        "Santa Lucía del Camino", "Santo Domingo Tehuantepec",
        "Santiago Pinotepa Nacional", "Loma Bonita", "Puerto Escondido",
        "Miahuatlán de Porfirio Díaz", "Ciudad Ixtepec", "San Antonio de la Cal",
        "Santa María Atzompa", "Crucecita", "Tlaxiaco",
        "Matías Romero Avendaño", "San Jacinto Amilpas", "Villa de Zaachila"
    };
    private final int[][] vertices = { // Coordenadas de las ciudades para visualización
        {15, 15}, {20, 25}, {30, 16}, {13, 10}, {30, 7},
        {3, 28}, {18, 14}, {28, 10}, {2, 5}, {23, 28},
        {10, 0}, {17, 7}, {27, 14}, {16, 11}, {9, 16},
        {20, 0}, {0, 16}, {30, 20}, {10, 20}, {12, 7}
    };
    private JPanel panelCentral;

    /**
     * Constructor de la clase Menu.
     *
     * @param grafo Grafo principal que se visualizará e interactuará en la
     * interfaz.
     */
    public Menu(GrafoTDA grafo) {
        this.grafo = grafo;
        this.mst = null; // Inicialmente no hay un MST generado
        this.dijkstra = null; //Inicialmente no hay un camino mas corto generado
        this.setTitle("Mapa de Oaxaca");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());

        // Panel de botones con las opciones disponibles
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        JButton mostrarGrafoBtn = new JButton("Mostrar grafo");
        JButton arbolMinimoBtn = new JButton("Árbol de esparcimiento mínimo");
        JButton rutaCortaBtn = new JButton("Ruta más corta entre dos ciudades");
        //Botones de seleccion de algoritmos de busqueda
        JButton btnAlgoBusqueda = new JButton("Seleccione del algoritmo de busqueda");
        JButton btnAlgoAnchura = new JButton("Anchura");
        JButton btnAlgoProfundidad = new JButton("Profundidad");
        //Botones de seleccion de algoritmos de MST
        JButton btnAlgoMST = new JButton("Seleccione el algoritmo de MST");
        JButton btnAlgoBoruvka = new JButton("Boruvka");
        JButton btnAlgoPrim = new JButton("Prim");
        JButton btnAlgoKruskal = new JButton("Kruskal");
        //Boton de seleccion de algoritmo de ruta mas corta
        JButton btnAlgoRutaCorta = new JButton("Seleccione el algoritmo de ruta corta");
        JButton btnAlgoBellman = new JButton("Bellman-Ford");
        JButton btnAlgoDijkstra = new JButton("Dijkstra");


        panelBotones.add(mostrarGrafoBtn);
        panelBotones.add(arbolMinimoBtn);
        panelBotones.add(rutaCortaBtn);

        // Acción para mostrar el grafo original
        mostrarGrafoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mst = null; // Se elimina el MST previo
                dijkstra = null; //Se elimina el camino mas corto generado
                actualizarPanelGrafo(panelBotones);
            }
        });

        // Acción para generar y mostrar el árbol de expansión mínima (MST)
        arbolMinimoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dijkstra = null; // Se elimina el camino mas corto generado
                mst = new GrafoTDA(grafo.arbolEsparcimientoMinimo());
                actualizarPanelGrafo(panelBotones);
            }
        });

        /**
         * Boton para mostrar la ruta mas corta entre dos ciudades.
         * Pide dos ciudades al usuario (origen y destino) y muestra la ruta 
         * mas corta en verde
         */
        rutaCortaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mst = null; // Se elimina el arbol MST previo
                String origen = JOptionPane.showInputDialog("Ingrese el nombre de la ciudad de origen");
                String destino = JOptionPane.showInputDialog("Ingrese el nombre de la ciudad de destino");
                if (!origen.equals(destino)) {
                    int idOrigen = -1;
                    int idDestino = -1;
                    for (int i = 0; i < ciudades.length; i++) {
                        if (ciudades[i].equals(origen)) {
                            idOrigen = i + 1;
                        } else if (ciudades[i].equals(destino)){
                            idDestino = i + 1;
                        }
                    }
                    if (idOrigen == -1) {
                        JOptionPane.showMessageDialog(null, "No se encontró a la ciudad de origen",
                            "No Encontrado", JOptionPane.ERROR_MESSAGE);
                    } else if (idDestino == -1){
                        JOptionPane.showMessageDialog(null, "No se encontró a la ciudad de destino",
                            "No Encontrado", JOptionPane.ERROR_MESSAGE);
                    } else{
                        dijkstra = new GrafoTDA(grafo.caminoMasCorto(idOrigen, idDestino));
                        actualizarPanelGrafo(panelBotones);
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(null, "No puede ingresar la misma ciudad",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                

            }
        });

        actualizarPanelGrafo(panelBotones);
    }
    /**
     * Actualiza EL grafo que se está mostrando, elimina todos los componentes
     * del frame y vuelve a agregarlos con el grafo actualizado
     * @param panelBotones 
     */
    private void actualizarPanelGrafo(JPanel panelBotones) {
        this.getContentPane().removeAll();
        this.add(panelBotones, BorderLayout.SOUTH);
        this.add(mostrarGrafo(), BorderLayout.CENTER);
        this.revalidate();
    }

    /**
     * Método para mostrar el grafo en un panel.
     *
     * Utiliza la librería GraphStream para crear una representación visual del
     * grafo. Si se ha generado un MST, las aristas del árbol se resaltan con un
     * estilo diferente.
     *
     * @return Un JPanel que contiene la visualización del grafo.
     */
    private JPanel mostrarGrafo() {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("grafo");

        // Agregar nodos (vértices) al grafo visual
        for (Integer idCiudad : grafo.obtenerVertices()) {
            Node vertice = graph.addNode(idCiudad.toString());
            vertice.setAttribute("ui.label", ciudades[idCiudad - 1]);
            vertice.setAttribute("xy", vertices[idCiudad - 1][0], vertices[idCiudad - 1][1]);
        }

        // Agregar aristas al grafo visual
        for (Integer idCiudad : grafo.obtenerVertices()) {
            String ciudad = idCiudad.toString();
            for (GrafoTDA.Arista arista : grafo.obtenerAdyacentes(idCiudad)) {
                Integer idDestino = arista.getDestino();
                String destino = idDestino.toString();
                if (graph.getEdge(ciudad + "-" + destino) == null && graph.getEdge(destino + "-" + ciudad) == null) {
                    Edge edge = graph.addEdge(ciudad + "-" + destino, ciudad, destino);
                    edge.setAttribute("ui.label", arista.getDistancia());
                    if (mst != null) {
                        for (GrafoTDA.Arista aristaMst : mst.obtenerAdyacentes(idCiudad)) {
                            if (arista.equals(aristaMst)) {
                                edge.setAttribute("ui.class", "mst");
                                break;
                            }
                        }
                    }
                    if (dijkstra != null) {
                        for (GrafoTDA.Arista aristaDijkstra : dijkstra.obtenerAdyacentes(idCiudad)) {
                            double distanciaAristaOG = Math.round(arista.getDistancia() * 100.0) / 100.0;
                            double distanciaAristaDijkstra = Math.round(aristaDijkstra.getDistancia() * 100.0) / 100.0;

                            if (arista.getOrigen() == aristaDijkstra.getOrigen()
                                    && arista.getDestino() == aristaDijkstra.getDestino()
                                    && distanciaAristaOG == distanciaAristaDijkstra) {
                                edge.setAttribute("ui.class", "dijkstra");
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Aplicar estilos al grafo
        graph.setAttribute("ui.stylesheet", """
            node {
                text-size: 10px;
                fill-color: yellow;
                size: 50px;
                text-alignment: center;
            }
            edge {
                text-size: 12px;
                fill-color: gray;
            }
            edge.mst {
                fill-color: red;
                size: 3px;
            }
            edge.dijkstra {
                fill-color: green;
                size: 3px;
            }""");

        // Crear y devolver el panel de visualizació
        Viewer viewer = graph.display(false);
        viewer.disableAutoLayout();
        View view = viewer.getDefaultView();
        view.openInAFrame(false);
        return (JPanel) view;
    }
}
