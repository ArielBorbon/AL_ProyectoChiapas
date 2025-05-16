package GUI;

import Algoritmos.Busqueda.BFS;
import Algoritmos.Busqueda.DFS;
import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.*;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;

public class PanelRecorrer extends JPanel {

    public PanelRecorrer() {
        initComponents();
        mostrarGrafo();
    }

    private void initComponents() {
        //configuracion del panel
        setLayout(new BorderLayout());
        //creacion de los botones
        JButton btnBFS = Estilos.crearBoton("Usar busqueda en anchura-BFS");
        JButton btnDFS = Estilos.crearBoton("Usar busqueda en profundidad-DFS");
        JButton btnVolver = Estilos.crearBoton("Volver");
        //creacion del panel de los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 3, 10, 10));
        panelBotones.add(btnBFS, BorderLayout.CENTER);
        panelBotones.add(btnDFS, BorderLayout.CENTER);
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

        btnBFS.addActionListener(e -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (!(ventana instanceof MenuPrincipal)) {
                return;
            }
            ModalSeleccionarFuente msf = new ModalSeleccionarFuente(
                    (MenuPrincipal) ventana,
                    true,
                    ModalSeleccionarFuente.ORIGEN
            );
            msf.mostrar();
            String nombre = msf.getCiudadOrigen();
            if (nombre != null && !nombre.isBlank()) {
                pintarRecorridoBFS(nombre);
            }
        });

