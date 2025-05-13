package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import GUI.Estilos;




public class MenuPrincipal extends JFrame {
    private JPanel panelMenuLateral;

    public MenuPrincipal() {
        this.setTitle("ALGORITMOS DE GRAFOS EN CHIAPAS");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setLayout(new BorderLayout());

        panelMenuLateral = new JPanel();
        panelMenuLateral.setLayout(new GridBagLayout());
        PanelDefault panelDefault = new PanelDefault();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weighty = 1.0; // Hace que los botones se expandan verticalmente

        // Crear botones
        JButton btnVisualizar = Estilos.crearBoton("Visualizar");
        JButton btnRecorrer = Estilos.crearBoton("Recorrer");
        JButton btnMST = Estilos.crearBoton("Árbol de expansión mínima");
        JButton btnRutaMasCorta = Estilos.crearBoton("Ruta más corta");
        JButton btnReportes = Estilos.crearBoton("Reportes de complejidad temporal");
        JButton btnSalir = Estilos.crearBoton("Salir");

        // Agregar botones
        panelMenuLateral.add(btnVisualizar, gbc);
        panelMenuLateral.add(btnRecorrer, gbc);
        panelMenuLateral.add(btnMST, gbc);
        panelMenuLateral.add(btnRutaMasCorta, gbc);
        panelMenuLateral.add(btnReportes, gbc);
        panelMenuLateral.add(btnSalir, gbc);

        // Agregar el panel al JFrame
        this.add(panelMenuLateral, BorderLayout.WEST);
        this.setVisible(true);
        this.add(panelDefault, BorderLayout.CENTER);

        btnVisualizar.addActionListener(e -> {
            this.getContentPane().removeAll();
            this.add(new PanelVisualizar(), BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
        });

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

    // Dentro de MenuPrincipal
    public JPanel getPanelMenuLateral() {
        return panelMenuLateral;
    }

}