package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPrincipal extends JFrame {                                      //         T(n) = 214          O(1)

    private JPanel panelMenuLateral;
    private PanelDefault panelDefault;

    public MenuPrincipal() {                                                //213
        this.setTitle("ALGORITMOS DE GRAFOS EN CHIAPAS");       //1
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);       //1
        this.setSize(1000, 800);                        //1
        this.setLayout(new BorderLayout());                     //2

        panelMenuLateral = new JPanel();
        panelMenuLateral.setLayout(new GridBagLayout());            //2
        GridBagConstraints gbc = new GridBagConstraints();          //2
        gbc.fill = GridBagConstraints.HORIZONTAL;               //2
        gbc.gridx = 0;                                          //2
        gbc.gridy = GridBagConstraints.RELATIVE;                //2
        gbc.weightx = 1.0;                                      //2
        gbc.insets = new Insets(10, 10, 10, 10);            //2
        gbc.weighty = 1.0; // Hace que los botones se expandan verticalmente        //2

        // Crear botones manualmente sin usar un loop
        JButton btnVisualizar = GUI.Estilos.crearBoton("Visualizar");           //23                    considerando el metodo de crearBoton en estilos
        JButton btnRecorrer = GUI.Estilos.crearBoton("Recorrer");               //23
        JButton btnMST = GUI.Estilos.crearBoton("Árbol de expansión mínima");           //23
        JButton btnRutaMasCorta = GUI.Estilos.crearBoton("Ruta más corta");             //23
        JButton btnReportes = GUI.Estilos.crearBoton("Reportes de complejidad temporal");       //23
        JButton btnSalir = GUI.Estilos.crearBoton("Salir");                                     //23

        // Agregar botones manualmente
        panelMenuLateral.add(btnVisualizar, gbc);                           //1
        panelMenuLateral.add(btnRecorrer, gbc);                             //1
        panelMenuLateral.add(btnMST, gbc);                                  //1
        panelMenuLateral.add(btnRutaMasCorta, gbc);                         //1
        panelMenuLateral.add(btnReportes, gbc);                             //1
        panelMenuLateral.add(btnSalir, gbc);                                //1

        // Agregar el panel al JFrame
        this.add(panelMenuLateral, BorderLayout.WEST);                      //2
        this.setVisible(true);                                                         //1
        this.add(this.panelDefault = new PanelDefault(), BorderLayout.CENTER);  //4
        // Agregar acciones a los botones manualmente sin condicionales
        btnVisualizar.addActionListener(e -> {                                           //1
            this.getContentPane().removeAll();                                           //2
            this.add(new PanelVisualizar(), BorderLayout.CENTER);               //3
            this.revalidate();                                                          //1
            this.repaint();                                                                 //1
        });

        btnRecorrer.addActionListener(e -> {                                                //1
            this.getContentPane().removeAll();                                              //2
            this.add(new PanelRecorrer(), BorderLayout.CENTER);                     //3
            this.revalidate();                                                              //1
            this.repaint();                                                                 //1
        });

        btnMST.addActionListener(e -> {                                                     //1
            this.getContentPane().removeAll();                                              //2
            this.add(new PanelMST(), BorderLayout.CENTER);                          //3
            this.revalidate();                                                              //1
            this.repaint();                                                                 //1
        });

        btnRutaMasCorta.addActionListener(e -> {                                            //1
            this.getContentPane().removeAll();                                              //2
            this.add(new PanelRutaMasCorta(), BorderLayout.CENTER);                 //3
            this.revalidate();                                                              //1
            this.repaint();                                                                 //1
        });

        btnReportes.addActionListener(e -> {                                                //1
            this.getContentPane().removeAll();                                              //2
            this.add(new PanelReportes(), BorderLayout.CENTER);                     //3
            this.revalidate();                                                              //1
            this.repaint();                                                                 //1
        });

        btnSalir.addActionListener(e -> System.exit(0));                                //2
    }

    public JPanel getPanelMenuLateral() {
        return panelMenuLateral;                                                                //1
    }

}
