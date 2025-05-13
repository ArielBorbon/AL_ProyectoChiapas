package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPrincipal extends JFrame {
    private JPanel panelMenuLateral;

    public MenuPrincipal() {
        this.setTitle("ALGORITMOS DE GRAFOS EN CHIAPAS");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setLayout(new BorderLayout());

        panelMenuLateral = new JPanel();
        panelMenuLateral.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weighty = 1.0; // Hace que los botones se expandan verticalmente

        // Crear botones manualmente sin usar un loop
        JButton btnVisualizar = GUI.Estilos.crearBoton("Visualizar");
        JButton btnRecorrer = GUI.Estilos.crearBoton("Recorrer");
        JButton btnMST = GUI.Estilos.crearBoton("Árbol de expansión mínima");
        JButton btnRutaMasCorta = GUI.Estilos.crearBoton("Ruta más corta");
        JButton btnReportes = GUI.Estilos.crearBoton("Reportes de complejidad temporal");
        JButton btnSalir = GUI.Estilos.crearBoton("Salir");

        // Agregar botones manualmente
        panelMenuLateral.add(btnVisualizar, gbc);
        panelMenuLateral.add(btnRecorrer, gbc);
        panelMenuLateral.add(btnMST, gbc);
        panelMenuLateral.add(btnRutaMasCorta, gbc);
        panelMenuLateral.add(btnReportes, gbc);
        panelMenuLateral.add(btnSalir, gbc);

        // Agregar el panel al JFrame
        this.add(panelMenuLateral, BorderLayout.WEST);
        this.setVisible(true);

        // Agregar acciones a los botones manualmente sin condicionales
        btnRecorrer.addActionListener(e -> {
            this.getContentPane().removeAll();
            this.add(new PanelRecorrer(), BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
        });

        btnMST.addActionListener(e -> {
            this.getContentPane().removeAll();
            this.add(new PanelMST(), BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
        });

        btnRutaMasCorta.addActionListener(e -> {
            this.getContentPane().removeAll();
            this.add(new PanelRutaMasCorta(), BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
        });

        btnReportes.addActionListener(e -> {
            this.getContentPane().removeAll();
            this.add(new PanelReportes(), BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
        });

        btnSalir.addActionListener(e -> System.exit(0));
    }

   
}