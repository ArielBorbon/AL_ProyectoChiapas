package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Implementacion.Arista;
import Implementacion.ColorVertice;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

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

                Map<Vertice, ColorVertice> color = new HashMap<>();
                Map<Vertice, Integer> nivel = new HashMap<>();
                Map<Vertice, Double> pesos = new HashMap<>();

                for (Vertice v : base.obtenerVertices()) {
                    color.put(v, ColorVertice.BLANCO);
                }

                GrafoTDA arbol = new GrafoTDA();
                base.obtenerVertices().forEach(arbol::agregarVertice);

                List<Vertice> ordenDescubrimiento = new ArrayList<>();
                Queue<Vertice> cola = new LinkedList<>();

                color.put(semilla, ColorVertice.GRIS);
                cola.add(semilla);
                nivel.put(semilla, 0);
                pesos.put(semilla, 0.0);
                ordenDescubrimiento.add(semilla);

                SwingUtilities.invokeLater(() -> {
                    removeCurrentGrafoPanel();
                    add(PanelGrafo.obtenerGrafoPintadoRecorrido(color, arbol),
                            BorderLayout.CENTER);
                    revalidate();
                    repaint();
                });
                Thread.sleep(800);

                while (!cola.isEmpty()) {
                    Vertice u = cola.poll();

                    for (Arista a : base.obtenerAdyacentes(u)) {
                        Vertice v = a.getDestino();
                        if (color.get(v) == ColorVertice.BLANCO) {
                            color.put(v, ColorVertice.GRIS);
                            arbol.agregarArista(u, v, a.getDistancia());
                            arbol.agregarArista(v, u, a.getDistancia());

                            pesos.put(v, a.getDistancia());
                            nivel.put(v, nivel.get(u) + 1);
                            ordenDescubrimiento.add(v);

                            SwingUtilities.invokeLater(() -> {
                                removeCurrentGrafoPanel();
                                add(PanelGrafo.obtenerGrafoPintadoRecorrido(color, arbol),
                                        BorderLayout.CENTER);
                                revalidate();
                                repaint();
                            });
                            Thread.sleep(800);

                            cola.add(v);
                        }
                    }

                    color.put(u, ColorVertice.NEGRO);
                    SwingUtilities.invokeLater(() -> {
                        removeCurrentGrafoPanel();
                        add(PanelGrafo.obtenerGrafoPintadoRecorrido(color, arbol),
                                BorderLayout.CENTER);
                        revalidate();
                        repaint();
                    });
                    Thread.sleep(800);
                }

                StringBuilder sb = new StringBuilder();
                sb.append("BFS desde ").append(semillaNombre).append("\n\n");

                sb.append("Orden de descubrimiento con niveles:\n");
                int i = 1;
                for (Vertice v : ordenDescubrimiento) {
                    sb.append(i++)
                            .append(". ")
                            .append(v.getNombre())
                            .append(" (nivel: ")
                            .append(nivel.getOrDefault(v, 0))
                            .append(")\n");
                }

                sb.append("\nOrden de descubrimiento con pesos:\n");
                int pesoTotal = 0;
                for (int j = 0; j < ordenDescubrimiento.size(); j++) {
                    Vertice v = ordenDescubrimiento.get(j);
                    double peso = pesos.getOrDefault(v, 0.0);
                    sb.append(v.getNombre()).append(" (").append(peso).append(")");
                    if (j < ordenDescubrimiento.size() - 1) {
                        sb.append(" → ");
                    }
                    pesoTotal += peso;
                }

                sb.append("\n\nPeso total del recorrido: ").append(pesoTotal);

                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        this,
                        sb.toString(),
                        "Resultado BFS",
                        JOptionPane.INFORMATION_MESSAGE
                ));

            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        this,
                        "Error en BFS: " + ex.getMessage(),
                        "BFS",
                        JOptionPane.ERROR_MESSAGE));
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

                Map<Vertice, ColorVertice> color = new HashMap<>();
                Map<Vertice, Double> pesos = new HashMap<>();

                for (Vertice v : base.obtenerVertices()) {
                    color.put(v, ColorVertice.BLANCO);
                }

                GrafoTDA arbol = new GrafoTDA();
                base.obtenerVertices().forEach(arbol::agregarVertice);

                List<Vertice> ordenDescubrimiento = new ArrayList<>();

                Consumer<Vertice> dfsVisitar = new Consumer<>() {
                    @Override
                    public void accept(Vertice u) {
                        try {
                            color.put(u, ColorVertice.GRIS);
                            ordenDescubrimiento.add(u);

                            SwingUtilities.invokeLater(() -> {
                                removeCurrentGrafoPanel();
                                add(PanelGrafo.obtenerGrafoPintadoRecorrido(color, arbol),
                                        BorderLayout.CENTER);
                                revalidate();
                                repaint();
                            });
                            Thread.sleep(800);

                            for (Arista a : base.obtenerAdyacentes(u)) {
                                Vertice v = a.getDestino();
                                if (color.get(v) == ColorVertice.BLANCO) {
                                    arbol.agregarArista(u, v, a.getDistancia());
                                    arbol.agregarArista(v, u, a.getDistancia());

                                    pesos.put(v, a.getDistancia());
                                    accept(v);
                                }
                            }

                            color.put(u, ColorVertice.NEGRO);
                            SwingUtilities.invokeLater(() -> {
                                removeCurrentGrafoPanel();
                                add(PanelGrafo.obtenerGrafoPintadoRecorrido(color, arbol),
                                        BorderLayout.CENTER);
                                revalidate();
                                repaint();
                            });
                            Thread.sleep(800);

                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                };

                pesos.put(semilla, 0.0);
                dfsVisitar.accept(semilla);

                for (Vertice v : base.obtenerVertices()) {
                    if (color.get(v) == ColorVertice.BLANCO) {
                        pesos.put(v, 0.0);
                        dfsVisitar.accept(v);
                    }
                }

                StringBuilder sb = new StringBuilder();
                sb.append("DFS desde ").append(semillaNombre).append("\n\n");

                sb.append("Orden de descubrimiento:\n");
                int i = 1;
                for (Vertice v : ordenDescubrimiento) {
                    sb.append(i++)
                            .append(". ")
                            .append(v.getNombre())
                            .append("\n");
                }

                sb.append("\nOrden de descubrimiento con pesos:\n");
                int pesoTotal = 0;
                for (int j = 0; j < ordenDescubrimiento.size(); j++) {
                    Vertice v = ordenDescubrimiento.get(j);
                    double peso = pesos.getOrDefault(v, 0.0);
                    sb.append(v.getNombre()).append(" (").append(peso).append(")");
                    if (j < ordenDescubrimiento.size() - 1) {
                        sb.append(" → ");
                    }
                    pesoTotal += peso;
                }

                sb.append("\n\nPeso total del recorrido: ").append(pesoTotal);

                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        this,
                        sb.toString(),
                        "Resultado DFS",
                        JOptionPane.INFORMATION_MESSAGE
                ));

            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        this,
                        "Error en DFS: " + ex.getMessage(),
                        "DFS",
                        JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

}
