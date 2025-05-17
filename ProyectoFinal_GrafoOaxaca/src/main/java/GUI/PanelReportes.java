package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PanelReportes extends JPanel {

    private JPanel panelOpciones; // <-- Necesario si lo usas en btnReportes.addActionListener

    public PanelReportes() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Botones principales
        JButton btnReportes = Estilos.crearBoton("Generar reportes de complejidad temporal/ T(n)");
        JButton btnVolver = Estilos.crearBoton("Volver");

        // Panel de botones de opciones
        // ... dentro de initComponents()

// Panel de botones de opciones
panelOpciones = new JPanel();
panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
JButton btnRecorrer = Estilos.crearBoton("Reportes de algoritmos de recorrido");
JButton btnMST = Estilos.crearBoton("Reportes de algoritmo de MST");
JButton btnRutaMasCorta = Estilos.crearBoton("Reportes de algoritmo de ruta más corta");

// Panel de botones de opciones
panelOpciones = new JPanel();
panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));


// Tamaño y alineación igual que btnReportes
java.awt.Dimension tamañoPreferido = btnReportes.getPreferredSize();
int altura = tamañoPreferido.height;

btnRecorrer.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, altura));
btnMST.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, altura));
btnRutaMasCorta.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, altura));

btnRecorrer.setAlignmentX(CENTER_ALIGNMENT);
btnMST.setAlignmentX(CENTER_ALIGNMENT);
btnRutaMasCorta.setAlignmentX(CENTER_ALIGNMENT);

panelOpciones.add(btnRecorrer);
panelOpciones.add(Box.createVerticalStrut(10));
panelOpciones.add(btnMST);
panelOpciones.add(Box.createVerticalStrut(10));
panelOpciones.add(btnRutaMasCorta);

panelOpciones.setVisible(false);  // <-- Oculto al inicio




        // Panel central con disposición vertical
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        btnReportes.setAlignmentX(CENTER_ALIGNMENT);
        panelOpciones.setAlignmentX(CENTER_ALIGNMENT); // opcional para centrar
        panelCentral.add(Box.createVerticalGlue());
        panelCentral.add(btnReportes);
        panelCentral.add(Box.createVerticalStrut(20));
        panelCentral.add(panelOpciones); // <-- ahora sí lo agregas al panel
        panelCentral.add(Box.createVerticalGlue());

        // Panel inferior con botón volver
        JPanel panelBotones = new JPanel(new GridLayout(1, 1, 10, 10));
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
        add(panelCentral, BorderLayout.CENTER);

        // Acción botón volver
        btnVolver.addActionListener(e -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof MenuPrincipal) {
                MenuPrincipal menu = (MenuPrincipal) ventana;
                menu.getContentPane().removeAll();
                menu.add(menu.getPanelMenuLateral(), BorderLayout.WEST);
                menu.add(new PanelDefault(), BorderLayout.CENTER);
                menu.revalidate();
                menu.repaint();
            }
        });

        // Acción botón reportes: mostrar/ocultar opciones
        btnReportes.addActionListener(e -> {
              btnReportes.setVisible(false);
    panelOpciones.setVisible(true);
    revalidate();
    repaint();
        });
    }
}