        btnDFS.addActionListener(e -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (!(ventana instanceof MenuPrincipal)) {
                return;
            }
            ModalSeleccionarFuente msf = new ModalSeleccionarFuente(
                    (MenuPrincipal) ventana,
                    true,
                    ModalSeleccionarFuente.ORIGEN
            );
            msf.mostrar();
            String nombre = msf.getCiudadOrigen();
            if (nombre != null && !nombre.isBlank()) {
                pintarRecorridoDFS(nombre);
            }
        });
    }

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

    private void removeCurrentGrafoPanel() {
        Component actual = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (actual != null) {
            remove(actual);
        }
    }

    private void pintarRecorridoBFS(String semillaNombre) {
        new Thread(() -> {
            try {
                int c1 = 1;
                GrafoTDA base = new GrafoChiapas().getGrafo();

                Vertice semilla = base.obtenerVertices().stream()
                        .filter(v -> v.getNombre().equals(semillaNombre))
                        .findFirst().orElseThrow();

                BFS.Resultado res = BFS.ejecutar(base, semilla);

                System.setProperty("org.graphstream.ui", "swing");
                Graph graph = PanelGrafo.crearGrafoChiapas();
                graph.setAttribute("ui.stylesheet", """
    node {
       fill-color: rgb(134,192,160);
          size: 30px;
          text-alignment: center; text-size: 12px;
        }
        node.gris { fill-color: orange; }
        node.negro { fill-color: gray; }
        edge { fill-color: rgb(130,130,130); text-size: 10px; }
        edge.highlighted { fill-color: red; size: 3px; }
    """);

                SwingViewer viewer = new SwingViewer(graph, SwingViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
                viewer.disableAutoLayout();
                ViewPanel view = (ViewPanel) viewer.addDefaultView(false);

                SwingUtilities.invokeLater(() -> {
                    removeCurrentGrafoPanel();
                    add(view, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                });

                for (int i = 1; i < res.orden.size(); i++) {
                    Vertice v = res.orden.get(i);

                    Vertice padre = null;
                    double peso = 0;
                    for (var entry : res.arbol.entrySet()) {
                        for (Arista a : entry.getValue()) {
                            if (a.getDestino().equals(v)) {
                                padre = entry.getKey();
                                peso = a.getDistancia();
                                break;
                            }
                        }
                        if (padre != null) {
                            break;
                        }
                    }
                    if (padre == null) {
                        continue;
                    }

                    Node nodoV = graph.getNode(v.getNombre());
                    nodoV.setAttribute("ui.class", "gris");

                    String id1 = padre.getNombre() + "-" + v.getNombre();
                    String id2 = v.getNombre() + "-" + padre.getNombre();
                    Edge e = graph.getEdge(id1);
                    if (e == null) {
                        e = graph.getEdge(id2);
                    }
                    if (e != null) {
                        e.setAttribute("ui.class", "highlighted");
                        e.setAttribute("ui.label", peso);
                    }

                    Thread.sleep(500);

                    c1++;

                    Vertice anterior = res.orden.get(i - 1);
                    graph.getNode(anterior.getNombre()).setAttribute("ui.class", "negro");
                }

                Vertice anterior = res.orden.get(c1 - 1);
                graph.getNode(anterior.getNombre()).setAttribute("ui.class", "negro");

                graph.getNode(semillaNombre).setAttribute("ui.class", "negro");

                StringBuilder sb = new StringBuilder();
                sb.append("BFS desde ").append(semillaNombre).append("\n\n")
                        .append("Orden de descubrimiento:\n")
                        .append(res.orden.stream()
                                .map(Vertice::getNombre)
                                .collect(Collectors.joining(" → ")))
                        .append("\n\nNiveles:\n");
                res.nivel.entrySet().stream()
                        .sorted(Comparator.comparing(e -> e.getKey().getNombre()))
                        .forEach(en -> sb.append(en.getKey().getNombre())
                        .append(": ").append(en.getValue())
                        .append("\n"));

                SwingUtilities.invokeLater(()
                        -> JOptionPane.showMessageDialog(this,
                                sb.toString(),
                                "Resultado BFS",
                                JOptionPane.INFORMATION_MESSAGE
                        )
                );
                graph.getNode(semillaNombre).setAttribute("ui.class", "negro");

            } catch (InterruptedException | NoSuchElementException ex) {
                SwingUtilities.invokeLater(()
                        -> JOptionPane.showMessageDialog(this,
                                "Error: " + ex.getMessage(),
                                "BFS",
                                JOptionPane.ERROR_MESSAGE
                        )
                );
            }
        }).start();
    }

    private void pintarRecorridoDFS(String semillaNombre) {
        new Thread(() -> {
            try {
                GrafoTDA base = new GrafoChiapas().getGrafo();
                int c1 = 1;
                Vertice semilla = base.obtenerVertices().stream()
                        .filter(v -> v.getNombre().equals(semillaNombre))
                        .findFirst().orElseThrow();

                DFS.Resultado res = DFS.ejecutar(base, semilla);

                System.setProperty("org.graphstream.ui", "swing");
                Graph graph = PanelGrafo.crearGrafoChiapas();
                graph.setAttribute("ui.stylesheet", """
                node {
                  fill-color: rgb(134,192,160);
                  size: 30px;
                  text-alignment: center;
                  text-size: 12px;
                }
                node.gris { fill-color: orange; }
                node.negro { fill-color: gray; }
                edge {
                  fill-color: rgb(130,130,130);
                  text-size: 10px;
                }
                edge.highlighted {
                  fill-color: red;
                  size: 3px;
                }
            """);

                SwingViewer viewer = new SwingViewer(graph,
                        SwingViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
                viewer.disableAutoLayout();
                ViewPanel view = (ViewPanel) viewer.addDefaultView(false);

                SwingUtilities.invokeLater(() -> {
                    removeCurrentGrafoPanel();
                    add(view, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                });

                for (int i = 1; i < res.orden.size(); i++) {
                    Vertice v = res.orden.get(i);

                    Vertice padre = null;
                    double peso = 0;
                    for (Map.Entry<Vertice, List<Arista>> entry : res.arbol.entrySet()) {
                        for (Arista a : entry.getValue()) {
                            if (a.getDestino().equals(v)) {
                                padre = entry.getKey();
                                peso = a.getDistancia();
                                break;
                            }
                        }
                        if (padre != null) {
                            break;
                        }
                    }
                    if (padre == null) {
                        continue;
                    }

                    graph.getNode(v.getNombre()).setAttribute("ui.class", "gris");

                    String id1 = padre.getNombre() + "-" + v.getNombre();
                    String id2 = v.getNombre() + "-" + padre.getNombre();
                    Edge e = graph.getEdge(id1);
                    if (e == null) {
                        e = graph.getEdge(id2);
                    }
                    if (e != null) {
                        e.setAttribute("ui.class", "highlighted");
                    }

                    Thread.sleep(500);

                    Vertice anterior = res.orden.get(i - 1);
                    graph.getNode(anterior.getNombre()).setAttribute("ui.class", "negro");
                    c1++;
                }

                Vertice anterior = res.orden.get(c1 - 1);
                graph.getNode(anterior.getNombre()).setAttribute("ui.class", "negro");

                graph.getNode(semillaNombre).setAttribute("ui.class", "negro");

                String resultado = "DFS desde " + semillaNombre + "\n\n"
                        + "Orden de descubrimiento:\n"
                        + res.orden.stream()
                                .map(Vertice::getNombre)
                                .collect(Collectors.joining(" → "));
                SwingUtilities.invokeLater(()
                        -> JOptionPane.showMessageDialog(this,
                                resultado,
                                "Resultado DFS",
                                JOptionPane.INFORMATION_MESSAGE
                        )
                );

            } catch (InterruptedException | NoSuchElementException ex) {
                SwingUtilities.invokeLater(()
                        -> JOptionPane.showMessageDialog(this,
                                "Error: " + ex.getMessage(),
                                "DFS",
                                JOptionPane.ERROR_MESSAGE
                        )
                );
            }
        }).start();
    }

}
