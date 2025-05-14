package GUI;

import javax.swing.*;

import Algoritmos.MST.Kruskal;
import Implementacion.GrafoTDA;

import java.awt.*;

public class PanelMST extends JPanel {

    public PanelMST() {
        initComponents();
        mostrarGrafoOriginal();

    }

    private void initComponents() {
        // Configuracion basica del panel
        setLayout(new BorderLayout());
        // botones
        JButton btnKruskal = Estilos.crearBoton("Usar algoritmo de Kruskal");
        JButton btnPrim = Estilos.crearBoton("Usar algoritmo de Prim");
        JButton btnBoruvka = Estilos.crearBoton("Usar algoritmo de Boruvka");
        JButton btnVolver = Estilos.crearBoton("Vovler");
        // panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 4, 10, 10));
        // agregar botones al panel
        panelBotones.add(btnKruskal, BorderLayout.CENTER);
        panelBotones.add(btnPrim, BorderLayout.CENTER);
        panelBotones.add(btnBoruvka, BorderLayout.CENTER);
        panelBotones.add(btnVolver, BorderLayout.CENTER);
        // agregar panel
        add(panelBotones, BorderLayout.SOUTH);

        // metodo para salir
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

        btnKruskal.addActionListener(e -> {
            pintarMSTKruskal();
        });

    }

    private void pintarMSTKruskal() {
        try {
            Kruskal kruskal = new Kruskal(new GrafoChiapas().getGrafo());
            kruskal.start();
            while(kruskal.isEjecutando()){
                mostrarGrafoPintado(kruskal.getMst());
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void mostrarGrafoPintado(GrafoTDA grafoPintado) {
        JPanel panelGrafo = PanelGrafo.obtenerGrafoPintado(grafoPintado);
        mostrarGrafo(panelGrafo);
    }

    private void mostrarGrafoOriginal() {
        JPanel panelGrafo = PanelGrafo.obtenerPanelGrafo();
        mostrarGrafo(panelGrafo);
    }

    private void mostrarGrafo(JPanel panelGrafo) {

        Component panelCentral = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (panelCentral != null) {
            remove(panelCentral);
        }

        add(panelGrafo, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

}
