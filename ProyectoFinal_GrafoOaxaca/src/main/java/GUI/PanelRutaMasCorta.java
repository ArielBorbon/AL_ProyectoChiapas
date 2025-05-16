package GUI;

import Algoritmos.RutaMasCorta.BellmanFord;
import Algoritmos.RutaMasCorta.Dijkstra;
import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;

public class PanelRutaMasCorta extends JPanel {

    public static final int OPCION_UNA_CIUDAD = 0;
    public static final int OPCION_DOS_CIUDADES = 1;

    private int opcion;

    public PanelRutaMasCorta() {
        initComponents();
        mostrarGrafo();
    }

    private void initComponents() {
        //configuracion del panel
        setLayout(new BorderLayout());
        //Crear botones
        JButton btnBF = Estilos.crearBoton("Usar algoritmo de Bellman-Ford");
        JButton btnDJK = Estilos.crearBoton("Usar algoritmo de Dijkstra");
        JButton btnVolver = Estilos.crearBoton("Volver");
        //panel botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 3, 10, 10));

        panelBotones.add(btnBF, BorderLayout.CENTER);
        panelBotones.add(btnDJK, BorderLayout.CENTER);
        panelBotones.add(btnVolver, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        //Metodo para regresar con el boton volver
        btnVolver.addActionListener(e -> {
            // Obtener el JFrame (ventana) que contiene este panel
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);

            if (ventana instanceof MenuPrincipal) {
                MenuPrincipal menu = (MenuPrincipal) ventana;

                // Limpiar todo lo que hay actualmente en la ventana
                menu.getContentPane().removeAll();

                // Volver a agregar el panel del menú lateral
                menu.add(menu.getPanelMenuLateral(), BorderLayout.WEST);

                // Agregar algún panel de inicio o bienvenida, si lo usas
                menu.add(new PanelDefault(), BorderLayout.CENTER);

                // Actualizar la interfaz gráfica
                menu.revalidate();
                menu.repaint();
            }
        });

        btnDJK.addActionListener(e -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);

            if (ventana instanceof MenuPrincipal) {
                MenuPrincipal menu = (MenuPrincipal) ventana;

                ModalRutaMasCorta modalOpciones = new ModalRutaMasCorta(menu, true);
                modalOpciones.mostrarModal();
                this.opcion = modalOpciones.getOpcion();

                String ciudadOrigen = "";
                String ciudadDestino = "";
                if (this.opcion == OPCION_DOS_CIUDADES) {
                    boolean seleccionValida = false;
                    while (!seleccionValida) {
                        ModalSeleccionarFuente modalSeleccionarFuente = new ModalSeleccionarFuente(menu, true, this.opcion);
                        modalSeleccionarFuente.mostrar();
                        ciudadOrigen = modalSeleccionarFuente.getCiudadOrigen();
                        ciudadDestino = modalSeleccionarFuente.getCiudadDestino();
                        if (ciudadDestino.equals(ciudadOrigen)) {
                            JOptionPane.showMessageDialog(this,
                                    "Por favor, selecciona 2 localidades distintas.",
                                    "Misma ubicación",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            seleccionValida = true;
                        }
                    }
                    pintarRutaDijkstra(ciudadOrigen, ciudadDestino);
                } else if (this.opcion == OPCION_UNA_CIUDAD) {
                    ModalSeleccionarFuente modalSeleccionarFuente = new ModalSeleccionarFuente(menu, true, this.opcion);
                    modalSeleccionarFuente.mostrar();
                    ciudadOrigen = modalSeleccionarFuente.getCiudadOrigen();
                    pintarRutaDijkstraTodas(ciudadOrigen);
                }
            }
        });

