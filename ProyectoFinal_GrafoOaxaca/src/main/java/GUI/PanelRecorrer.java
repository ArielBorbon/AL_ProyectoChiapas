package GUI;

import javax.swing.*;
import java.awt.*;
import GUI.Estilos;
public class PanelRecorrer extends JPanel {

    public PanelRecorrer(){
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
    }

}
