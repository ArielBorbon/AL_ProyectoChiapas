package GUI;

import Algoritmos.Busqueda.BFS;
import Algoritmos.Busqueda.DFS;
import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;
import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
                        .findFirst()
                        .orElseThrow();

                BFS.Resultado res = BFS.ejecutar(base, semilla);

                GrafoTDA sub = new GrafoTDA();
                base.obtenerVertices().stream()
                        .sorted(Comparator.comparing(Vertice::getNombre))
                        .forEach(sub::agregarVertice);

                for (int i = 1; i < res.orden.size(); i++) {
                    Vertice v = res.orden.get(i);

                    Vertice padre = null;
                    double peso = 0;
                    for (Vertice u : res.arbol.keySet()) {
                        for (Arista a : res.arbol.get(u)) {
                            if (a.getDestino().equals(v)) {
                                padre = u;
                                peso = a.getDistancia();
                            }
                        }
                    }
                    if (padre != null) {
                        sub.agregarArista(padre, v, peso);
                    }

                    SwingUtilities.invokeLater(() -> {
                        removeCurrentGrafoPanel();
                        add(PanelGrafo.obtenerGrafoPintado(sub), BorderLayout.CENTER);
                        revalidate();
                        repaint();
                    });
                    Thread.sleep(500);
                }

                StringBuilder sb = new StringBuilder();
                sb.append("BFS desde ").append(semillaNombre).append("\n\n");
                sb.append("Orden de descubrimiento:\n")
                        .append(res.orden.stream()
                                .map(Vertice::getNombre)
                                .collect(Collectors.joining(" → ")))
                        .append("\n\nNiveles:\n");
                res.nivel.entrySet().stream()
                        .sorted(Comparator.comparing(e -> e.getKey().getNombre()))
                        .forEach(e
                                -> sb.append(e.getKey().getNombre())
                                .append(": ")
                                .append(e.getValue())
                                .append("\n")
                        );

                SwingUtilities.invokeLater(()
                        -> JOptionPane.showMessageDialog(this,
                                sb.toString(),
                                "Resultado BFS",
                                JOptionPane.INFORMATION_MESSAGE
                        )
                );

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
                Vertice semilla = base.obtenerVertices().stream()
                        .filter(v -> v.getNombre().equals(semillaNombre))
                        .findFirst()
                        .orElseThrow();

                DFS.Resultado res = DFS.ejecutar(base, semilla);

                GrafoTDA sub = new GrafoTDA();
                base.obtenerVertices().stream()
                        .sorted(Comparator.comparing(Vertice::getNombre))
                        .forEach(sub::agregarVertice);

                for (int i = 1; i < res.orden.size(); i++) {
                    Vertice v = res.orden.get(i);
                    Vertice padre = null;
                    double peso = 0;
                    for (Vertice u : res.arbol.keySet()) {
                        for (Arista a : res.arbol.get(u)) {
                            if (a.getDestino().equals(v)) {
                                padre = u;
                                peso = a.getDistancia();
                            }
                        }
                    }
                    if (padre != null) {
                        sub.agregarArista(padre, v, peso);
                    }

                    SwingUtilities.invokeLater(() -> {
                        removeCurrentGrafoPanel();
                        add(PanelGrafo.obtenerGrafoPintado(sub), BorderLayout.CENTER);
                        revalidate();
                        repaint();
                    });
                    Thread.sleep(500);
                }

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