        btnBF.addActionListener(e
                -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (!(ventana instanceof MenuPrincipal)) {
                return;
            }
            MenuPrincipal menu = (MenuPrincipal) ventana;

            ModalRutaMasCorta modalOpc = new ModalRutaMasCorta(menu, true);
            modalOpc.mostrarModal();
            this.opcion = modalOpc.getOpcion();

            String ciudadOrigen = "";
            String ciudadDestino = "";
            if (opcion == OPCION_DOS_CIUDADES) {
                boolean ok = false;
                while (!ok) {
                    ModalSeleccionarFuente msf = new ModalSeleccionarFuente(menu, true, opcion);
                    msf.mostrar();
                    ciudadOrigen = msf.getCiudadOrigen();
                    ciudadDestino = msf.getCiudadDestino();
                    if (ciudadOrigen.equals(ciudadDestino)) {
                        JOptionPane.showMessageDialog(this,
                                "Selecciona dos localidades distintas",
                                "Error de selección",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        ok = true;
                    }
                }
                pintarRutaBellmanFord(ciudadOrigen, ciudadDestino);
            } else {
                ModalSeleccionarFuente msf = new ModalSeleccionarFuente(menu, true, opcion);
                msf.mostrar();
                ciudadOrigen = msf.getCiudadOrigen();
                pintarRutaBellmanFordTodas(ciudadOrigen);
            }
        });

    }

    ;
    
    

    private void mostrarGrafo() {
        JPanel panelGrafo = PanelGrafo.obtenerPanelGrafo();

        Component panelCentral = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (panelCentral != null) {
            remove(panelCentral);
        }
        add(panelGrafo, BorderLayout.CENTER);

        revalidate();
        repaint();

    }

    private void pintarRutaDijkstra(String origenNombre, String destinoNombre) {
        new Thread(() -> {
            try {
                GrafoTDA base = new GrafoChiapas().getGrafo();
                Vertice origen = findVertice(base, origenNombre);
                Vertice destino = findVertice(base, destinoNombre);

                Dijkstra.ResultadoPrevia previa = Dijkstra.ejecutarPrevia(base, origen);
                List<Arista> camino = Dijkstra.caminoMasCortoListaAristas(base, origen, destino);

                System.setProperty("org.graphstream.ui", "swing");
                Graph graph = PanelGrafo.crearGrafoChiapas();
                graph.setAttribute("ui.stylesheet", """
                node {
                  fill-color: rgb(134,192,160);
                  size: 30px; text-alignment: center; text-size: 12px;
                }
                node.gris  { fill-color: orange; }
                node.negro { fill-color: gray;   }
                edge { fill-color: rgb(130,130,130); size: 2px; }
                edge.highlighted { fill-color: red; size: 4px; }
            """);

                SwingViewer viewer = new SwingViewer(graph, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
                viewer.disableAutoLayout();
                ViewPanel panel = (ViewPanel) viewer.addDefaultView(false);

                SwingUtilities.invokeLater(() -> {
                    removeCurrentGrafoPanel();
                    add(panel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                });

                Vertice anterior = null;
                for (Arista ar : camino) {
                    graph.getNode(ar.getDestino().getNombre())
                            .setAttribute("ui.class", "gris");

                    String id1 = ar.getOrigen().getNombre() + "-" + ar.getDestino().getNombre();
                    String id2 = ar.getDestino().getNombre() + "-" + ar.getOrigen().getNombre();
                    Edge e = graph.getEdge(id1);
                    if (e == null) {
                        e = graph.getEdge(id2);
                    }

                    if (e != null) {
                        e.setAttribute("ui.class", "highlighted");
                        e.setAttribute("ui.label", ar.getDistancia());
                    } else {
                        System.err.println("Arista no encontrada: " + id1 + "/" + id2);
                    }

                    Thread.sleep(1200);

                    if (anterior != null) {
                        graph.getNode(anterior.getNombre())
                                .setAttribute("ui.class", "negro");
                    }
                    anterior = ar.getDestino();
                }

                if (anterior != null) {
                    graph.getNode(anterior.getNombre())
                            .setAttribute("ui.class", "negro");
                }

                Map<Vertice, Double> distAcum = previa.distancias;
                List<Vertice> secuencia = new ArrayList<>();
                for (Vertice v = destino; v != null; v = previa.previos.get(v)) {
                    secuencia.add(0, v);
                }

                String texto = secuencia.stream()
                        .map(v -> v.getNombre() + " (" + (int) Math.round(distAcum.getOrDefault(v, 0.0)) + ")")
                        .collect(Collectors.joining(" → "));

                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        this,
                        "Ruta " + origenNombre + " → " + destinoNombre + ":\n" + texto,
                        "Dijkstra",
                        JOptionPane.INFORMATION_MESSAGE
                ));

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * Quita el panel central actual antes de añadir el nuevo
     */
    private void removeCurrentGrafoPanel() {
        Component actual = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (actual != null) {
            remove(actual);
        }
    }

    private Vertice findVertice(GrafoTDA grafo, String nombre) {
        return grafo.obtenerVertices().stream()
                .filter(v -> v.getNombre().equals(nombre))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                "No se encontró el vértice con nombre: " + nombre));
    }

    private void pintarRutaDijkstraTodas(String origenNombre) {
        new Thread(() -> {
            try {
                GrafoTDA base = new GrafoChiapas().getGrafo();
                Vertice origen = findVertice(base, origenNombre);

                Dijkstra.ResultadoPrevia res = Dijkstra.ejecutarPrevia(base, origen);
                GrafoTDA subGrafoCompleto = Dijkstra.caminoMasCortoTodas(base, origen);
                Map<Vertice, Double> distAcum = res.distancias;

                System.setProperty("org.graphstream.ui", "swing");
                Graph graph = PanelGrafo.crearGrafoChiapas();
                graph.setAttribute("ui.stylesheet", """
                node {
                  fill-color: rgb(134,192,160);
                  size: 30px; text-alignment: center; text-size: 12px;
                }
                node.gris  { fill-color: orange; }
                node.negro { fill-color: gray;   }
                edge { fill-color: rgb(130,130,130); size: 2px; }
                edge.highlighted { fill-color: red; size: 4px; }
            """);
                SwingViewer viewer = new SwingViewer(graph, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
                viewer.disableAutoLayout();
                ViewPanel panel = (ViewPanel) viewer.addDefaultView(false);
                SwingUtilities.invokeLater(() -> {
                    removeCurrentGrafoPanel();
                    add(panel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                });

                Set<String> vistas = new HashSet<>();
                for (Vertice u : subGrafoCompleto.obtenerVertices()) {
                    for (Arista a : subGrafoCompleto.obtenerAdyacentes(u)) {
                        String id1 = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();
                        String id2 = a.getDestino().getNombre() + "-" + a.getOrigen().getNombre();
                        if (vistas.contains(id1) || vistas.contains(id2)) {
                            continue;
                        }
                        vistas.add(id1);

                        graph.getNode(a.getDestino().getNombre())
                                .setAttribute("ui.class", "gris");
                        Edge e = graph.getEdge(id1);
                        if (e == null) {
                            e = graph.getEdge(id2);
                        }
                        if (e != null) {
                            e.setAttribute("ui.class", "highlighted");
                        }

                        Thread.sleep(1200);
                    }
                }

                StringBuilder sb = new StringBuilder("Caminos mínimos desde " + origenNombre + ":\n");
                List<Vertice> vs = new ArrayList<>(base.obtenerVertices());
                vs.sort(Comparator.comparing(Vertice::getNombre));
                for (Vertice destino : vs) {
                    if (destino.equals(origen)) {
                        continue;
                    }
                    List<String> pasos = new ArrayList<>();
                    for (Vertice v = destino; v != null; v = res.previos.get(v)) {
                        pasos.add(0, v.getNombre() + " (" + (int) Math.round(distAcum.getOrDefault(v, 0.0)) + ")");
                    }
                    sb.append("• ").append(String.join(" → ", pasos)).append("\n");
                }
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        this, sb.toString(),
                        "Dijkstra — todas", JOptionPane.INFORMATION_MESSAGE
                ));

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void pintarRutaBellmanFord(String origenNombre, String destinoNombre) {
        new Thread(() -> {
            try {
                GrafoTDA base = new GrafoChiapas().getGrafo();
                Vertice origen = findVertice(base, origenNombre);
                Vertice destino = findVertice(base, destinoNombre);

                BellmanFord.Resultado res = BellmanFord.ejecutar(base, origen);
                List<Arista> camino = BellmanFord.reconstruirCamino(res, destino);

                System.setProperty("org.graphstream.ui", "swing");
                Graph graph = PanelGrafo.crearGrafoChiapas();  
                graph.setAttribute("ui.stylesheet", """
                node {
                  fill-color: rgb(134,192,160);
                  size: 30px;
                  text-alignment: center;
                  text-size: 12px;
                }
                node.gris  { fill-color: orange; }
                node.negro { fill-color: gray;   }
                edge {
                  fill-color: rgb(130,130,130);
                  size: 2px;
                }
                edge.highlighted {
                  fill-color: red;
                  size: 4px;
                }
            """);

                SwingViewer viewer = new SwingViewer(graph, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
                viewer.disableAutoLayout();
                ViewPanel panel = (ViewPanel) viewer.addDefaultView(false);

                SwingUtilities.invokeLater(() -> {
                    removeCurrentGrafoPanel();
                    add(panel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                });

                Vertice anterior = null;

                for (Arista ar : camino) {

                    Node nodoDestino = graph.getNode(ar.getDestino().getNombre());
                    nodoDestino.setAttribute("ui.class", "gris");

                    String id1 = ar.getOrigen().getNombre() + "-" + ar.getDestino().getNombre();
                    String id2 = ar.getDestino().getNombre() + "-" + ar.getOrigen().getNombre();
                    Edge e = graph.getEdge(id1);
                    if (e == null) {
                        e = graph.getEdge(id2);
                    }
                    if (e == null) {
                        e = graph.addEdge(id1,
                                ar.getOrigen().getNombre(),
                                ar.getDestino().getNombre(),
                                false);
                    }

                    e.setAttribute("ui.class", "highlighted");
                    e.setAttribute("ui.label", ar.getDistancia());

                    Thread.sleep(1200);

                    if (anterior != null) {
                        graph.getNode(anterior.getNombre()).setAttribute("ui.class", "negro");
                    }
                    anterior = ar.getDestino();
                }

                if (anterior != null) {
                    graph.getNode(anterior.getNombre()).setAttribute("ui.class", "negro");
                }

                List<Vertice> secuencia = new ArrayList<>();
                for (Vertice v = destino; v != null; v = res.previos.get(v)) {
                    secuencia.add(0, v);
                }
                String texto = secuencia.stream()
                        .map(v -> v.getNombre() + " (" + (int) Math.round(res.distancias.get(v)) + ")")
                        .collect(Collectors.joining(" → "));

                SwingUtilities.invokeLater(()
                        -> JOptionPane.showMessageDialog(this,
                                "Ruta " + origenNombre + " → " + destinoNombre + ":\n"
                                + texto,
                                "Bellman–Ford",
                                JOptionPane.INFORMATION_MESSAGE)
                );

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void pintarRutaBellmanFordTodas(String origenNombre) {
        new Thread(() -> {
            try {
                GrafoTDA base = new GrafoChiapas().getGrafo();
                Vertice origen = findVertice(base, origenNombre);

                BellmanFord.Resultado res = BellmanFord.ejecutar(base, origen);

                GrafoTDA subGrafoCompleto = BellmanFord.caminoMasCortoTodas(base, origen);
                Map<Vertice, Double> distAcum = res.distancias;

                System.setProperty("org.graphstream.ui", "swing");
                Graph graph = PanelGrafo.crearGrafoChiapas();  

                graph.setAttribute("ui.stylesheet", """
                node {
                  fill-color: rgb(134,192,160);
                  size: 30px;
                  text-alignment: center;
                  text-size: 12px;
                }
                node.gris  { fill-color: orange; }
                node.negro { fill-color: gray;   }
                edge {
                  fill-color: rgb(130,130,130);
                  size: 2px;
                }
                edge.highlighted {
                  fill-color: red;
                  size: 4px;
                }
            """);

                SwingViewer viewer = new SwingViewer(graph, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
                viewer.disableAutoLayout();
                ViewPanel panel = (ViewPanel) viewer.addDefaultView(false);

                SwingUtilities.invokeLater(() -> {
                    removeCurrentGrafoPanel();
                    add(panel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                });

                Set<String> aristasVistas = new HashSet<>();
                List<Arista> todasAristas = new ArrayList<>();
                for (Vertice v : subGrafoCompleto.obtenerVertices()) {
                    for (Arista a : subGrafoCompleto.obtenerAdyacentes(v)) {
                        String clave1 = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();
                        String clave2 = a.getDestino().getNombre() + "-" + a.getOrigen().getNombre();
                        if (!aristasVistas.contains(clave1) && !aristasVistas.contains(clave2)) {
                            aristasVistas.add(clave1);
                            todasAristas.add(a);
                        }
                    }
                }

                Set<Vertice> visitados = new HashSet<>();

                for (Arista a : todasAristas) {
                   
                    if (!visitados.contains(a.getOrigen())) {
                        graph.getNode(a.getOrigen().getNombre()).setAttribute("ui.class", "gris");
                        visitados.add(a.getOrigen());
                    }
                    if (!visitados.contains(a.getDestino())) {
                        graph.getNode(a.getDestino().getNombre()).setAttribute("ui.class", "gris");
                        visitados.add(a.getDestino());
                    }

                 
                    String id1 = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();
                    String id2 = a.getDestino().getNombre() + "-" + a.getOrigen().getNombre();
                    Edge e = graph.getEdge(id1);
                    if (e == null) {
                        e = graph.getEdge(id2);
                    }
                    if (e == null) {
                        e = graph.addEdge(id1,
                                a.getOrigen().getNombre(),
                                a.getDestino().getNombre(),
                                false);
                    }

                    e.setAttribute("ui.class", "highlighted");
                    e.setAttribute("ui.label", a.getDistancia());

                    Thread.sleep(1200);
                }

                for (Vertice v : visitados) {
                    graph.getNode(v.getNombre()).setAttribute("ui.class", "negro");
                }

                
                StringBuilder sb = new StringBuilder("Caminos mínimos desde " + origenNombre + ":\n");
                List<Vertice> vs = new ArrayList<>(base.obtenerVertices());
                vs.sort(Comparator.comparing(Vertice::getNombre));

                for (Vertice destino : vs) {
                    if (destino.equals(origen)) {
                        continue;
                    }
                    List<String> pasos = new ArrayList<>();
                    Vertice v = destino;
                    while (v != null) {
                        pasos.add(0, v.getNombre() + " (" + (int) Math.round(distAcum.get(v)) + ")");
                        v = res.previos.get(v);
                    }
                    sb.append("• ").append(String.join(" → ", pasos)).append("\n");
                }

                SwingUtilities.invokeLater(()
                        -> JOptionPane.showMessageDialog(this,
                                sb.toString(),
                                "Bellman–Ford — todas",
                                JOptionPane.INFORMATION_MESSAGE)
                );

            } catch (Exception ex) {
                SwingUtilities.invokeLater(()
                        -> JOptionPane.showMessageDialog(this,
                                ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE)
                );
            }
        }).start();
    }

}
