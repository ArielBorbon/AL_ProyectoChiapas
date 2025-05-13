package GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import GUI.Estilos;
import javax.swing.*;

//import org.graphstream.ui.graphicGraph.stylesheet.Color;
import org.w3c.dom.events.MouseEvent;

public class PanelVisualizar extends JPanel {

    public PanelVisualizar() {
        initComponents();
    }

    private void initComponents() {
        // configuracion del panel
        setLayout(new BorderLayout());
        // creacion de los botones
        JButton btnTabla = Estilos.crearBoton("Mostrar tabla de nosos y aristas");
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

        });
        btnGrafo.addActionListener(e -> {

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
        
        Component panelCentral = ((BorderLayout) menu.getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (panelCentral != null) {
            menu.getContentPane().remove(panelCentral);
        }
        this.add(panelGrafo, BorderLayout.CENTER);

    }
}
