package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class PanelReportes extends JPanel {

    private JPanel panelOpciones;
    private JPanel panelOpcionesBusqueda;
    private JPanel panelOpcionesMST;
    private JPanel panelOpcionesRuta;
    private JTextArea textAreaReportes;
    private JPanel panelActual; // NUEVO: para volver al panel anterior

    public PanelReportes() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JButton btnReportes = Estilos.crearBoton("Generar reportes de complejidad temporal/ T(n)");
        JButton btnVolver = Estilos.crearBoton("Volver");

        // Panel de botones principales
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        JButton btnRecorrer = Estilos.crearBoton("Reportes de algoritmos de recorrido");
        JButton btnMST = Estilos.crearBoton("Reportes de algoritmo de MST");
        JButton btnRutaMasCorta = Estilos.crearBoton("Reportes de algoritmo de ruta más corta");
        panelOpciones.add(btnRecorrer);
        panelOpciones.add(Box.createVerticalStrut(10));
        panelOpciones.add(btnMST);
        panelOpciones.add(Box.createVerticalStrut(10));
        panelOpciones.add(btnRutaMasCorta);
        panelOpciones.setVisible(false);

        // Subpaneles
        panelOpcionesBusqueda = new JPanel();
        panelOpcionesBusqueda.setLayout(new BoxLayout(panelOpcionesBusqueda, BoxLayout.Y_AXIS));
        JButton btnBFS = Estilos.crearBoton("Reportes de algoritmo BFS");
        JButton btnDFS = Estilos.crearBoton("Reportes de algoritmo DFS");
        panelOpcionesBusqueda.add(btnBFS);
        panelOpcionesBusqueda.add(Box.createVerticalStrut(10));
        panelOpcionesBusqueda.add(btnDFS);
        panelOpcionesBusqueda.setVisible(false);

        panelOpcionesMST = new JPanel();
        panelOpcionesMST.setLayout(new BoxLayout(panelOpcionesMST, BoxLayout.Y_AXIS));
        JButton btnPrim = Estilos.crearBoton("Reportes de algoritmo Prim");
        JButton btnKruskal = Estilos.crearBoton("Reportes de algoritmo Kruskal");
        JButton btnBoruvka = Estilos.crearBoton("Reportes de algoritmo Boruvka");
        panelOpcionesMST.add(btnPrim);
        panelOpcionesMST.add(Box.createVerticalStrut(10));
        panelOpcionesMST.add(btnKruskal);
        panelOpcionesMST.add(Box.createVerticalStrut(10));
        panelOpcionesMST.add(btnBoruvka);
        panelOpcionesMST.setVisible(false);

        panelOpcionesRuta = new JPanel();
        panelOpcionesRuta.setLayout(new BoxLayout(panelOpcionesRuta, BoxLayout.Y_AXIS));
        JButton btnDijkstra = Estilos.crearBoton("Reportes de algoritmo Dijkstra");
        JButton btnBellman = Estilos.crearBoton("Reportes de algoritmo Bellman-Ford");
        panelOpcionesRuta.add(btnDijkstra);
        panelOpcionesRuta.add(Box.createVerticalStrut(10));
        panelOpcionesRuta.add(btnBellman);
        panelOpcionesRuta.setVisible(false);

        // Tamaño uniforme de botones
        Dimension tamBoton = new Dimension(350, 45);
        JButton[] todosLosBotones = {
            btnRecorrer, btnMST, btnRutaMasCorta,
            btnBFS, btnDFS,
            btnPrim, btnKruskal, btnBoruvka,
            btnDijkstra, btnBellman,
            btnReportes, btnVolver
        };
        for (JButton boton : todosLosBotones) {
            boton.setPreferredSize(tamBoton);
            boton.setMaximumSize(tamBoton);
            boton.setMinimumSize(tamBoton);
            boton.setAlignmentX(CENTER_ALIGNMENT);
        }

        // Área de texto
        textAreaReportes = new JTextArea();
        textAreaReportes.setLineWrap(true);
        textAreaReportes.setWrapStyleWord(true);
        textAreaReportes.setEditable(false);
        textAreaReportes.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(textAreaReportes);
        scrollPane.setPreferredSize(new Dimension(700, 400));

        // Panel inferior
        JPanel panelBotonesAbajo = new JPanel();
        panelBotonesAbajo.setLayout(new BoxLayout(panelBotonesAbajo, BoxLayout.Y_AXIS));
        panelBotonesAbajo.add(btnReportes);
        panelBotonesAbajo.add(Box.createVerticalStrut(10));
        panelBotonesAbajo.add(panelOpciones);
        panelBotonesAbajo.add(panelOpcionesBusqueda);
        panelBotonesAbajo.add(panelOpcionesMST);
        panelBotonesAbajo.add(panelOpcionesRuta);
        panelBotonesAbajo.add(Box.createVerticalStrut(10));
        panelBotonesAbajo.add(btnVolver);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotonesAbajo, BorderLayout.SOUTH);

        // Acción volver
        btnVolver.addActionListener(e -> {
            if (panelOpcionesBusqueda.isVisible() || panelOpcionesMST.isVisible() || panelOpcionesRuta.isVisible()) {
                if (panelActual != null) {
                    panelOpcionesBusqueda.setVisible(false);
                    panelOpcionesMST.setVisible(false);
                    panelOpcionesRuta.setVisible(false);
                    panelActual.setVisible(true);
                    textAreaReportes.setText("");
                    panelActual = null; // ya regresamos
                }
            } else {
                // Volver al menú principal
                JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (ventana instanceof MenuPrincipal) {
                    MenuPrincipal menu = (MenuPrincipal) ventana;
                    menu.getContentPane().removeAll();
                    menu.add(menu.getPanelMenuLateral(), BorderLayout.WEST);
                    menu.add(new PanelDefault(), BorderLayout.CENTER);
                    menu.revalidate();
                    menu.repaint();
                }
            }
        });

        // Acción reportes
        btnReportes.addActionListener(e -> {
            btnReportes.setVisible(false);
            panelOpciones.setVisible(true);
            revalidate();
            repaint();
        });

        // Opciones principales
        btnRecorrer.addActionListener(e -> {
            panelActual = panelOpciones;
            panelOpciones.setVisible(false);
            panelOpcionesBusqueda.setVisible(true);
            textAreaReportes.setText("");
        });

        btnMST.addActionListener(e -> {
            panelActual = panelOpciones;
            panelOpciones.setVisible(false);
            panelOpcionesMST.setVisible(true);
            textAreaReportes.setText("");
        });

        btnRutaMasCorta.addActionListener(e -> {
            panelActual = panelOpciones;
            panelOpciones.setVisible(false);
            panelOpcionesRuta.setVisible(true);
            textAreaReportes.setText("");
        });

        // Reportes
        btnBFS.addActionListener(e -> {
            textAreaReportes.setText("\n--- Reporte BFS ---\n\nEste algoritmo recorre el grafo nivel por nivel utilizando una cola.\nComplejidad: O(V + E)");
        });

        btnDFS.addActionListener(e -> {
            textAreaReportes.setText("\n--- Reporte DFS ---\n\nEste algoritmo recorre el grafo en profundidad utilizando recursión o una pila.\nComplejidad: O(V + E)");
        });

        btnPrim.addActionListener(e -> {
            textAreaReportes.setText("\n--- Reporte Prim ---\n\nÁrbol de expansión mínima usando cola de prioridad.\nComplejidad: O(E log V)");
        });

        btnKruskal.addActionListener(e -> {
            textAreaReportes.setText("\n--- Reporte Kruskal ---\n\nOrdena aristas y une componentes con conjuntos disjuntos.\nComplejidad: O(E log E)");
        });

        btnBoruvka.addActionListener(e -> {
            textAreaReportes.setText("\n--- Reporte Boruvka ---\n\nUne componentes con aristas más baratas en paralelo.\nComplejidad: O(E log V)");
        });

        btnDijkstra.addActionListener(e -> {
            textAreaReportes.setText("\n--- Reporte Dijkstra ---\n\nRutas más cortas sin pesos negativos.\nComplejidad: O((V + E) log V)");
        });

        btnBellman.addActionListener(e -> {
            textAreaReportes.setText("\n--- Reporte Bellman-Ford ---\n\nRutas con pesos negativos permitidos.\nComplejidad: O(V * E)");
        });
    }
}
