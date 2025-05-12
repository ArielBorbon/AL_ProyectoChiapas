package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        JButton btnVisualizar = crearBoton("Visualizar");
        JButton btnRecorrer = crearBoton("Recorrer");
        JButton btnMST = crearBoton("Árbol de expansión mínima");
        JButton btnRutaMasCorta = crearBoton("Ruta más corta");
        JButton btnReportes = crearBoton("Reportes de complejidad temporal");
        JButton btnSalir = crearBoton("Salir");

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

    private JButton crearBoton(String nombre) {
        JButton boton = new JButton(nombre);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        boton.setBackground(new Color(230, 230, 250));
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        boton.setPreferredSize(new Dimension(250, 80)); // Aumenta la altura de cada botón a 80px

        Color colorBase = new Color(230, 230, 250);
        Color colorHover = new Color(180, 180, 255);

        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                boton.setBackground(colorHover);
            }

            public void mouseExited(MouseEvent evt) {
                boton.setBackground(colorBase);
            }
        });

        return boton;
    }
}