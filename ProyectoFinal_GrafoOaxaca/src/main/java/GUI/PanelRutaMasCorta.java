package GUI;

import javax.swing.*;
import java.awt.*;

public class PanelRutaMasCorta extends JPanel {

    public PanelRutaMasCorta(){
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

}
