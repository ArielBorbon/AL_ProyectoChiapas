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

    private JPanel panelOpciones; // <-- Necesario si lo usas en btnReportes.addActionListener
    private JTextArea textAreaReportes;

    public PanelReportes() {
        initComponents();
    }

    private void initComponents() {
    setLayout(new BorderLayout());

    // Botones principales
    JButton btnReportes = Estilos.crearBoton("Generar reportes de complejidad temporal/ T(n)");
    JButton btnVolver = Estilos.crearBoton("Volver");

    // Panel de botones de opciones
    panelOpciones = new JPanel();
    panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
    JButton btnRecorrer = Estilos.crearBoton("Reportes de algoritmos de recorrido");
    JButton btnMST = Estilos.crearBoton("Reportes de algoritmo de MST");
    JButton btnRutaMasCorta = Estilos.crearBoton("Reportes de algoritmo de ruta más corta");

    // Panel de botones de opciones de búsqueda
    JPanel panelOpcionesBusqueda = new JPanel();
    JButton btnBFS = Estilos.crearBoton("Reportes de algoritmo BFS");
    JButton btnDFS = Estilos.crearBoton("Reportes de algoritmo DFS");
    panelOpcionesBusqueda.setLayout(new BoxLayout(panelOpcionesBusqueda, BoxLayout.Y_AXIS));
    panelOpcionesBusqueda.add(btnBFS);
    panelOpcionesBusqueda.add(Box.createVerticalStrut(10));
    panelOpcionesBusqueda.add(btnDFS);
    panelOpcionesBusqueda.setVisible(false);

    // Panel de botones de opciones de MST
    JPanel panelOpcionesMST = new JPanel();
    JButton btnPrim = Estilos.crearBoton("Reportes de algoritmo Prim");
    JButton btnKruskal = Estilos.crearBoton("Reportes de algoritmo Kruskal");
    JButton btnBoruvka = Estilos.crearBoton("Reportes de algoritmo Boruvka");
    panelOpcionesMST.setLayout(new BoxLayout(panelOpcionesMST, BoxLayout.Y_AXIS));
    panelOpcionesMST.add(btnPrim);
    panelOpcionesMST.add(Box.createVerticalStrut(10));
    panelOpcionesMST.add(btnKruskal);
    panelOpcionesMST.add(Box.createVerticalStrut(10));
    panelOpcionesMST.add(btnBoruvka);
    panelOpcionesMST.setVisible(false);

    // Panel de botones de opciones de ruta más corta
    JPanel panelOpcionesRuta = new JPanel();
    JButton btnDijkstra = Estilos.crearBoton("Reportes de algoritmo Dijkstra");
    JButton btnBellman = Estilos.crearBoton("Reportes de algoritmo Bellman-Ford");
    panelOpcionesRuta.setLayout(new BoxLayout(panelOpcionesRuta, BoxLayout.Y_AXIS));
    panelOpcionesRuta.add(btnDijkstra);
    panelOpcionesRuta.add(Box.createVerticalStrut(10));
    panelOpcionesRuta.add(btnBellman);
    panelOpcionesRuta.setVisible(false);

    // Establecer tamaño uniforme más grande para todos los botones
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

    // Área de texto para reportes más grande
    textAreaReportes = new JTextArea();
    textAreaReportes.setLineWrap(true);
    textAreaReportes.setWrapStyleWord(true);
    textAreaReportes.setEditable(false);
    textAreaReportes.setBorder(new EmptyBorder(10, 10, 10, 10));
    JScrollPane scrollPane = new JScrollPane(textAreaReportes);
    scrollPane.setPreferredSize(new Dimension(700, 400));

    // Panel de botones de opciones
    panelOpciones.add(btnRecorrer);
    panelOpciones.add(Box.createVerticalStrut(10));
    panelOpciones.add(btnMST);
    panelOpciones.add(Box.createVerticalStrut(10));
    panelOpciones.add(btnRutaMasCorta);
    panelOpciones.setVisible(false);

    // Panel inferior con todos los botones (opcionales + volver)
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

    // Añadir el área de texto en el centro (arriba) y botones abajo
    add(scrollPane, BorderLayout.CENTER);
    add(panelBotonesAbajo, BorderLayout.SOUTH);

    // Acción botón volver
    btnVolver.addActionListener(e -> {
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (ventana instanceof MenuPrincipal) {
            MenuPrincipal menu = (MenuPrincipal) ventana;
            menu.getContentPane().removeAll();
            menu.add(menu.getPanelMenuLateral(), BorderLayout.WEST);
            menu.add(new PanelDefault(), BorderLayout.CENTER);
            menu.revalidate();
            menu.repaint();
        }
    });

    // Acción botón reportes: mostrar/ocultar opciones
    btnReportes.addActionListener(e -> {
        btnReportes.setVisible(false);
        panelOpciones.setVisible(true);
        revalidate();
        repaint();
    });

    btnRecorrer.addActionListener(e -> {
        panelOpciones.setVisible(false);
        panelOpcionesBusqueda.setVisible(true);
        panelOpcionesMST.setVisible(false);
        panelOpcionesRuta.setVisible(false);
        textAreaReportes.setText("");
        revalidate();
        repaint();
    });

    btnMST.addActionListener(e -> {
        panelOpciones.setVisible(false);
        panelOpcionesMST.setVisible(true);
        panelOpcionesBusqueda.setVisible(false);
        panelOpcionesRuta.setVisible(false);
        textAreaReportes.setText("");
        revalidate();
        repaint();
    });

    btnRutaMasCorta.addActionListener(e -> {
        panelOpciones.setVisible(false);
        panelOpcionesRuta.setVisible(true);
        panelOpcionesBusqueda.setVisible(false);
        panelOpcionesMST.setVisible(false);
        textAreaReportes.setText("");
        revalidate();
        repaint();
    });

    // Reportes específicos
    btnBFS.addActionListener(e -> {
        textAreaReportes.setText("\n--- Reporte BFS ---\n\nEste algoritmo recorre el grafo nivel por nivel utilizando una cola.\nComplejidad: O(V + E)\nDonde V = vértices y E = aristas.");
    });

    btnDFS.addActionListener(e -> {
        textAreaReportes.setText("\n--- Reporte DFS ---\n\nEste algoritmo recorre el grafo en profundidad utilizando recursión o una pila.\nComplejidad: O(V + E)\nDonde V = vértices y E = aristas.");
    });

    btnPrim.addActionListener(e -> {
        textAreaReportes.setText("\n--- Reporte Prim ---\n\nAlgoritmo para encontrar el árbol de expansión mínima.\nUtiliza un conjunto creciente de vértices y una cola de prioridad.\nComplejidad: O(E log V)");
    });

    btnKruskal.addActionListener(e -> {
        textAreaReportes.setText("\n--- Reporte Kruskal ---\n\nAlgoritmo de árbol de expansión mínima basado en ordenamiento de aristas.\nUtiliza estructuras de conjuntos disjuntos.\nComplejidad: O(E log E)");
    });

    btnBoruvka.addActionListener(e -> {
        textAreaReportes.setText("\n--- Reporte Boruvka ---\n\nSelecciona la arista más barata de cada componente y las une iterativamente.\nComplejidad: O(E log V)");
    });

    btnDijkstra.addActionListener(e -> {
        textAreaReportes.setText("\n--- Reporte Dijkstra ---\n\nEncuentra las rutas más cortas desde un vértice fuente.\nNo funciona con pesos negativos.\nComplejidad: O((V + E) log V)");
    });

    btnBellman.addActionListener(e -> {
        textAreaReportes.setText("\n--- Reporte Bellman-Ford ---\n\nEncuentra las rutas más cortas incluso con pesos negativos.\nComplejidad: O(V * E)");
    });
}

}
