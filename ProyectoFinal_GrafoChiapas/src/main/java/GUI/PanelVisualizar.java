package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import Implementacion.GrafoTDA;
import Implementacion.Vertice;


public class PanelVisualizar extends JPanel {

    private final GrafoChiapas grafoChiapas = new GrafoChiapas();


    public PanelVisualizar() {
        initComponents();
        mostrarGrafo();
    }

    private void initComponents() {
        // configuracion del panel
        setLayout(new BorderLayout());
        // creacion de los botones
        JButton btnTabla = Estilos.crearBoton("Mostrar tabla de nodos y aristas");
        JButton btnGrafo = Estilos.crearBoton("Mostrar grafo de forma visual");
        JButton btnVolver = Estilos.crearBoton("Volver");
        // creacion del panel de los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 3, 10, 10));

        // agregar botones al panel
        panelBotones.add(btnTabla, BorderLayout.CENTER);
        panelBotones.add(btnGrafo, BorderLayout.CENTER);
        panelBotones.add(btnVolver, BorderLayout.CENTER);
        // agregar panel de los botones
        add(panelBotones, BorderLayout.SOUTH);

        btnTabla.addActionListener(e -> {
    // Aquí llamamos al método que crea y muestra la tabla
    mostrarTabla();
});

        btnGrafo.addActionListener(e -> {
            mostrarGrafo();
        });
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
    private String[][] obtenerDatosTabla() {
    List<String[]> datos = new ArrayList<>();

    GrafoTDA grafo = grafoChiapas.getGrafo();

    for (Vertice origen : grafo.obtenerVertices()) {
        for (var arista : grafo.obtenerAdyacentes(origen)) {
            // Evitar duplicados en grafo no dirigido
            if (origen.getNombre().compareTo(arista.getDestino().getNombre()) < 0) {
                String[] fila = {
                    arista.getOrigen().getNombre(),
                    arista.getDestino().getNombre(),
                    String.valueOf(arista.getDistancia())
                };
                datos.add(fila);
            }
        }
    }

    return datos.toArray(new String[0][0]);
}
private void mostrarTabla() {
    String[][] datos = obtenerDatosTabla();
    String[] columnas = {"Origen", "Destino", "Distancia"};

    ModeloTabla modelo = new ModeloTabla(convertirADatosNumericos(datos), columnas);
    JTable tabla = new JTable(modelo);
    JScrollPane scrollPane = new JScrollPane(tabla);

    // Estilo visual
    tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
    tabla.setRowHeight(28);
    JTableHeader header = tabla.getTableHeader();
    header.setFont(new Font("SansSerif", Font.BOLD, 16));
    header.setBackground(new Color(60, 130, 180));
    header.setForeground(Color.WHITE);
    tabla.setShowHorizontalLines(false);
    tabla.setShowVerticalLines(false);
    tabla.setIntercellSpacing(new Dimension(0, 0));
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Estilo de celdas
    tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (isSelected) {
                c.setBackground(new Color(186, 228, 255));
            } else if (row % 2 == 0) {
                c.setBackground(new Color(245, 245, 245));
            } else {
                c.setBackground(Color.WHITE);
            }

            return c;
        }
    });

    // Ordenador
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
    tabla.setRowSorter(sorter);

    // ComboBox para ordenar alfabéticamente
    String[] opcionesAlfabetico = {"Ordenar por Origen: A-Z", "Ordenar por Origen: Z-A"};
    JComboBox<String> comboAlfabetico = new JComboBox<>(opcionesAlfabetico);
    comboAlfabetico.setBackground(new java.awt.Color(221, 221, 221));
    comboAlfabetico.setFont(new java.awt.Font("Segoe UI", 0, 14));
    comboAlfabetico.setForeground(new java.awt.Color(0, 0, 0));

    comboAlfabetico.addActionListener(e -> {
        if (comboAlfabetico.getSelectedIndex() == 0) {
            sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        } else {
            sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.DESCENDING)));
        }
    });

    // ComboBox para ordenar por distancia
    String[] opcionesDistancia = {"Ordenar por Distancia: Menor a Mayor", "Ordenar por Distancia: Mayor a Menor"};
    JComboBox<String> comboDistancia = new JComboBox<>(opcionesDistancia);
    comboDistancia.setBackground(new java.awt.Color(221, 221, 221));
    comboDistancia.setFont(new java.awt.Font("Segoe UI", 0, 14));
    comboDistancia.setForeground(new java.awt.Color(0, 0, 0));
    

    comboDistancia.addActionListener(e -> {
        if (comboDistancia.getSelectedIndex() == 0) {
            sorter.setSortKeys(List.of(new RowSorter.SortKey(2, SortOrder.ASCENDING)));
        } else {
            sorter.setSortKeys(List.of(new RowSorter.SortKey(2, SortOrder.DESCENDING)));
        }
    });

    // Panel para comboboxes
    JPanel panelFiltros = new JPanel(new GridLayout(1, 2, 10, 10));
    panelFiltros.add(comboAlfabetico);
    panelFiltros.add(comboDistancia);
    panelFiltros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Reemplazar el contenido actual
    Component panelCentral = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER);
    if (panelCentral != null) {
        remove(panelCentral);
    }

    Component panelArriba = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.NORTH);
    if (panelArriba != null) {
        remove(panelArriba);
    }

    add(panelFiltros, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    revalidate();
    repaint();
}
private Object[][] convertirADatosNumericos(String[][] datos) {
    Object[][] resultado = new Object[datos.length][datos[0].length];

    for (int i = 0; i < datos.length; i++) {
        resultado[i][0] = datos[i][0];
        resultado[i][1] = datos[i][1];
        resultado[i][2] = Double.parseDouble(datos[i][2]); // convierte a número
    }

    return resultado;
}

class ModeloTabla extends DefaultTableModel {
    public ModeloTabla(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 2) { // Distancia
            return Double.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // hace que las celdas no sean editables
    }
}



}
