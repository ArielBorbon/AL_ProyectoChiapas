package GUI;

import javax.swing.*;
import java.awt.*;

public class PanelMST extends JPanel {

    public PanelMST(){
        initComponents();
        mostrarGrafo();

    }

    private void initComponents() {
        //Configuracion basica del panel
        setLayout(new BorderLayout());
        //botones
        JButton btnKruskal = Estilos.crearBoton("Usar algoritmo de Kruskal");
        JButton btnPrim = Estilos.crearBoton("Usar algoritmo de Prim");
        JButton btnBoruvka = Estilos.crearBoton("Usar algoritmo de Boruvka");
        JButton btnVolver = Estilos.crearBoton("Vovler");
        // panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 4, 10, 10));
        //agregar botones al panel
        panelBotones.add(btnKruskal, BorderLayout.CENTER);
        panelBotones.add(btnPrim, BorderLayout.CENTER);
        panelBotones.add(btnBoruvka, BorderLayout.CENTER);
        panelBotones.add(btnVolver, BorderLayout.CENTER);
        //agregar panel
        add(panelBotones, BorderLayout.SOUTH);
        
        //metodo para salir
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
