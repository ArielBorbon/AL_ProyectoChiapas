package GUI;

import javax.swing.*;
import java.awt.*;
import GUI.Estilos;

public class PanelReportes extends JPanel {

    public PanelReportes(){
        setLayout(new BorderLayout());
        //Botones
        JButton btnReportes = Estilos.crearBoton("Generar reportes de complejidad temporal/ T(n)");
        JButton btnVolver = Estilos.crearBoton("Vovler");
        //Panel botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnReportes, BorderLayout.CENTER);
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
