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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
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
                GrafoTDA base = new GrafoChiapas().getGrafo();
                Vertice semilla = base.obtenerVertices().stream()
                        .filter(v -> v.getNombre().equals(semillaNombre))
                        .findFirst().orElseThrow();

                BFS.Resultado res = BFS.ejecutarBFS(base, semilla);

           
                GrafoTDA bst = new GrafoTDA();
                for (Vertice v : base.obtenerVertices()) {
                    bst.agregarVertice(v);
                }
                for (Map.Entry<Vertice, List<Arista>> e : res.arbol.entrySet()) {
                    for (Arista a : e.getValue()) {
                        bst.agregarArista(a.getOrigen(), a.getDestino(), a.getDistancia());
                        a.getDestino().setAnterior(a.getOrigen());
                    }
                }

                JPanel panel = PanelGrafo.obtenerGrafoPintado(bst);
                SwingUtilities.invokeLater(() -> {
                    removeCurrentGrafoPanel();
                    add(panel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                });

                StringBuilder sb = new StringBuilder();
                sb.append("BFS desde ").append(semillaNombre).append("\n\n")
                        .append("Orden de descubrimiento (con pesos):\n");

                int pesoTotal = 0;
                List<Vertice> orden = res.orden;

                for (int i = 0; i < orden.size(); i++) {
                    Vertice v = orden.get(i);
                    double peso = 0;

                    Vertice anterior = v.getAnterior();
                    if (anterior != null) {
                        List<Arista> aristas = res.arbol.get(anterior);
                        if (aristas != null) {
                            for (Arista a : aristas) {
                                if (a.getDestino().equals(v)) {
                                    peso = a.getDistancia();
                                    pesoTotal += peso;
                                    break;
                                }
                            }
                        }
                    }

                    sb.append(v.getNombre()).append(" (").append(peso).append(")");
                    if (i < orden.size() - 1) {
                        sb.append(" → ");
                    }
                }

                sb.append("\n\nPeso total del recorrido: ").append(pesoTotal);
                sb.append("\n\nNiveles:\n");

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
            } catch (NoSuchElementException ex) {
                SwingUtilities.invokeLater(()
                        -> JOptionPane.showMessageDialog(this,
                                "Ciudad no encontrada: " + semillaNombre,
                                "BFS", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    private void pintarRecorridoDFS(String semillaNombre) {
        new Thread(() -> {
            try {
                GrafoTDA base = new GrafoChiapas().getGrafo();
                Vertice semilla = base.obtenerVertices().stream()
                        .filter(v -> v.getNombre().equals(semillaNombre))
                        .findFirst().orElseThrow();

                DFS.Resultado res = DFS.ejecutarDFS(base, semilla);

                GrafoTDA dfsTree = new GrafoTDA();
                base.obtenerVertices().forEach(dfsTree::agregarVertice);
                for (Map.Entry<Vertice, List<Arista>> entry : res.arbol.entrySet()) {
                    for (Arista a : entry.getValue()) {
                        dfsTree.agregarArista(a.getOrigen(), a.getDestino(), a.getDistancia());
                        a.getDestino().setAnterior(a.getOrigen());
                    }
                }

                JPanel panelDFS = PanelGrafo.obtenerGrafoPintado(dfsTree);

                SwingUtilities.invokeLater(() -> {
                    removeCurrentGrafoPanel();
                    add(panelDFS, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                });

                StringBuilder sb = new StringBuilder();
                sb.append("DFS desde ").append(semillaNombre).append("\n\n")
                        .append("Orden de descubrimiento (con pesos):\n");

                int pesoTotal = 0;
                List<Vertice> orden = res.orden;
                for (int i = 0; i < orden.size(); i++) {
                    Vertice v = orden.get(i);
                    double peso = 0;

                    Vertice anterior = v.getAnterior();
                    if (anterior != null) {
                        List<Arista> aristas = res.arbol.get(anterior);
                        if (aristas != null) {
                            for (Arista a : aristas) {
                                if (a.getDestino().equals(v)) {
                                    peso = a.getDistancia();
                                    pesoTotal += peso;
                                    break;
                                }
                            }
                        }
                    }

                    sb.append(v.getNombre()).append(" (").append(peso).append(")");
                    if (i < orden.size() - 1) {
                        sb.append(" → ");
                    }
                }

                sb.append("\n\nPeso total del recorrido: ").append(pesoTotal);


                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        this,
                        sb.toString(),
                        "Resultado DFS",
                        JOptionPane.INFORMATION_MESSAGE
                ));

            } catch (NoSuchElementException ex) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        this,
                        "Ciudad no encontrada: " + semillaNombre,
                        "DFS",
                        JOptionPane.ERROR_MESSAGE
                ));

            } catch (InterruptedException ex) {
                Logger.getLogger(PanelRecorrer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

}
